package com.mybatis.config;

import javafx.util.Pair;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;


@Component
@Aspect
public class MultiDataSourceTransactionAspect {
    /**
     * 线程本地变量：为什么使用栈？※为了达到后进先出的效果※
     */
    private static final ThreadLocal<Stack<Pair<DataSourceTransactionManager, TransactionStatus>>> THREAD_LOCAL = new ThreadLocal<>();



    /**
     * 用于获取事务管理器
     */
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 事务声明
     */
    private DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    {
        // 非只读模式
        def.setReadOnly(false);
        // 事务隔离级别：采用数据库的
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    /**
     * 切面
     */
    @Pointcut("@annotation(com.mybatis.config.MultiDataSourceTransactional)")
    public void pointcut() {
    }

    /**
     * 声明事务
     *
     * @param transactional 注解
     */
    @Before("pointcut() && @annotation(transactional)")
    public void before(MultiDataSourceTransactional transactional) {
        System.out.println("===============事务开始===============");
        // 根据设置的事务名称按顺序声明，并放到ThreadLocal里
        String[] transactionManagerNames = transactional.transactionManagers();
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = new Stack<>();
        for (String transactionManagerName : transactionManagerNames) {
            System.out.println("事务名称："+ transactionManagerName);
            DataSourceTransactionManager transactionManager = applicationContext.getBean(transactionManagerName, DataSourceTransactionManager.class);
            TransactionStatus transactionStatus = transactionManager.getTransaction(def);
            pairStack.push(new Pair(transactionManager, transactionStatus));
        }
        THREAD_LOCAL.set(pairStack);
    }


    /**
     * 提交事务
     */
    @AfterReturning("pointcut()")
    public void afterReturning() {
        // ※栈顶弹出（后进先出）
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            pair.getKey().commit(pair.getValue());
            System.out.println("提交事务："+ pair.getValue());
        }
        THREAD_LOCAL.remove();
        System.out.println("===============事务正常结束===============");
    }


    /**
     * 回滚事务
     */
    @AfterThrowing(value = "pointcut()")
    public void afterThrowing() {
        // ※栈顶弹出（后进先出）
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            pair.getKey().rollback(pair.getValue());
            System.out.println("回滚事务："+ pair.getValue());
        }
        THREAD_LOCAL.remove();
        System.out.println("===============事务异常，已回滚===============");
    }


}

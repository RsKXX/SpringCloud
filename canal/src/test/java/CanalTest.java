import com.canal.CanalApplication;
import com.canal.constant.RabbitMqConstant;
import com.canal.data.entity.UserInformation;
import com.canal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= CanalApplication.class)
public class CanalTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private UserService userService;
//    @Test
//    public void testSave(){
//        UserInformation userInformation = new UserInformation();
//        userInformation.setUsername("xx").setPassword("124");
//        userService.save(userInformation);
//        System.out.println(userInformation);
//    }
    @Test
    public void testRabbitMQ(){
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_TOPIC,RabbitMqConstant.ROUTING_KEY,"hello");
    }

    @Test
    public void testDel(){
//        UserInformation userInformation = new UserInformation();
//        userInformation.setUsername("xx").setPassword("124");
//        userService.save(userInformation);
    }
    @Test
    public void testUpdate(){

//        userService.save(userInformation);
    }
}

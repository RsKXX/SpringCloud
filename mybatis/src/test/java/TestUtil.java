import com.mybatis.data.entity.User;
import com.mybatis.data.entity.UserInformation;
import com.mybatis.service.UserInformationService;
import com.mybatis.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUtil {

    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private UserService userService;

    @Test
    public void Test(){
        User user = new User().setUsername("a").setPassword("x");
        UserInformation userInformation = new UserInformation().setUsername("b").setPassword("x");
        userService.save(user);
        userInformationService.save(userInformation);
    }
}

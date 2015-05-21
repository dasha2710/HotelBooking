import com.hotel.domain.User;
import com.hotel.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Dasha on 21.05.2015.
 */
@Ignore("test is used for adding admin to db")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/contexts/security-context.xml",
                                   "/contexts/service-context.xml",
                                   "/contexts/dao-context.xml"})
public class AddAdminToDb {

    @Autowired
    private UserService service;

    @Test
    public void addAdminToDB(){
        User user = new User("admin_test", "12345678");
        service.saveAdmin(user);
    }

}

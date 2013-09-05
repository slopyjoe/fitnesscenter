import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.util.Date;

import models.Activity;
import models.Member;

import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;


public class ModelTest {
    @Test
    public void insertTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
            	/*User user = new User();
            	user.email = "instructor@fake.com";
            	user.firstName = "User";
            	user.lastName = "Last";
            	user.dob = new Date();
            	user.employee_id = "12345";
            	user.lastLoggedIn = new Date();
            	user.company = "LM";
            	user.save();
            	
            	Activity activity = new Activity();
            	activity.name = "Activity ";
            	activity.setDescription("description");
            	activity.instructor = user;
            	activity.save();
            

            	assertThat(user.email).isEqualTo(User.find.byId(user.email).email);*/
            }
        });
    }
}

import controllers.Dashboard;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;


import play.mvc.BodyParser;
import play.mvc.Result;

import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    
    @Test
    public void findUser() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                Result result = (Result) Dashboard.findUser("123456").getWrappedResult();
                System.out.println(result);
            }
        });
    }
  
   
}

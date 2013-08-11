package controllers;

import static play.data.Form.form;
import models.Client;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;
public class Application extends Controller {
	
	public static class Login {
		public String employeeId;
	}
  
    public static Result index() {
    	return ok(login.render(form(Login.class)));
    }
  
    public static Result login(){
    	return ok(login.render(form(Login.class)));
    }
    
    public static Result authenticate(){
    	Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("employeeId", loginForm.get().employeeId);
            Client client = Client.findClient(loginForm.get().employeeId);
            if(client.id >= 0 )
            {
            	flash("success", "Welcome " + client.person.name + " from " + client.organization);
            	return redirect(routes.Applicationd.index());
            }
            return redirect(
                routes.Application.index()
            );
        }
    }
}

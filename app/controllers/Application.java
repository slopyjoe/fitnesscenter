package controllers;

import java.util.List;
import java.util.Map;

import models.Activity;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
  
	public static class Login{
		public String employee_id;
	}
	
	
    public static Result index() {
    	List<Activity> activities = Activity.find.all();
        return ok(index.render(scala.collection.JavaConversions.asScalaBuffer(activities)));
    }
  
    public static Result login(){
    	Map<String, String[]> queryString = request().queryString();
    	if(queryString.get("employee_id") != null)
    	{
    		flash("success", "Apples");
    	}
    	
    	return redirect(routes.Application.index());
    }
    
}

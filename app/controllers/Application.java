package controllers;

import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
  
	public static class Login{
		public String employee_id;
	}
	
	
    public static Result index() {
    	return redirect(routes.Dashboard.index());
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

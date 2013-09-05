package controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

import models.Activity;
import models.BulletinPost;
import models.FitnessSchedule;
import models.Member;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.dashboard.index;

public class Dashboard extends Controller{

	public static Result index() {
    	List<Activity> activities = Activity.find.all();
	    List<BulletinPost> posts = BulletinPost.find.all();
    	List<Activity> allTheTimeActs = Activity.find.where().eq("hasSchedule", false).findList();
        return ok(index.render(scala.collection.JavaConversions.asScalaBuffer(activities),null,
        		scala.collection.JavaConversions.asScalaBuffer(posts),
        		scala.collection.JavaConversions.asScalaBuffer(allTheTimeActs)));
    }
	
	@Transactional
	public static Result enter() {
		Map<String,String[]> params;
	    params = request().body().asFormUrlEncoded();
	    String email = params.get("employee_id")[0];
	    Member user = Member.find.where().eq("employee_id", email).findUnique();
	    List<Activity> activities = Activity.find.all();
	    List<BulletinPost> posts = BulletinPost.find.all();
	    
	    Calendar currentCal = Calendar.getInstance();
	    Calendar compareCal = Calendar.getInstance();
	    List<Activity> available = new ArrayList<Activity>();
	    
	    for(FitnessSchedule schedule : FitnessSchedule.find.all())
	    {
	    	compareCal.setTime(schedule.time_slot);
	    	if(compareCal.get(DateFormat.DAY_OF_WEEK_FIELD) == currentCal.get(DateFormat.DAY_OF_WEEK_FIELD)){
	    		System.out.println("we found an activity");
	    		available.add(schedule.activity);
	    	}
	    }
	    
	    
		return ok(index.render(scala.collection.JavaConversions.asScalaBuffer(activities),user,
        		scala.collection.JavaConversions.asScalaBuffer(posts),
        		scala.collection.JavaConversions.asScalaBuffer(available)));
	}
	
	public static Result findUser(String employee_id){
		
		ObjectNode result = Json.newObject();
		System.out.println("Employee ID " + employee_id);
		result.put("name", Json.toJson("User"));
		return ok(result);
	}
	
	
}

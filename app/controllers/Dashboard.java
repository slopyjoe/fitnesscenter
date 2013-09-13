package controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import models.Activity;
import models.BulletinPost;
import models.FitnessSchedule;
import models.Member;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

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
	
	public static Result getMessageBoard(){
		ObjectNode result = Json.newObject();
		List<BulletinPost> posts = BulletinPost.find.all();
		result.put("messages", Json.toJson(posts));
		System.out.println(result);
		return ok(result);
	}
	
	public static Result getAllActivities(){
		List<Activity> activities = Activity.find.all();
		ObjectNode result = Json.newObject();
		result.put("activities", Json.toJson(activities));
		return ok(result);
	}
	
	@Transactional
	public static Result enter() {
		Map<String,String[]> params;
	    params = request().body().asFormUrlEncoded();
	    String email = params.get("employee_id")[0];
	    Member user = Member.find.where().eq("employee_id", email).findUnique();
	    
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
	    
		List<Activity> activities = Activity.find.all();
	    List<BulletinPost> posts = BulletinPost.find.all();

		return ok(index.render(scala.collection.JavaConversions.asScalaBuffer(activities),user,
        		scala.collection.JavaConversions.asScalaBuffer(posts),
        		scala.collection.JavaConversions.asScalaBuffer(available)));
	}
	
	public static Result findUser(String employee_id){
		ObjectNode result = Json.newObject();
		Member member = Member.find.where().eq("employee_id", employee_id).findUnique();
		if(member != null)
		{
			JsonNode memberNode = Json.toJson(member);
			result.put("user", memberNode);
			System.out.println(member);
		}
		
		result.put("name", Json.toJson("User"));
		return ok(result);
	}

}

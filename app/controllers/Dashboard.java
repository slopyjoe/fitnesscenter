package controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import models.Activity;
import models.BulletinPost;
import models.FitnessSchedule;
import models.Member;
import models.MemberCount;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.dashboard.index;

public class Dashboard extends Controller{

	/*
	 * Only for testing purposes
	 */
	@Transactional
	public static void updateSchedule(){
		Activity act = Activity.find.where().eq("name", "Yoga").findUnique();
		FitnessSchedule schedule = null;
		if(act != null)
			schedule = FitnessSchedule.find.where().eq("activity", act).findUnique();
		
		if(schedule != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 30);
			schedule.time_slot = cal.getTime();
			schedule.save();
			
		}
	}
	
	public static Result index() {
    	List<Activity> activities = Activity.find.all();
	    List<BulletinPost> posts = BulletinPost.find.all();
	    updateSchedule();
    	return ok(index.render(scala.collection.JavaConversions.asScalaBuffer(activities),
        		scala.collection.JavaConversions.asScalaBuffer(posts)));
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
	
	
	public static void retrieveMemberCount(Member member, ObjectNode json){
		int day = 0;
		int week = 0;
		int month = 0;
		int total = 0;
		if(member != null)
		{
			List<MemberCount> memberActivity = MemberCount.find.where().eq("member", member).findList();
			total = memberActivity.size();
			
		}
			json.with("user").put("dayCount", day);
			json.with("user").put("weekCount", week);
			json.with("user").put("monthCount", month);
			json.with("user").put("totalCount", total);
		
	}
	
	@Transactional
	public static Result signIn(){
		JsonNode json = request().body().asJson();
	    String empId = json.findValue("empId").asText();
	    String activityName = json.findValue("activityName").asText();
	    Activity act = Activity.find.where().eq("name", activityName).findUnique();
	    Member member = Member.find.where().eq("employee_id", empId).findUnique();
	    MemberCount count = new MemberCount();
	    count.setMember(member);
	    count.setActivity(act);
	    count.setTimestamp(Calendar.getInstance().getTime());
	    count.save();
		return ok("Member Signed In");
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

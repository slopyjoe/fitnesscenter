package controllers;

import java.text.DateFormat;
import java.util.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
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

//TODO Update to WebSocket
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
    	return ok(index.render());
    }
	
	public static Result getMessageBoard(){
		ObjectNode result = Json.newObject();
		List<BulletinPost> posts = BulletinPost.find.all();
		result.put("messages", Json.toJson(posts));
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
			json.with("counts").put("dayCount", day);
			json.with("counts").put("weekCount", week);
			json.with("counts").put("monthCount", month);
			json.with("counts").put("totalCount", total);
		
	}
	
	@Transactional
	public static Result signIn(){
		JsonNode json = request().body().asJson();
        JsonNode memberNode = json.findValue("user");

        if(memberNode != null)
        {
            Member member = Json.fromJson(memberNode,Member.class);

            String activityName = json.findValue("activityName").asText();
            Activity act = Activity.find.where().eq("name", activityName).findUnique();
            MemberCount count = new MemberCount();
            count.setMember(member);
            count.setActivity(act);
            count.setTimestamp(Calendar.getInstance().getTime());
            count.save();


        }
		return ok("Member Signed In");
    }
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

		return ok(index.render());
	}

    public static Result createMember(){
        JsonNode json = request().body().asJson();
        JsonNode memberNode = json.findValue("user");

        if(memberNode != null)
        {
            Member newMember = Json.fromJson(memberNode,Member.class);
            Date date = Calendar.getInstance().getTime();
            newMember.setActive(true);
            newMember.setDob(date);
            newMember.setLastLoggedIn(date);
            newMember.save();
        }
        return ok("Member saved");
    };

    /**
     * Notes : it would be nice to look into FutureList when this
     * application has more than one instance running.
     * @return
     */
	public static Result findUser(){
        Map<String,String[]> params;

        params = request().queryString();
        ObjectNode result = Json.newObject();
        Map<String, Object> allEq = new HashMap<String, Object>();
        allEq.put("employee_id", params.get("empId")[0]);

        if(params.containsKey("organization") && ! params.get("organization")[0].equals(""))
        {
            allEq.put("organization",params.get("organization")[0]);
        }
        List<Member> uniqueMembers = Member.find.where().allEq(allEq).findList();

        boolean found = !uniqueMembers.isEmpty();
        if(uniqueMembers.size() > 1)
        {
            result.with("multipleUsers").put("users", Json.toJson(uniqueMembers));
        }
        else if(uniqueMembers.size() > 0)
        {
            result = getMemberReport(uniqueMembers.get(0));
        }
        result.put("found", found);




		return ok(result);
	}


    @Transactional
	public static ObjectNode getMemberReport(Member member){
        ObjectNode result = Json.newObject();
        result.put("user", Json.toJson(member));
        result.put("defaultActivity", Json.toJson((Activity.getDefault())));
        result.put("activities",Json.toJson(Activity.staticClasses()));
        result.put("classes",Json.toJson(getCurrentActivities()));
        retrieveMemberCount(member, result);
        return result;
    }

    @Transactional
    public static List<Activity> getCurrentActivities(){
        List<Activity> available = new ArrayList<Activity>();
        Calendar currentCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();

        for(FitnessSchedule schedule : FitnessSchedule.find.all())
        {
            compareCal.setTime(schedule.time_slot);
            if(compareCal.get(DateFormat.DAY_OF_WEEK_FIELD) == currentCal.get(DateFormat.DAY_OF_WEEK_FIELD)){
                available.add(schedule.activity);
            }
        }
        return available;
    }
}

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;

import models.Activity;
import models.BulletinPost;
import models.FitnessSchedule;
import models.Member;
import models.MemberCount;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		InitialData.insert(app);
	}

	static class InitialData {

		public static void insert(Application app) {
			if (Ebean.find(Member.class).findRowCount() == 0) {

				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
						.load("initial-data.yml");

				// Insert users first
				Ebean.save(all.get("Members"));
				
				

			}
			
			if(Ebean.find(Activity.class).findRowCount() == 0) {
				Member jane = Member.find.byId("jane@sample.com");
				Member scooby = Member.find.byId("scooby@sample.com");
				if(jane != null && scooby != null){
					
					Activity act = new Activity();
					
					act.setDescription("General Fitness center, weights and cardio");
					act.setName("Exercise Room");
					act.setHasSchedule(false);
                    act.setDefaultActivity(true);
					act.setInstructor(jane);
					act.save();
					
					Activity yoga = new Activity();
					yoga.setDescription("Stretching and core building.");
					yoga.setName("Yoga");
					yoga.setHasSchedule(true);
                    act.setDefaultActivity(false);
					yoga.setInstructor(jane);
					yoga.save();
					
					Activity boxing = new Activity();
					boxing.setDescription("Punching and kicking");
					boxing.setName("Cardio Boxing");
					boxing.setHasSchedule(true);
                    act.setDefaultActivity(false);
					boxing.setInstructor(scooby);
					boxing.save();
					
					act = new Activity();
					act.setDescription("Court for basketball");
					act.setName("BasketBall");
					act.setHasSchedule(false);
                    act.setDefaultActivity(false);
					act.setInstructor(jane);
					act.save();
					
					act = new Activity();
					act.setDescription("Changing room, lockers, restrooms, and showers");
					act.setName("Locker Room");
					act.setHasSchedule(false);
                    act.setDefaultActivity(false);
					act.setInstructor(jane);
					act.save();
					
					Calendar cal = Calendar.getInstance();
					
					cal.set(2000, 02, 24, 11, 30, 0); 
					//cal.setWeekDate(2000, 1, 3);
					FitnessSchedule schedule = new FitnessSchedule(); 
					schedule.activity = boxing;
					schedule.difficulty = FitnessSchedule.Difficulty.EASY.toString();
					schedule.time_slot = cal.getTime();
					 
					schedule.save();
					cal.set(2000, 02, 24, 5, 0, 0); 
					//cal.setWeekDate(2000, 1, 4);
					schedule = new FitnessSchedule(); 
					schedule.activity = yoga;
					schedule.difficulty = FitnessSchedule.Difficulty.EASY.toString();
					schedule.time_slot = cal.getTime();
					schedule.save();
					
					cal = Calendar.getInstance();
					MemberCount memberCount = new MemberCount();
					memberCount.setMember(scooby);
					memberCount.setActivity(boxing);
					memberCount.setTimestamp(cal.getTime());
					memberCount.save();
					
					cal.add(Calendar.DAY_OF_MONTH, -7);
					
					memberCount = new MemberCount();
					memberCount.setActivity(yoga);
					memberCount.setMember(scooby);
					memberCount.setTimestamp(cal.getTime());
					memberCount.save();
					
					
				}
				
				if(Ebean.find(BulletinPost.class).findRowCount() == 0){
					BulletinPost post = new BulletinPost();
					post.setImagePath("bloodDrive.jpg");
					post.setName("Blood Drive");
					post.setMessage("Today is the local blood drive");
					post.save();
				
					
				}
				
					
			}
		}

	}

}
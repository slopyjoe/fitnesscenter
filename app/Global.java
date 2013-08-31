import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;

import models.Activity;
import models.BulletinPost;
import models.FitnessSchedule;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		InitialData.insert(app);
	}

	static class InitialData {

		public static void insert(Application app) {
			if (Ebean.find(User.class).findRowCount() == 0) {

				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
						.load("initial-data.yml");

				// Insert users first
				Ebean.save(all.get("users"));
				
				

			}
			
			if(Ebean.find(Activity.class).findRowCount() == 0) {
				User jane = User.find.byId("jane@sample.com");
				User scooby = User.find.byId("scooby@sample.com");
				if(jane != null && scooby != null){
					
					Activity act = new Activity();
					
					act.setDescription("General Fitness center, weights and cardio");
					act.setName("Exercise Room");
					act.setHasSchedule(false);
					act.setInstructor(jane);
					act.save();
					
					act = new Activity();
					act.setDescription("Stretching and core building.");
					act.setName("Yoga");
					act.setHasSchedule(false);
					act.setInstructor(jane);
					act.save();
					
					act = new Activity();
					act.setDescription("Punching and kicking");
					act.setName("Cardio Boxing");
					act.setHasSchedule(true);
					act.setInstructor(scooby);
					act.save();
					
					act = new Activity();
					act.setDescription("Court for basketball");
					act.setName("BasketBall");
					act.setHasSchedule(false);
					act.setInstructor(jane);
					act.save();
					
					act = new Activity();
					act.setDescription("Changing room, lockers, restrooms, and showers");
					act.setName("Locker Room");
					act.setHasSchedule(false);
					act.setInstructor(jane);
					act.save();
					
					Calendar cal = Calendar.getInstance();
					
					cal.set(2000, 02, 24, 11, 30, 0); 
					//cal.setWeekDate(2000, 1, 3);
					FitnessSchedule schedule = new FitnessSchedule(); 
					schedule.activity = act;
					schedule.difficulty = FitnessSchedule.Difficulty.EASY.toString();
					schedule.time_slot = cal.getTime();
					 
					schedule.save();
					cal.set(2000, 02, 24, 5, 0, 0); 
					//cal.setWeekDate(2000, 1, 4);
					schedule = new FitnessSchedule(); 
					schedule.activity = act;
					schedule.difficulty = FitnessSchedule.Difficulty.EASY.toString();
					schedule.time_slot = cal.getTime();
					schedule.save();
					
				}
				
				if(Ebean.find(BulletinPost.class).findRowCount() == 0){
					BulletinPost post = new BulletinPost();
					post.setImagePath("1.jpg");
					post.setName("Blood Drive");
					post.setMessage("Today is the local blood drive");
					post.save();
					
					post = new BulletinPost();
					post.setImagePath("1.jpg");
					post.setName("Nutrition Tip");
					post.setMessage("Don't eat the greasy stuff.");
					post.save();
					
					post = new BulletinPost();
					post.setImagePath("2.jpg");
					post.setName("Fit Tip");
					post.setMessage("Run and do cardio stuff");
					post.save();
					
				}
					
				
			}

			/*
			 * if(User.find.byId("fake@email.com") == null){ Calendar cal =
			 * Calendar.getInstance();
			 * 
			 * User user = new User(); user.setFirstName("First");
			 * user.setLastName("Last"); user.setEmail("fake@email.com");
			 * 
			 * cal.set(1987, 4, 12); user.setDob(cal.getTime());
			 * 
			 * user.save();
			 * 
			 * 
			 * Activity activity = new Activity();
			 * activity.setDescription("This is an activity"); activity.name =
			 * "Activiy"; activity.instructor = user;
			 * 
			 * activity.save();
			 * 
			 * cal.set(2000, 02, 24, 11, 30, 0); cal.setWeekDate(2000, 1, 3);
			 * System.out.println(cal.toString()); FitnessSchedule schedule =
			 * new FitnessSchedule(); schedule.activity = activity;
			 * schedule.difficulty = FitnessSchedule.Difficulty.EASY.toString();
			 * schedule.time_slot = cal.getTime();
			 * 
			 * schedule.save();
			 * 
			 * activity = new Activity();
			 * activity.setDescription("This is another activity");
			 * activity.name = "New Activity"; activity.instructor = user;
			 * 
			 * activity.save();
			 * 
			 * cal.set(2000, 02, 24, 5, 0, 0); cal.setWeekDate(2000, 1, 3);
			 * schedule = new FitnessSchedule(); schedule.activity = activity;
			 * schedule.difficulty = FitnessSchedule.Difficulty.EASY.toString();
			 * schedule.time_slot = cal.getTime();
			 * 
			 * schedule.save();
			 * 
			 * }
			 */
		}

	}

}
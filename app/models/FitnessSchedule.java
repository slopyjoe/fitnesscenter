package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.format.Formats.DateTime;
import play.db.ebean.Model;

@Entity
public class FitnessSchedule extends Model{

	public enum Difficulty{
		EASY,
		MEDIUM,
		HARD;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long internal_id;
	
	@OneToOne
	public Activity activity;
	
	@DateTime(pattern="MM-dd-yyyy HH:mm:ss")
	public Date time_slot;
	
	public String difficulty;
	
	public String validate(){
		try{
			Difficulty.valueOf(difficulty);
			return null;
		}catch(IllegalArgumentException iae){
			return "Bad Difficulty: " + difficulty;
		}
	}
	
	public static Finder<Long, FitnessSchedule> find = new Finder<Long, FitnessSchedule>(Long.class, FitnessSchedule.class);
	
	public static List<Activity> getCurrentActivities(){
		List<Activity> activities = new ArrayList<Activity>();
		Calendar cal = Calendar.getInstance();
		Calendar scheduleTime = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int hour = cal.get(Calendar.HOUR);
		for(FitnessSchedule schedule : find.all()){
			scheduleTime.setTime(schedule.time_slot);
			if(scheduleTime.get(Calendar.DAY_OF_WEEK) == dayOfWeek){
				activities.add(schedule.activity);
			}
		}
		return activities;
	}
	
}

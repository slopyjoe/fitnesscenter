package models;

import java.util.Date;

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
	
	
}

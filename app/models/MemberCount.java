package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class MemberCount extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long internal_id;
	
	private Date timestamp;
	
	@OneToOne
	private Activity activity;
	
	@OneToOne
	private Member member;

	public static Finder<Long, MemberCount> find = new Finder<Long, MemberCount>(Long.class, MemberCount.class);
	
	public Long getInternal_id() {
		return internal_id;
	}

	public void setInternal_id(Long internal_id) {
		this.internal_id = internal_id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	
}

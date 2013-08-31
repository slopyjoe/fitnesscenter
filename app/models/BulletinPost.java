package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class BulletinPost extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long internal_id;
	
	private String message;
	
	private String imagePath;
	
	private String name;
	
	public static Finder<Long, BulletinPost> find = new Finder<Long, BulletinPost>(Long.class, BulletinPost.class);

	public Long getInternal_id() {
		return internal_id;
	}

	public void setInternal_id(Long internal_id) {
		this.internal_id = internal_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}

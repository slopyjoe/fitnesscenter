package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Page;
import play.data.validation.Constraints;

@Entity
public class Activity extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4946634082511994210L;

	@Id
	public Long id;

	@Constraints.Required
	public String name;

	@Constraints.Required
	public String description;

	@ManyToOne
	public Employee employee;


	public Activity(){}
	
	public Activity(String name, String description, Employee employee)
	{
		this.name = name; 
		this.description = description;
		this.employee = employee;
	}
	
	public static Finder<Long, Activity> find = new Finder<Long, Activity>(
			Long.class, Activity.class);

	/**
	 * Return a page of computer
	 * 
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 * @param sortBy
	 *            Computer property used for sorting
	 * @param order
	 *            Sort order (either or asc or desc)
	 * @param filter
	 *            Filter applied on the name column
	 */
	public static Page<Activity> page(int page, int pageSize, String sortBy,
			String order, String filter) {
		return find.where().ilike("name", "%" + filter + "%")
				.orderBy(sortBy + " " + order).fetch("employee").findPagingList(pageSize)
				.getPage(page);
	}

	public static void main(String args[]) {
		String temp = "insert into activity (id, name, description, employee_id)"
				+ " values (%d,\'Name%d\',\'This is a class on apples.\',%d);";

		for (int i = 0; i < 51; i++) {
			System.out.println(String.format(temp, i, i, i));
		}
	}
}

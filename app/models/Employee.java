package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
public class Employee extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6610635190727694126L;

	@Id
	public Long id;

	@Constraints.Required
	public String role;
	
	public Person person;
	
	public static Finder<Long, Employee> find = new Finder<Long, Employee>(
			Long.class, Employee.class);

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
	public static Page<Employee> page(int page, int pageSize, String sortBy,
			String order, String filter) {
		return find.where()
				.ilike("name", "%" + filter + "%")
				.orderBy(sortBy + " " + order)
				.fetch("person")
				.findPagingList(pageSize)
				.getPage(page);
	}

	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Employee e: Employee.find.orderBy("name").findList()) {
            options.put(e.id.toString(), e.person.name);
        }
        return options;
    }
	
	public static void main(String args[]) {
		String temp = "insert into employee (id, role, person_id)"
				+ " values (%d,\'Admin\',%d);";

		for (int i = 0; i < 51; i++) {
			System.out.println(String.format(temp, i, i));
		}
	}
}

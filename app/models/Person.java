package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
public class Person extends Model {

	/**
	 * Auto-generated UID by eclipse
	 */
	private static final long serialVersionUID = -6804651384495187160L;

	@Id
	public Long Id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String email;
	
    @Formats.DateTime(pattern="MM-dd-yyyy")
    @Constraints.Required
    public Date dob;
    
    public static Finder<Long, Person> find = new Finder<Long, Person>(
			Long.class, Person.class);

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
	public static Page<Person> page(int page, int pageSize, String sortBy,
			String order, String filter) {
		return find.where()
				.ilike("name", "%" + filter + "%")
				.orderBy(sortBy + " " + order)
				.findPagingList(pageSize)
				.getPage(page);
	}
	
	public static void main (String args[]){
    	String temp = "insert into person (id, name, email, dob)" +
    			" values (%d,\'name%d\',\'fake@email.com\', \'1984-01-24\');";
    	
    	for (int i = 0; i < 51; i++)
    	{
    		System.out.println(String.format(temp, i,i));
    	}
    }
}

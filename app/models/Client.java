package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
public class Client extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2483643054777982784L;

	@Id
	public Long id;
	
	@Constraints.Required
	public String empId;
	
	@Constraints.Required
	public String organization;
	
	@OneToOne
	public Person person;
	
	public static Finder<Long, Client> find = new Finder<Long, Client>(Long.class, Client.class);
	
	public static Client findClient(String empId){
		return find.where().eq("empId", empId).findUnique();
	}
	
	
	/**
     * Return a page of computer
     *
     * @param page Page to display
     * @param pageSize Number of computers per page
     * @param sortBy Computer property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Client> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.orderBy(sortBy + " " + order)
                .fetch("person")
                .findPagingList(pageSize)
                .getPage(page);
    }
    
    public static void main (String args[]){
    	String temp = "insert into client (id, emp_id, organization, person_id)" +
    			" values (%d,\'123%d\',\'LM\',%d);";
    	
    	for (int i = 0; i < 51; i++)
    	{
    		System.out.println(String.format(temp, i,i,i));
    	}
    }
}

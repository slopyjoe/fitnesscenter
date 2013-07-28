package controllers;

import static play.data.Form.form;
import models.Employee;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.employees.createForm;
import views.html.employees.editForm;
import views.html.employees.list;

public class Employees extends Controller{

	
	/**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Employees.list(0, "name", "asc", "")
    );
	
	public static Result index(){
		return GO_HOME;
	}
	
	/**
     * Display the paginated list of computers.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on computer names
     */
    public static Result list(int page, String sortBy, String order, String filter) {
        
    	return ok(
            list.render(
            	Employee.page(page, 10, sortBy, order, filter),
            	sortBy, order, filter
                    )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Employee.
     *
     * @param id Id of the computer to edit
     */
    public static Result edit(Long id) {
        Form<Employee> computerForm = form(Employee.class).fill(
            Employee.find.byId(id)
        );
        return ok(
            editForm.render(id, computerForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the computer to edit
     */
    public static Result update(Long id) {
        Form<Employee> clientForm = form(Employee.class).bindFromRequest();
        if(clientForm.hasErrors()) {
            return badRequest(editForm.render(id, clientForm));
        }
        clientForm.get().update(id);
        flash("success", "Employee " + clientForm.get().person.name + " has been updated");
        return GO_HOME;
    }
    
    /**
     * Display the 'new computer form'.
     */
    public static Result create() {
        Form<Employee> clientForm = form(Employee.class);
        return ok(
            createForm.render(clientForm)
        );
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    public static Result save() {
    	System.out.println("Saving something");
        Form<Employee> clientForm = form(Employee.class).bindFromRequest();
        if(clientForm.hasErrors()) {
        	System.out.println(clientForm.errorsAsJson().toString());
            return badRequest(createForm.render(clientForm));
        }
        clientForm.get().save();
        flash("success", "Employee " + clientForm.get().person.name + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle computer deletion
     */
    public static Result delete(Long id) {
        Employee.find.ref(id).delete();
        flash("success", "Employee has been deleted");
        return GO_HOME;
    }
}

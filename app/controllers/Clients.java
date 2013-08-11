package controllers;

import static play.data.Form.form;
import models.Client;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.clients.createForm;
import views.html.clients.editForm;
import views.html.clients.list;
public class Clients extends Controller{

	
	/**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Clients.list(0, "name", "asc", "")
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
                Client.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Client.
     *
     * @param id Id of the computer to edit
     */
    public static Result edit(Long id) {
    	Client cl = Client.find.byId(id);
    	if(cl == null)
    	{
    		System.out.println("Null client");
    		
    	}else if(cl.organization.equals("")){
    		System.out.println("empty name");
    	}else
    	{
    		System.out.println(cl.organization );
    	}
        Form<Client> computerForm = form(Client.class).fill(
            Client.find.byId(id)
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
        Form<Client> clientForm = form(Client.class).bindFromRequest();
        if(clientForm.hasErrors()) {
            return badRequest(editForm.render(id, clientForm));
        }
        clientForm.get().update(id);
        flash("success", "Client " + clientForm.get().person.name + " has been updated");
        return GO_HOME;
    }
    
    /**
     * Display the 'new client form'.
     */
    public static Result create() {
        Form<Client> clientForm = form(Client.class);
        return ok(
            createForm.render(clientForm)
        );
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    public static Result save() {
    	System.out.println("Saving something");
        Form<Client> clientForm = form(Client.class).bindFromRequest();
        if(clientForm.hasErrors()) {
        	System.out.println(clientForm.errorsAsJson().toString());
            return badRequest(createForm.render(clientForm));
        }
        clientForm.get().save();
        flash("success", "Client " + clientForm.get().person.name + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle computer deletion
     */
    public static Result delete(Long id) {
        Client.find.ref(id).delete();
        flash("success", "Client has been deleted");
        return GO_HOME;
    }
    
    
    
}

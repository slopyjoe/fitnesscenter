package controllers;

import static play.data.Form.form;
import models.Activity;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.activities.createForm;
import views.html.activities.editForm;
import views.html.activities.list;

/**
 * Manage a database of computers
 */
public class Activities extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Activities.list(0, "name", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to computers list
     */
    public static Result index() {
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
                Activity.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     */
    public static Result edit(Long id) {
        Form<Activity> activityForm = form(Activity.class).fill(
        		Activity.find.byId(id)
        );
        return ok(
            editForm.render(id, activityForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the computer to edit
     */
    public static Result update(Long id) {
        Form<Activity> activityForm = form(Activity.class).bindFromRequest();
        if(activityForm.hasErrors()) {
            return badRequest(editForm.render(id, activityForm));
        }
        activityForm.get().update(id);
        flash("success", "Activity " + activityForm.get().name + " has been updated");
        return GO_HOME;
    }
    
    /**
     * Display the 'new computer form'.
     */
    public static Result create() {
        Form<Activity> activityForm = form(Activity.class);
        return ok(
            createForm.render(activityForm)
        );
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    public static Result save() {
        Form<Activity> activityForm = form(Activity.class).bindFromRequest();
        if(activityForm.hasErrors()) {
            return badRequest(createForm.render(activityForm));
        }
        activityForm.get().save();
        flash("success", "Activity " + activityForm.get().name + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle computer deletion
     */
    public static Result delete(Long id) {
    	Activity.find.ref(id).delete();
        flash("success", "Activity has been deleted");
        return GO_HOME;
    }
    

}
            

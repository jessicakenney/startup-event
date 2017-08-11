import models.Event;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class App {

  public static void main(String[] args) {
    staticFileLocation ("/public");

    ArrayList<String> names1 = new ArrayList<String>(Arrays.asList("jessica","bart","beth","esti","evan"));
    ArrayList<String> names2 = new ArrayList<String>(Arrays.asList("kateb","kates","kimberly","stephanie","shy"));
    ArrayList<String> names3 = new ArrayList<String>(Arrays.asList("collin","carson","maria","paul","ryan"));
    Event event3 = new Event("What is Blockchain","what is",names1);
    Event event1 = new Event("Blockchain Security","description security",names2);
    Event event2 = new Event("Blockchain Architecture","description architecture",names3);

   //get Dashboard homepage, shows all events
    get ("/events", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      ArrayList<Event> events = Event.getAll();
      model.put("events", events);
      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());

    //get Startup Weekend Event Page
    get ("/", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "event.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show new Event form
    get("/events/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "event-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process new Event form
    post("/events/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      ArrayList<String> names = new ArrayList<String>(Arrays.asList());
      Event newEvent = new Event(name,description,names);
      model.put("event", newEvent);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show an individual Event
    get("/events/:id", (request, response) ->  {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("id"));
      Event findEvent = Event.findById(eventId);
      model.put("event", findEvent);
      return new ModelAndView(model, "event-detail.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show a form to update a Event
    get("/events/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("id"));
      Event editEvent = Event.findById(eventId);
      model.put("editEvent", editEvent);
      return new ModelAndView(model, "event-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process a form to update Event
    post("/events/:id/update", (request,response) -> {
      Map<String, Object> model = new HashMap<>();
      String newName = request.queryParams("name");
      String newDescription = request.queryParams("description");
      int eventId = Integer.parseInt(request.params("id"));
      Event editEvent = Event.findById(eventId);
      editEvent.updateName(newName);
      editEvent.updateDescription(newDescription);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show a form to add Attendees for Event
    get("/events/:id/addAttendee", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("id"));
      Event addAttendee = Event.findById(eventId);
      model.put("addAttendee",addAttendee);
      return new ModelAndView(model, "event-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process a form to add Attendee
    post("/events/:id/addAttendee", (request,response) -> {
      Map<String, Object> model = new HashMap<>();
      String newAttendee = request.queryParams("attendee");
      int eventId = Integer.parseInt(request.params("id"));
      Event editEvent = Event.findById(eventId);
      editEvent.addAttendee(newAttendee);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

       // running out of time to add remove attendees functionality
//    //post: process a form to remove attendees
//    post("/events/:id", (request,response) -> {
//      Map<String, Object> model = new HashMap<>();
//      String removeAttendee = request.queryParams("attendee");
//      int eventId = Integer.parseInt(request.params("id"));
//      Event editEvent = Event.findById(eventId);
//      System.out.println("REMOVE"+removeAttendee);
//      editEvent.deleteAttendee(removeAttendee);
//      return new ModelAndView(model, "success.hbs");
//    }, new HandlebarsTemplateEngine());


  }

}

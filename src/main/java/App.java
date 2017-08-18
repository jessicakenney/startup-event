import dao.EventDao;
import dao.Sql2oAttendeeDao;
import dao.Sql2oEventDao;
import models.Event;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class App {

  public static void main(String[] args) {
    staticFileLocation ("/public");
    String connectionString = "jdbc:h2:~/startup.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    Sql2oEventDao eventDao = new Sql2oEventDao(sql2o);
    Sql2oAttendeeDao attendeeDao = new Sql2oAttendeeDao(sql2o);


//    ArrayList<String> names1 = new ArrayList<String>(Arrays.asList("Jessica","Bart","Beth","Esti","Evan"));
//    ArrayList<String> names2 = new ArrayList<String>(Arrays.asList("Kateb","Kates","Kimberly","Stephanie","Shy"));
//    ArrayList<String> names3 = new ArrayList<String>(Arrays.asList("Collin","Carson","Maria","Paul","Ryan"));
//    Event event3 = new Event("What is Blockchain?","Let's talk about what a ‘distributed, decentralised transaction ledger’ really means.",names1);
//    Event event1 = new Event("Blockchain Security","How to prevent the threat of hacking.",names2);
//    Event event2 = new Event("Blockchain Architecture","Legos and Blockchain.",names3);

    //get Startup Weekend Event Page
    get ("/", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "event.hbs");
    }, new HandlebarsTemplateEngine());

   //get Dashboard homepage, shows all events
    get ("/events", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      List<Event> events = eventDao.getAll();
      model.put("events", events);
      return new ModelAndView(model, "index.hbs");
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
      String date = request.queryParams("date");
      Event newEvent = new Event(name,description,date);
      eventDao.add(newEvent);
      model.put("event", newEvent);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show an individual Event
    get("/events/:id", (request, response) ->  {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("id"));
      Event findEvent = eventDao.findById(eventId);
      model.put("event", findEvent);
      return new ModelAndView(model, "event-detail.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show a form to update a Event
    get("/events/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("id"));
      Event editEvent = eventDao.findById(eventId);
      model.put("editEvent", editEvent);
      return new ModelAndView(model, "event-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process a form to update Event
    post("/events/:id/update", (request,response) -> {
      Map<String, Object> model = new HashMap<>();
      String newName = request.queryParams("name");
      String newDescription = request.queryParams("description");
      String newDate = request.queryParams("date");
      int eventId = Integer.parseInt(request.params("id"));
      eventDao.update(eventId,newName,newDescription,newDate);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

//    //get: show a form to add Attendees for Event
//    get("/events/:id/addAttendee", (request, response) -> {
//      Map<String, Object> model = new HashMap<>();
//      int eventId = Integer.parseInt(request.params("id"));
//      Event addAttendee = Event.findById(eventId);
//      model.put("addAttendee",addAttendee);
//      return new ModelAndView(model, "event-form.hbs");
//    }, new HandlebarsTemplateEngine());
//
//    //post: process a form to add Attendee
//    post("/events/:id/addAttendee", (request,response) -> {
//      Map<String, Object> model = new HashMap<>();
//      String newAttendee = request.queryParams("attendee");
//      int eventId = Integer.parseInt(request.params("id"));
//      Event editEvent = Event.findById(eventId);
//      editEvent.addAttendee(newAttendee);
//      return new ModelAndView(model, "success.hbs");
//    }, new HandlebarsTemplateEngine());

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

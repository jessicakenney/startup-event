import dao.EventDao;
import dao.Sql2oAttendeeDao;
import dao.Sql2oEventDao;
import models.Attendee;
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

    //get: delete all Events
    get("/events/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      eventDao.clearAllEvents();
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: delete all Attendees of All Events
    get("/attendees/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      attendeeDao.clearAllAttendees();
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: delete all attendees of a specific event
    get("/events/:event_id/attendees/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(req.params("event_id"));
      List<Attendee> attendees = attendeeDao.getAll();
      for ( Attendee attendee : attendees) {
        if (attendee.getEventId() == eventId) {
          System.out.println("DEBUG___DELETING"+eventId);
          attendeeDao.deleteById(eventId);
        }
      }
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

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

    //get shows all attendees (independent of event)
    get ("/attendees", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      List<Attendee> attendees = attendeeDao.getAll();
      model.put("attendees", attendees);
      return new ModelAndView(model, "attendee-index.hbs");
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
    get("/events/:event_id", (request, response) ->  {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("event_id"));
      Event findEvent = eventDao.findById(eventId);
      model.put("event", findEvent);

      //include also all the attendees for specific event
      List<Attendee> eventAttendees= new ArrayList<>();
      List<Attendee> attendees = attendeeDao.getAll();
      for ( Attendee attendee : attendees) {
        if (attendee.getEventId() == eventId) {
          eventAttendees.add(attendee);
        }
      }
      model.put("attendees", eventAttendees);
      return new ModelAndView(model, "event-detail.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show a form to update a Event
    get("/events/:event_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("event_id"));
      Event editEvent = eventDao.findById(eventId);
      model.put("editEvent", editEvent);
      return new ModelAndView(model, "event-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process a form to update Event
    post("/events/:event_id/update", (request,response) -> {
      Map<String, Object> model = new HashMap<>();
      String newName = request.queryParams("name");
      String newDescription = request.queryParams("description");
      String newDate = request.queryParams("date");
      int eventId = Integer.parseInt(request.params("event_id"));
      eventDao.update(eventId,newName,newDescription,newDate);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: delete an Individual event
    get("/events/:event_id/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfEventToDelete = Integer.parseInt(req.params("event_id"));
      eventDao.deleteById(idOfEventToDelete);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show a form to add Attendees for Event
    get("/events/:event_id/attendees", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("event_id"));
      Event addAttendee = eventDao.findById(eventId);
      model.put("addAttendee",addAttendee);
      return new ModelAndView(model, "event-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process a form to add Attendee
    post("/events/:event_id/attendees", (request,response) -> {
      Map<String, Object> model = new HashMap<>();
      String name = request.queryParams("name");
      int eventId = Integer.parseInt(request.params("event_id"));
      Attendee newAttendee = new Attendee(name,eventId);
      attendeeDao.add(newAttendee);
      model.put("attendee", newAttendee);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show an individual Attendee within Event
    get("/events/:event_id/attendees/:attendee_id", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfAttendeeToFind = Integer.parseInt(req.params("attendee_id"));
      Attendee foundAttendee = attendeeDao.findById(idOfAttendeeToFind);
      model.put("attendee", foundAttendee);
      return new ModelAndView(model, "attendee-detail.hbs");
    }, new HandlebarsTemplateEngine());

    /// in progress....
    //get: show a form to update an Attendee
    get("/attendees/:attendee_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.params("attendee_id"));
      Attendee editAttendee = attendeeDao.findById(eventId);
      model.put("editAttendee",editAttendee);
      return new ModelAndView(model, "attendee-form.hbs");
    }, new HandlebarsTemplateEngine());

    //post: process a form to update  an Attendee
    post("/attendees/:attendee_id/update", (request,response) -> {
      Map<String, Object> model = new HashMap<>();
      String newName = request.queryParams("name");
      int attendeeId = Integer.parseInt(request.params("attendee_id"));
      attendeeDao.update(attendeeId,newName);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());

    //get: delete an Attendee of a specific event
    get("/attendees/:attendee_id/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfAttendeeToDelete = Integer.parseInt(req.params("attendee_id"));
      attendeeDao.deleteById(idOfAttendeeToDelete);
      return new ModelAndView(model, "success.hbs");
    }, new HandlebarsTemplateEngine());




  }

}

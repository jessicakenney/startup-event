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

    Event event1 = new Event("What is Blockchain?", "Let's talk about what a 'distrubuted, decentralised transaction ledger’ really means.", "2017-10-22");
    Event event2 = new Event("Blockchain Security", "How to prevent the threat of hacking.", "2017-10-22");
    Event event3 = new Event("Blockchain Architecture", "Legos and Blockchain.", "2017-10-23");

    eventDao.add(event1);
    eventDao.add(event2);
    eventDao.add(event3);

    Attendee attendee0 = new Attendee("Ann", 1);
    Attendee attendee1 = new Attendee("Jessica", 1);
    Attendee attendee2 = new Attendee( "Bart", 2);
    Attendee attendee3 = new Attendee( "Kate", 3);
    Attendee attendee4 = new Attendee( "Barry", 1);

    attendeeDao.add(attendee0);
    attendeeDao.add(attendee1);
    attendeeDao.add(attendee2);
    attendeeDao.add(attendee3);
    attendeeDao.add(attendee4);

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
          int attendeeIdToDelete = attendee.getId();
          attendeeDao.deleteById(attendeeIdToDelete);
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

    //get shows all attendees ordered by Name
    get ("/attendeesbyname", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      Map<String, String> attendeesEventName = new LinkedHashMap<>();
      List<Attendee> attendees = attendeeDao.getAllNameOrdered();
      for (Attendee attendee : attendees) {
        attendeesEventName.put( attendee.getName(), eventDao.findById(attendee.getEventId()).getName() );
      }
      model.put("attendeesbyname", attendeesEventName);
      return new ModelAndView(model, "attendee-index.hbs");
    }, new HandlebarsTemplateEngine());

    //get shows all attendees ordered by Event
    get ("/attendeesbyevent", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      Map<String, String> attendeesEventName = new LinkedHashMap<>();
      List<Attendee> attendees = attendeeDao.getAllEventOrdered();
      for (Attendee attendee : attendees) {
        attendeesEventName.put( attendee.getName(), eventDao.findById(attendee.getEventId()).getName() );
      }
      model.put("attendeesbyevent", attendeesEventName);
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
      List<Attendee> eventAttendees = eventDao.getAllAttendeesByEvent(eventId);
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
      Event event = eventDao.findById(eventId);
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

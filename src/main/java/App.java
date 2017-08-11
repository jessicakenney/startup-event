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

   //get Dashboard homepage, shows all events
    get ("/events", (req, resp) -> {
      Map<String, Object> model = new HashMap<>();
      ArrayList<Event> events = Event.getAll();
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
      ArrayList<String> names = new ArrayList<String>(Arrays.asList("name1","name2","name3"));
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







  }

}

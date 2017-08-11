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

   //get Dashboard homepage
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


  }

}

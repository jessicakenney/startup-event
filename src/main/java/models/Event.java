package models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event {
  private String name;
  private String description;
  private ArrayList<String> attendees;
  private static ArrayList<Event> instances = new ArrayList<>();
  public Event(String name, String description, ArrayList<String>attendees) {
    this.name = name;
    this.description = description;
    this.attendees = attendees;
    instances.add(this);
  }

  public String getName(){
    return name;
  }
  public String getDescription(){
    return description;
  }
  public ArrayList<String> getAttendees(){
    return attendees;
  }

  public static ArrayList<Event> getAll() {
    return instances;
  }
}


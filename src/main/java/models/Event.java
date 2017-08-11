package models;

import java.util.ArrayList;

public class Event {
  private String name;
  private String description;
  private ArrayList<String> attendees;

  public Event(String name, String description, ArrayList<String>attendees) {
    this.name = name;
    this.description = description;
    this.attendees = attendees;
  }

  public String getName(){
    return name;
  }

}


package models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event {
  private String name;
  private String description;
  private String date;
  private int id;

  public Event(String name, String description, String date ) {
    this.name = name;
    this.description = description;
    this.date = date;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName(){
    return name;
  }

  public String getDescription(){
    return description;
  }

  public int getId(){
    return id;
  }

  public String getDate(){
    return date;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Event event = (Event) o;

    if (id != event.id) return false;
    if (!name.equals(event.name)) return false;
    if (!description.equals(event.description)) return false;
    return date.equals(event.date);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + description.hashCode();
    result = 31 * result + date.hashCode();
    result = 31 * result + id;
    return result;
  }
}


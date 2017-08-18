package dao;

import models.Event;

import java.util.List;

public interface EventDao {

  //create
  void add (Event event);

  //read
  List<Event> getAll();
  Event findById(int id);
  //List<Attendee> findAttendeesById(int id);

  //update
  void update(int id, String name, String description, String date);

  //delete
  void deleteById(int id);
  void clearAllEvents();

}

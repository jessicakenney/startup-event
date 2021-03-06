package dao;

import models.Attendee;
import models.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;


import java.util.List;

import static org.junit.Assert.*;

public class Sql2oEventDaoTest {

 private Sql2oEventDao eventDao;
 private Sql2oAttendeeDao attendeeDao;
 private Connection conn;

 public Event getTestEvent(){
   String name = "What is Blockchain?";
   String description = "introduction talk";
   String date = "2017-11-03";
   return new Event(name, description,date );
 }

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    eventDao = new Sql2oEventDao(sql2o);
    attendeeDao = new Sql2oAttendeeDao(sql2o);
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addingEventSetsId() throws Exception   {
    Event event = getTestEvent();
    eventDao.add(event);
    assertEquals (1,event.getId());
  }

  @Test
  public void existingEventsCanBeFoundById() throws Exception {
    Event event = getTestEvent();
    eventDao.add(event);
    Event foundEvent = eventDao.findById(event.getId());
    assertEquals(event, foundEvent);
  }

  @Test
  public void getAll_allEventsAreFound () throws Exception {
    Event event = getTestEvent();
    Event anotherEvent = new Event ("Event2", "description2", "2017-11-03");
    eventDao.add(event);
    eventDao.add(anotherEvent);
    int number = eventDao.getAll().size();
    assertEquals(2,number );
  }

  @Test
  public void getAll_noEventsAreFound () throws Exception {
    int number = eventDao.getAll().size();
    assertEquals(0,number );
  }

  @Test
  public void getAllAttendeesByEvent () throws Exception {
    Event event = getTestEvent();
    eventDao.add(event);
    int eventId = event.getId();
    Attendee attendee1 = new Attendee("ann",eventId);
    Attendee attendee2 = new Attendee("bob",eventId);
    attendeeDao.add(attendee1);
    attendeeDao.add(attendee2);
    List<Attendee> allAttendees = eventDao.getAllAttendeesByEvent(eventId);
    assertEquals(2,allAttendees.size());
  }

  @Test
  public void update_correctlyUpdates () {
    Event event = getTestEvent();
    eventDao.add(event);
    eventDao.update(event.getId(), "newName","newDescription", "2000-04-03");
    Event updatedEvent = eventDao.findById(event.getId());
    assertEquals("newDescription",updatedEvent.getDescription());
  }

  @Test
  public void deleteById_deletesVeryWell () {
    Event event = getTestEvent();
    eventDao.add(event);
    eventDao.deleteById(event.getId());
    assertEquals(0,eventDao.getAll().size());
  }

  @Test
  public void clearAllEvents() {
    Event event = getTestEvent();
    Event anotherEvent = new Event ("Event2", "description2", "2017-11-03");
    eventDao.add(event);
    eventDao.add(anotherEvent);
    eventDao.clearAllEvents();
    assertEquals(0, eventDao.getAll().size());
  }




}
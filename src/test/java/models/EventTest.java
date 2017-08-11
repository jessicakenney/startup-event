package models;

import org.junit.After;
import org.junit.Test;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class EventTest {

  @After
  public void tearDown() throws Exception {
    Event.clearAll();
  }
  //helper
  public Event newEvent() {
    ArrayList<String> names = new ArrayList<String>(Arrays.asList("name1","name2"));
    return new Event("Event1", "description1", names);
  }
  public Event newEvent2() {
    ArrayList<String> names = new ArrayList<String>(Arrays.asList("cat","dog"));
    return new Event("Event2", "description2", names);
  }

  @Test
  public void newEvent_InstantiatesCorrectly_true() throws Exception {
    Event testEvent = newEvent();
    assertEquals(true, testEvent instanceof Event);
  }

  @Test
  public void getName_returnsNewEventName_True() throws Exception {
    Event testEvent = newEvent();
    assertEquals("Event1", testEvent.getName());
  }

  @Test
  public void getDescription_returnsNewEventDescription_True() throws Exception {
    Event testEvent = newEvent();
    assertEquals("description1", testEvent.getDescription());
  }

  @Test
  public void getAttendees_returnsNewEventAttendees_True() throws Exception {
    Event testEvent = newEvent();
    ArrayList<String> expected = new ArrayList<String>(Arrays.asList("name1","name2"));
    assertEquals(expected, testEvent.getAttendees());
  }

  @Test
  public void getAll_returnsAllEvents_True() throws Exception {
    Event testEvent = newEvent();
    Event testEvent2 = newEvent2();
    assertEquals(2, Event.getAll().size());
  }

  @Test
  public void getAll_returnsEventName_True() throws Exception {
    Event testEvent = newEvent();
    Event testEvent2 = newEvent2();
    assertEquals("Event1", Event.getAll().get(0).getName());
    assertEquals("Event2", Event.getAll().get(1).getName());
  }

  @Test
  public void getId_newEventCreatesId_int() throws Exception {
    Event testEvent = newEvent();
    assertEquals(1, testEvent.getId());
  }

  @Test
  public void findEventById_returnsEvent_True() throws Exception {
    Event testEvent = newEvent();
    Event testEvent2 = newEvent2();
    assertEquals("Event1", Event.findById(2).getName());
  }




}
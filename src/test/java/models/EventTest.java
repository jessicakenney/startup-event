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
    //Event.clearAllPosts(); //clear out allll the posts before each test.
  }
  //helper
  public Event newEvent() {
    ArrayList<String> names = new ArrayList<String>(Arrays.asList("name1","name2"));
    return new Event("Event1", "description1", names);
  }

  @Test
  public void newEvent_InstantiatesCorrectly_true() throws Exception {
    Event testEvent = newEvent();
    assertEquals(true, testEvent instanceof Event);
  }




}
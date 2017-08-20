package dao;

import models.Attendee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

import static org.junit.Assert.*;


public class Sql2oAttendeeDaoTest {

  private Sql2oAttendeeDao attendeeDao;
  private Connection conn;

  public Attendee getTestAttendee(){
    String name = "Jessica Sheridan";
    int eventId = 1;
    return new Attendee(name, eventId );
  }

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    attendeeDao = new Sql2oAttendeeDao(sql2o);
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addingAttendeeSetsId() throws Exception   {
    Attendee attendee = getTestAttendee();
    attendeeDao.add(attendee);
    assertEquals (1,attendee.getId());
  }

  @Test
  public void getAll_allAttendeesAreFound () throws Exception {
    Attendee attendee = getTestAttendee();
    Attendee anotherAttendee = new Attendee ("Sam Sheridan",1);
    attendeeDao.add(attendee);
    attendeeDao.add(anotherAttendee);
    int number = attendeeDao.getAll().size();
    assertEquals(2,number );
  }

  @Test
  public void getAll_noAttendeesAreFound () throws Exception {
    int number = attendeeDao.getAll().size();
    assertEquals(0,number );
  }

  @Test
  public void update_correctlyUpdates () {
    Attendee attendee = getTestAttendee();
    attendeeDao.add(attendee);
    attendeeDao.update(attendee.getId(), "newName");
    Attendee updatedAttendee = attendeeDao.findById(attendee.getId());
    assertEquals("newName",updatedAttendee.getName());
  }

  @Test
  public void deleteById_deletesVeryWell () {
    Attendee attendee = getTestAttendee();
    Attendee anotherAttendee = new Attendee ("Sam Sheridan",1);
    attendeeDao.add(attendee);
    attendeeDao.deleteById(attendee.getId());
    assertEquals(0,attendeeDao.getAll().size());
  }

  @Test
  public void clearAllAttendees() {
    Attendee attendee = getTestAttendee();
    Attendee anotherAttendee = new Attendee ("Attendee2",1);
    attendeeDao.add(attendee);
    attendeeDao.add(anotherAttendee);
    attendeeDao.clearAllAttendees();
    assertEquals(0, attendeeDao.getAll().size());
  }
  @Test
  public void getAllNameOrdered_worksCorrectly_True () throws Exception {
    Attendee attendee = getTestAttendee();
    Attendee anotherAttendee = new Attendee ("Ann",1);
    attendeeDao.add(attendee);
    attendeeDao.add(anotherAttendee);
    assertEquals("Ann", attendeeDao.getAllNameOrdered().get(0).getName());
    assertEquals("Jessica Sheridan", attendeeDao.getAllNameOrdered().get(1).getName());
  }
  @Test
  public void getAllEventOrdered_worksCorrectly_True () throws Exception {
    Attendee attendee = getTestAttendee();
    Attendee anotherAttendee0 = new Attendee ("Ann",2);
    Attendee anotherAttendee = new Attendee ("Jude",1);
    attendeeDao.add(attendee);
    attendeeDao.add(anotherAttendee0);
    attendeeDao.add(anotherAttendee);
    assertEquals(1, attendeeDao.getAllEventOrdered().get(0).getEventId());
    assertEquals(1, attendeeDao.getAllEventOrdered().get(1).getEventId());
    assertEquals(2, attendeeDao.getAllEventOrdered().get(2).getEventId());
  }


}
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


}
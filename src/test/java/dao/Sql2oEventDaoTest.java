package dao;

import models.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;


import static org.junit.Assert.*;

public class Sql2oEventDaoTest {

 private Sql2oEventDao eventDao;
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



}
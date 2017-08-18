package dao;
import models.Attendee;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Sql2oAttendeeDao implements AttendeeDao {
    private final Sql2o sql2o;

    public Sql2oAttendeeDao(Sql2o sql2o){
      this.sql2o = sql2o;
    }

  @Override
  public void add(Attendee attendee) {
    String sql = "INSERT INTO attendees (name,eventId) VALUES (:name, :eventid)"; //raw sql
    try(Connection con = sql2o.open()){ //try to open a connection
      int id = (int) con.createQuery(sql) //make a new variable
              .addParameter("name", attendee.getName())
              .addParameter("eventid", attendee.getEventId())
              .addColumnMapping("NAME", "name")
              .addColumnMapping("EVENTID", "eventid")
              .executeUpdate() //run it all
              .getKey(); //int id is now the row number (row “key”) of db
      attendee.setId(id);
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }




}

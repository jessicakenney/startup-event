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

  @Override
  public List<Attendee> getAll() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM attendees") //raw sql
              .executeAndFetch(Attendee.class); //fetch a list
    }
  }

  @Override
  public List<Attendee> getAllNameOrdered() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM attendees ORDER BY name") //raw sql
              .executeAndFetch(Attendee.class); //fetch a list
    }
  }

  @Override
  public List<Attendee> getAllEventOrdered() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM attendees ORDER BY eventid") //raw sql
              .executeAndFetch(Attendee.class); //fetch a list
    }
  }

  @Override
  public Attendee findById(int id) {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM attendees WHERE id = :id")
              .addParameter("id", id) //key/value pair, key must match above
              .executeAndFetchFirst(Attendee.class); //fetch an individual item
    }
  }

  @Override
  public void update(int id, String newName){
    String sql = "UPDATE attendees SET name = :name WHERE id=:id";
    try(Connection con = sql2o.open()){
      con.createQuery(sql)
              .addParameter("name", newName)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }

  @Override
  public void deleteById(int id) {
    String sql = "DELETE from attendees WHERE id=:id";
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }
  @Override
  public void clearAllAttendees() {
    String sql = "DELETE from attendees";
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
              .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

}

package dao;
import models.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Sql2oEventDao implements EventDao {
  private final Sql2o sql2o;

  public Sql2oEventDao(Sql2o sql2o){
    this.sql2o = sql2o;
  }

  @Override
  public void add(Event event) {
    String sql = "INSERT INTO events (name,description,date) VALUES (:name, :description,:date)"; //raw sql
    try(Connection con = sql2o.open()){ //try to open a connection
      int id = (int) con.createQuery(sql) //make a new variable
              .addParameter("name", event.getName())
              .addParameter("description", event.getDescription())
              .addParameter("date", event.getDate())
              .addColumnMapping("NAME", "name")
              .addColumnMapping("DESCRIPTION", "description")
              .addColumnMapping("DATE", "date")
              .executeUpdate() //run it all
              .getKey(); //int id is now the row number (row “key”) of db
      event.setId(id); //update object to set id now from database
    } catch (Sql2oException ex) {
      System.out.println(ex); //oops we have an error!
    }
  }

  @Override
  public List<Event> getAll() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM events") //raw sql
              .executeAndFetch(Event.class); //fetch a list
    }
  }

  @Override
  public Event findById(int id) {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM events WHERE id = :id")
              .addParameter("id", id) //key/value pair, key must match above
              .executeAndFetchFirst(Event.class); //fetch an individual item
    }
  }






}

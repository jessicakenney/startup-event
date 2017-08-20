package dao;
import models.Attendee;
import java.util.List;

public interface AttendeeDao {

    //create
    void add (Attendee attendee);

    //read
    List<Attendee> getAll();
    List<Attendee> getAllNameOrdered();
    List<Attendee> getAllEventOrdered();
    Attendee findById(int id);

    //update
    void update(int id, String name);

    //delete
    void deleteById(int id);
    void clearAllAttendees();

}

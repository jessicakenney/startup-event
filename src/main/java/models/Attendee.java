package models;

public class Attendee {
  private String name;
  private int eventId;
  private int id;

  public Attendee(String name, int eventId) {
    this.name = name;
    this.eventId = eventId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Attendee attendee = (Attendee) o;

    if (eventId != attendee.eventId) return false;
    if (id != attendee.id) return false;
    return name.equals(attendee.name);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + eventId;
    result = 31 * result + id;
    return result;
  }
}

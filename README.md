# Startup Weekend Event Dashboard 

##### Epicodus Section: Java Week2 and 3: Web Applications and Database Basics

### By Jessica Sheridan

## Description

Startup Weekend Event Dashboard application will provide busy event coordinators
the ability to track their events and attendees seamlessly. Dashboard homepage
will list all scheduled events. Coordinator can click on an event to update the Event
to change it's name,description, or scheduled time. Additionally, new attendees can 
be added in the Event details, and individual attendees clicked on to edit or delete
them. 

## Test Plan 

| Behavior      | Input | Output |
| ------------- | ------------- | ------------- |
| New Event gets created with id| event1  | true |
| New Event created with name |[event1]|[event1]|
| New Event created with description |[description1] |[description1]|
| New Event created with attendees |[name1,name2,name3] | [name1,name2,name3] |
| Two events created successfully |[event1, event2] | true |
| Return all Events |[event1,event2] | [event1,event2]  |
| Events created with unique id  |[event1] | [event1 id |
| Find event by id  |[2] | [event2]|
| Event name update  |[event1, editName] | [editName] |
| Delete attendee  |[name1, name2] | [name1] |
| Add attendee  |[name1,name2] | [name1,name2,name3] |
----------------|-------------|-----------------------|
| New Attendee gets created with id| attendee  | true |
| Two Attendees created successfully |[attendee, attendee1] | true |
| Attendee update works sucessfully| [name1] | [name-editted] |
| Attendee deletebyId  [name1] | [] |
| Attendee clearAllAttendees  [name1,name2] | [] |


## Setup
git clone https://github.com/jessicakenney/startup-event.git  
bring up url: localhost:4567

## Support and Contact details
email with any questions: jessicakenney@yahoo.com,

## Known Issues/Bugs

## Technologies Used
Java, Spark, Handlebars

### Legal
This software is licensed under MIT Copyright (c) 2017 Jessica Sheridan

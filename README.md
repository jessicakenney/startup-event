# Startup Weekend Event Dashboard 

##### Epicodus Section: Java Week2: Web Applications 

### By Jessica Sheridan

## Description

Startup Weekend Event Dashboard application will provide busy event coordinators
the ability to track their events and attendees seamlessly. Dashboard homepage
will list scheduled events. Coordinator can click on an event to update the Event
to change it's name. Updating attendees is a secondary feature. 

## Test Plan: 

| Behavior      | Input | Output |
| ------------- | ------------- | ------------- |
| New Event gets created| event1  | true |
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



## Setup
git clone https://github.com/jessicakenney/startup-event.git  

## Support and Contact details
email with any questions: jessicakenney@yahoo.com,

## Known Issues/Bugs

## Technologies Used
Java, Spark, Handlebars

### Legal
This software is licensed under MIT Copyright (c) 2017 Jessica Sheridan

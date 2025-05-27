# Event Management Backen


## Installation

* Navigate to the directory with your docker-compose.yaml file then build and run the services
```
docker compose up --build

```

* Port Conflict
  * Open docker-compose.yaml.
  * Locate the ports: section.
  * Change only the first number (before the colon :) to another unused port.

* Spring Boot App
  * The Spring Boot application runs by default on *http://localhost:8090*
  * to test it 
    * open Postmann
    *  send a GET request to *http://localhost:8090/events/all*

* The development email interface is available at: *http://localhost:5000
* When you call the /events/all endpoint (or trigger any email-sending logic), you should see a new email appear here.

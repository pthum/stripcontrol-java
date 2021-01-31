# Stripcontrol

This project offers a small webservice with a frontend to control APA102 strips.

More specifically: 


The light switches in my apartment have a "bad UX". For my daily "workflows", they're always located in a way, that i need to put some additional effort when I want to use them. The idea to solve this problem was to use the RaspberryPi as a cloud-free smart home solution. In the current state, the RasPi drives only the LED strips, but more may come in future (such as controlling radio-controlled power sockets).

Furthermore, this project is also a playground to try different technologies, tools, etc, where the vue frontend and spring-boot application can be seen as a reference implementation of the project. Other implementations (f.i. implementations using microprofile, spark, golang, rust) may follow in the future.


## Project Overview
In the current state, there are several sub-projects in this project.

### stripcontrol-frontend
Includes the frontend, which is a VueJS application. The backend applications copy the resources from this project and serve them during runtime.

### stripcontrol-models
Library that contains POJO Models. The models have annotations to store them in a database.

### stripcontrol-ledhandling
Library that encapsulates the business logic for the LED-handling and is used by the backend implementations based on java.

### stripcontrol-springbackend
An implementation of the backend part based on the Spring Boot framework.

### Quarkus implementation
The implemenation of the backend part based on the Quarkus framework is split up in two parts.
The first part handles the database interactions and delivers the frontend. 
The second part manages the LED strips.
Both services communicate over an MQTT message broker like Eclipse Mosquitto.

#### stripcontrol-quarkusbackend
An implementation of the first backend part based on the Quarkus framework. This implementation handles the database interaction and sends MQTT messages.
There's an [alternative implementation](https://github.com/pthum/stripcontrol-golang) available written in golang.

#### stripcontrol-quarkuslights
An implementation of the second backend part based on the Quarkus framework. This implementation manages the LED strips and receives the MQTT messages.


## Further readme files
There are only the readme files at the moment. More detailed documentation will follow.

See:

 * [VueJS Frontend Readme](stripcontrol-frontend/README.md)
 * [SpringBoot Backend Readme](stripcontrol-javabackend/README.md)

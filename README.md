# Stripcontrol

This project offers a small webservice with a frontend to control APA102 strips.

More specifically: 


The light switches in my apartment have a "bad UX". For my daily "workflows", they're always located in a way, that i need to put some additional effort when I want to use them. The idea to solve this problem was to use the RaspberryPi as a cloud-free smart home solution. In the current state, the RasPi drives only the LED strips, but more may come in future (such as controlling radio-controlled power sockets).

Furthermore, this project is also a playground to try different technologies, tools, etc, where the vue frontend and spring-boot application can be seen as a reference implementation of the project. Other implementations (f.i. implementations using microprofile, spark, golang, rust) may follow in the future.


## Project Overview
In the current state, there are several sub-projects in this project.

### stripcontrol-models
Library that contains POJO Models. The models have annotations to store them in a database.

### stripcontrol-ledhandling
Library that encapsulates the business logic for the LED-handling and is used by the backend implementations based on java.

### stripcontrol-quarkuslights
An implementation of the second backend part based on the Quarkus framework. This implementation manages the LED strips and receives the MQTT messages.

## Further links
This project has evolved over time, for old implementations, see the [Archive](https://github.com/pthum/stripcontrol-archive/)
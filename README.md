# Stripcontrol-java

This repository contains (current) java implementations for the stripcontrol project.
For more information about the whole project, see the [main repository](https://github.com/pthum/stripcontrol)

## Project Overview
In the current state, there are several sub-projects in this project.

### stripcontrol-models
Library that contains POJO Models. The models have annotations to store them in a database.

### stripcontrol-ledhandling
Library that encapsulates the business logic for the LED-handling and is used by the backend implementations based on java.

### stripcontrol-quarkuslights
An implementation of the second backend part based on the Quarkus framework. This implementation manages the LED strips and receives the MQTT messages.

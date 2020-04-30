# stripcontrol-javabackend

> Stripcontrol backend application made with SpringBoot2.

## Project Build
```bash
mvn clean package
```

### Run project
```bash
java -jar target/stripcontrol-javabackend-0.0.1-SNAPSHOT.jar

```

### Build and run docker image
To build the docker image, copy the packaged jar file to the `docker/stripcontrol` folder and run the build 

```bash
# start in directory of this readme-file
cp target/stripcontrol-javabackend-0.0.1-SNAPSHOT.jar docker/stripcontrol/stripcontrol.jar
cd docker
docker build -t pthum/stripcontrol:0.0.1-SNAPSHOT -f Dockerfile .
```

To run the image (on the pi), you can either use the provided docker-compose for convenience or run directly over docker:
```bash
docker run -d -p8080:8080 --cap-add=SYS_RAWIO --privileged --device="/dev/gpiomem:/dev/gpiomem" --volume="/home/pi/scdocker:/stripcontrol/config" pthum/stripcontrol:0.0.1-SNAPSHOT
```
In both cases, the volume mapping is used to map a directory, which contains the logback.xml (currently required) as well as this is the location for the h2 database file.
In this current version, it is necessary to run in privileged mode and will hopefully change in future.

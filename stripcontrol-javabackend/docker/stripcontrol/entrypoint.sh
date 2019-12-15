#!/bin/sh
java -Dlogging.config="/stripcontrol/config/logback.xml" -Dspring.datasource.url="jdbc:h2:file:/stripcontrol/config/stripcontroldb" -Dstrips.effecttime=20 -Dstrips.enabled=true -jar /stripcontrol/stripcontrol.jar


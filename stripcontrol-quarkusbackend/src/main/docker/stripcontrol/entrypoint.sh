#!/bin/bash
set +e
JAVA_OPTS+=('-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager')

if [[ "$DISABLE_STRIPS" == true ]]; then
    JAVA_OPTS+=(" -Dstrips.enabled=false")
else
    JAVA_OPTS+=(" -Dstrips.enabled=true")
fi

if [[ -z "$STRIP_EFFECTTIME" ]]; then
    JAVA_OPTS+=(" -Dstrips.effecttime=20")
else
    JAVA_OPTS+=(" -Dstrips.effecttime=$STRIP_EFFECTTIME")
fi

echo "starting stripcontrol with the following args:"

echo "${JAVA_OPTS[@]}"
echo "run:"
exec java ${JAVA_OPTS[@]} -jar /stripcontrol/stripcontrol.jar

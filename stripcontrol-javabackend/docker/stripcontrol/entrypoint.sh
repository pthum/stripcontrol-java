#!/bin/bash
set +e
JAVA_OPTS+=('-Dspring.datasource.url=jdbc:h2:file:/stripcontrol/config/stripcontroldb')

if [[ -f "/stripcontrol/config/logback.xml" ]]; then 
    JAVA_OPTS+=(' -Dlogging.config=/stripcontrol/config/logback.xml ')
fi

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

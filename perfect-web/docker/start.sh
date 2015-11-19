#!/bin/bash

JAVA_OPTIONS="-server \
    -Dfile.encoding=UTF-8 \
    -Djava.io.tmpdir=/tmp/jetty"

if [ -n "$HEAP_MEMORY_SIZE" ];  then
    JAVA_OPTIONS="$JAVA_OPTIONS -DXms=$HEAP_MEMORY_SIZE -DXmx=$HEAP_MEMORY_SIZE"
fi

if [ -n "$HTTP_PORT" ];  then
    JAVA_OPTIONS="$JAVA_OPTIONS -Djetty.http.port=$HTTP_PORT"
fi


java $JAVA_OPTIONS -jar $JETTY_HOME/start.jar
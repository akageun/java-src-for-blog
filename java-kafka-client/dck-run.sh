#!/bin/sh

start() {
    echo "==== Start ===="
    export EXPOSED_HOSTNAME=$(ipconfig getifaddr en0)
    echo "EXPOSED_HOSTNAME : ${EXPOSED_HOSTNAME}"
    docker-compose -f docker-compose.yml up -d
}

stop() {
    echo "==== Stop ===="

    #docker-compose -f docker-compose.yml stop && docker-compose -f docker-compose.yml rm -vf
    docker-compose -f docker-compose.yml stop

    echo "Done."
}

case "$1" in
'start')
    start
    ;;
'stop')
    stop
    ;;
'restart')
    stop
    echo "Sleeping..."
    sleep 5
    start
    ;;
*)
    echo
    echo "Usage: $0 { start | stop | restart }"
    echo
    exit 1
    ;;
esac

exit 0

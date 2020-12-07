trap "exit" INT TERM ERR
trap "kill 0" EXIT

PLAYER_1_PORT=${1:-8080}
PLAYER_2_PORT=${2:-8081}
PLAYER_1_MODE=${3:-MANUAL}
PLAYER_2_MODE=${4:-AUTOMATIC}

java -jar target/game-of-three-1.0.0.jar --server.port=$PLAYER_1_PORT --game-of-three.other-player.uri=http://localhost:$PLAYER_2_PORT --game-of-three.play-mode=$PLAYER_1_MODE &

java -jar target/game-of-three-1.0.0.jar --server.port=$PLAYER_2_PORT --game-of-three.other-player.uri=http://localhost:$PLAYER_1_PORT --game-of-three.play-mode=$PLAYER_2_MODE &

wait
if "%1"=="" (set player1Port=8080) else (set player1Port=%1%)
if "%2"=="" (set player2Port=8081) else (set player2Port=%2%)
if "%3"=="" (set player1Mode=MANUAL) else (set player1Mode=%3%)
if "%4"=="" (set player2Mode=AUTOMATIC) else (set player2Mode=%4%)

start java -jar target/game-of-three-1.0.0.jar --server.port=%player1Port% --game-of-three.other-player.uri=http://localhost:%player2Port% --game-of-three.play-mode=%player1Mode%

start java -jar target/game-of-three-1.0.0.jar --server.port=%player2Port% --game-of-three.other-player.uri=http://localhost:%player1Port% --game-of-three.play-mode=%player2Mode%
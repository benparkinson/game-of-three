set player1Port=8080
set player2Port=8081
set player1Mode=MANUAL
set player2Mode=AUTOMATIC

start java -jar target/game-of-three-1.0.0.jar --server.port=%player1Port% --game-of-three.other-player.uri=http://localhost:%player2Port% --game-of-three.play-mode=%player1Mode%

start java -jar target/game-of-three-1.0.0.jar --server.port=%player2Port% --game-of-three.other-player.uri=http://localhost:%player1Port% --game-of-three.play-mode=%player2Mode%
# game-of-three
REST API implementation of a simple 2 player game.

To run, first package the project:

`mvn clean package`

Next, depending on your environment, run either the `run.bat` (Windows) or `run.sh` (*nix/bash) scripts. This will start two players, one running on port 8080 in 'manual' mode, the other on 8081 in 'automatic'.
If you wish to alter the ports or the running modes of the players, you can do so with command-line arguments to these scripts, with the order `player1Port player2Port player1Mode player2Mode`, for example:

`./run.sh 8090 8091 AUTOMATIC MANUAL`

will run with player 1 on port 8090 in automatic mode, and player 2 on port 8091 in manual mode.

Once the player servers are running, you can head to http://localhost:8080 or http://localhost:8081 to play (or whichever ports you have configured).

Press the 'Start Game' button to play. At the top you can see the mode the player is currently in, press the 'Switch' button to toggle this. Note that the mode can be changed mid-game.

# Known limitations

The players communicate via a REST API, but game moves arrive by means of a WebSocket. This means that if one player is running in manual mode and doesn't have their browser open, they will miss the step and the game must be restarted, as I have not had time to implement a replay of messages when the browser connects to the WebSocket.

Related to this is the drawback that if the server restarts, the browser must refresh in order to reconnect to the WebSocket.

Since the players communicate via a REST API, if one player is not running when a move is sent then the request will unfortunately fail. Implementing this with an event-based communication would have prevented this, but I decided on the REST-based communication as I have more experience with it.

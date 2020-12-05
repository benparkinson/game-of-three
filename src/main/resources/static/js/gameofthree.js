var stompClient = null;

function parseMoveMessage(message) {
    return JSON.parse(message.body);
}

function connectToWebSocket() {
    var socket = new SockJS('/game-of-three-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => { };
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/my-moves', function (myMove) {
            var move = parseMoveMessage(myMove);
            console.log('mymove: ' + move.result + ', ' + move.addend);
        });
        stompClient.subscribe('/topic/their-moves', function (theirMove) {
            var move = parseMoveMessage(theirMove);
            console.log('theirmove: ' + move.result + ', ' + move.addend);
        });
    });
}

$(document).ready(function () {
    $('#startButton').on('click', function () {
        $.ajax({
            type: 'POST',
            url: '/gameofthree/v1/api/games',
            success: function (firstMove) {
                $('#feedback-message').text('My first move: ' + firstMove.result);
            },
            error: function () {
                $('#feedback-message').text('Error starting game');
            }
        })
    });

    connectToWebSocket();
});

var stompClient = null;

const gameMoveCard = (gameMove) => {

    let displayString;
    let prepend;

    if (gameMove.firstMove) {
        displayString = `First move: <strong>${gameMove.result}</strong>`;
        prepend = '';
    } else {
        const addend = gameMove.addend < 0 ? gameMove.addend : '+ ' + gameMove.addend;
        displayString = `${gameMove.previousResult} ${addend} = ${gameMove.dividend}<br/>
            ${gameMove.dividend} / 3 = <strong>${gameMove.result}</strong>`;
        prepend = '<div class="invisible card list-group-item"><div class="card-body"></div></div>';
    }

    return `
    ${prepend}
    <div class="pt-1 card list-group-item text-center">
        <div class="card-body">
            <p class="card-text">
                ${displayString}
            </p>
        </div>
    </div>
`};

function clearMoveHistory() {
    console.log('clearing history...');
    $('#myMoveList').empty();
    $('#theirMoveList').empty();
}

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
            $('#myMoveList').append(gameMoveCard(move));
        });
        stompClient.subscribe('/topic/their-moves', function (theirMove) {
            var move = parseMoveMessage(theirMove);
            if (move.firstMove) {
                clearMoveHistory();
            }
            $('#theirMoveList').append(gameMoveCard(move));
        });
    });
}

$(document).ready(function () {
    $('#startButton').on('click', function () {

        clearMoveHistory();

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

const AUTOMATIC = 'AUTOMATIC';
const MANUAL = 'MANUAL';
const DUMMY_CARD = '<div class="invisible card list-group-item"><div class="card-body"><br/></div></div>';
const DUMMY_MANUAL_INPUT_CARD = '<div id="dummyManualInput" class="invisible card list-group-item"><div class="card-body"><br/></div></div>';
var playMode = null;

let previousMoveForManualInput = null;

$(document).ready(function () {
    fetchPlayMode();

    $('#startButton').on('click', function () {
        startGame();
    });

    $('#myMoveList').on('click', '.minusOne', () => {
        sendManualMove(previousMoveForManualInput, -1);
    });

    $('#myMoveList').on('click', '.zero', () => {
        sendManualMove(previousMoveForManualInput, 0);
    });

    $('#myMoveList').on('click', '.plusOne', () => {
        sendManualMove(previousMoveForManualInput, 1);
    });

    connectToWebSocket();
});

function startGame() {
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
    });
}

function fetchPlayMode() {
    $.ajax({
        type: 'GET',
        url: '/gameofthree/v1/api/playmode',
        success: function (configuredPlayMode) {
            playMode = configuredPlayMode;
            updatePlayModeText(playMode);
        },
        error: function () {
            playMode = 'AUTOMATIC';
            updatePlayModeText(playMode);
        }
    });
}

function updatePlayModeText(playMode) {
    $('#playMode').text('Mode: ' + playMode);
}

function connectToWebSocket() {
    var socket = new SockJS('/game-of-three-websocket');
    var stompClient = Stomp.over(socket);
    stompClient.debug = () => { };
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/my-moves', function (myMove) {
            var move = parseMoveMessage(myMove);
            $('#myMoveList').append(gameMoveCard(move));
            if (move.result === 1) {
                updateWinnerText('You won!');
            }
        });
        stompClient.subscribe('/topic/their-moves', function (theirMove) {
            var move = parseMoveMessage(theirMove);
            if (move.firstMove) {
                clearMoveHistory();
            }
            $('#theirMoveList').append(gameMoveCard(move));
            if (move.result === 1) {
                updateWinnerText('You lost!');
            } else if (playMode === MANUAL) {
                addManualInputCard(move);
            }
        });
    });
}

function updateWinnerText(text) {
    $('#winner').text(text);
}

function parseMoveMessage(message) {
    return JSON.parse(message.body);
}

function addManualInputCard(previousMove) {
    previousMoveForManualInput = previousMove;
    $('#myMoveList').append(manualInputCard(previousMove));
}

function clearMoveHistory() {
    $('#myMoveList').empty();
    $('#theirMoveList').empty();
    $('#winner').text('');
}

const gameMoveCard = (gameMove) => {
    let displayString;
    let prepend;

    if (gameMove.firstMove) {
        displayString = `First move: <strong>${gameMove.result}</strong>`;
        prepend = '';
    } else {
        const addend = gameMove.addend < 0 ? gameMove.addend : '+' + gameMove.addend;
        displayString = `${gameMove.previousResult} ${addend} = ${gameMove.dividend}<br/>
            ${gameMove.dividend} / 3 = <strong>${gameMove.result}</strong>`;
        prepend = DUMMY_CARD;
    }

    return `
    ${prepend}
    <div class="list-group-item">
        <div class="card text-center">
            <div class="card-body">
                <p class="card-text">
                    ${displayString}
                </p>
            </div>
        </div>
    </div>
`};

const manualInputCard = (previousMove) => {
    return `
    ${DUMMY_MANUAL_INPUT_CARD}
    <div id="manualInputCard" class="list-group-item">
        <div class="card text-center">
            <div class="card-body">
                <p class="card-text">
                    <strong>${previousMove.result}</strong>
                </p>
            </div>
            <div id="manualCardFooter" class="card-footer">
                <button type="button" class="minusOne btn btn-dark">-1</button>
                <button type="button" class="zero btn btn-dark">0</button>
                <button type="button" class="plusOne btn btn-dark">+1</button>
            </div>
        </div>
    </div>`;
};

function validateManualMove(previousMove, addend) {
    const result = previousMove.result + addend;
    $('#manualCardFooter').find('#manualInputError').remove();
    if (result % 3 !== 0) {
        $('#manualCardFooter').append('<div id="manualInputError" class="pt-2 text-danger">Result not divisible by three!</div>');
        return false;
    }
    return true;
}

function sendManualMove(previousMove, addend) {
    if (!validateManualMove(previousMove, addend)) {
        return;
    }

    const manualMove = {
        previousResult: previousMove.result,
        addend: addend
    };

    $.ajax({
        type: 'POST',
        url: '/gameofthree/v1/api/manual/gamemoves',
        data: JSON.stringify(manualMove),
        contentType: 'application/json',
        success: function () {
            removeManualStepCard();
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function removeManualStepCard() {
    $('#myMoveList').find('#manualInputCard').remove();
    $('#myMoveList').find('#dummyManualInput').remove();
}

const AUTOMATIC = 'AUTOMATIC';
const MANUAL = 'MANUAL';
const DUMMY_CARD = '<div class="invisible card list-group-item"><div class="card-body"><br/></div></div>';
const DUMMY_MANUAL_INPUT_CARD = '<div id="dummy-manual-input" class="invisible card list-group-item"><div class="card-body"><br/></div></div>';
var playMode = null;

let previousMoveForManualInput = null;

$(document).ready(function () {
    fetchPlayMode();

    $('#start-button').on('click', function () {
        startGame();
    });

    $('#my-move-list').on('click', '.minus-one', () => {
        sendManualMove(previousMoveForManualInput, -1);
    });

    $('#my-move-list').on('click', '.zero', () => {
        sendManualMove(previousMoveForManualInput, 0);
    });

    $('#my-move-list').on('click', '.plus-one', () => {
        sendManualMove(previousMoveForManualInput, 1);
    });

    $('#switch-mode-button').on('click', function () {
        switchGameMode();
    });

    connectToWebSocket();
});

function startGame() {
    clearMoveHistory();

    $.ajax({
        type: 'POST',
        url: '/game-of-three/v1/api/games',
        error: function (error) {
            $('#error-message').text('Error starting game, is the other player running?');
        }
    });
}

function fetchPlayMode() {
    $.ajax({
        type: 'GET',
        url: '/game-of-three/v1/api/play-mode',
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

function switchGameMode() {
    if (!playMode) {
        return;
    }

    const newMode = playMode === AUTOMATIC ? MANUAL : AUTOMATIC;

    $.ajax({
        type: 'PUT',
        url: '/game-of-three/v1/api/play-mode/' + newMode,
        success: function (configuredPlayMode) {
            fetchPlayMode();
        }
    });

}

function updatePlayModeText(playMode) {
    $('#play-mode').text('Mode: ' + playMode);
}

function connectToWebSocket() {
    var socket = new SockJS('/game-of-three-websocket');
    var stompClient = Stomp.over(socket);
    stompClient.debug = () => { };
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/my-moves', function (myMove) {
            var move = parseMoveMessage(myMove);
            $('#my-move-list').append(gameMoveCard(move));
            if (move.result === 1) {
                updateWinnerText('You won!');
            }
        });
        stompClient.subscribe('/topic/their-moves', function (theirMove) {
            var move = parseMoveMessage(theirMove);
            if (move.firstMove) {
                clearMoveHistory();
            }
            $('#their-move-list').append(gameMoveCard(move));
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
    $('#my-move-list').append(manualInputCard(previousMove));
}

function clearMoveHistory() {
    $('#my-move-list').empty();
    $('#their-move-list').empty();
    $('#winner').text('');
    $('#error-message').text('');
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
    <div id="manual-input-card" class="list-group-item">
        <div class="card text-center">
            <div class="card-body">
                <p class="card-text">
                    <strong>${previousMove.result}</strong>
                </p>
            </div>
            <div id="manual-card-footer" class="card-footer">
                <button type="button" class="minus-one btn btn-dark">-1</button>
                <button type="button" class="zero btn btn-dark">0</button>
                <button type="button" class="plus-one btn btn-dark">+1</button>
            </div>
        </div>
    </div>`;
};

function validateManualMove(previousMove, addend) {
    const result = previousMove.result + addend;
    $('#manual-card-footer').find('#manual-input-error').remove();
    if (result % 3 !== 0) {
        $('#manual-card-footer').append('<div id="manual-input-error" class="pt-2 text-danger">Result not divisible by three!</div>');
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
        url: '/game-of-three/v1/api/manual/game-moves',
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
    $('#my-move-list').find('#manual-input-card').remove();
    $('#my-move-list').find('#dummy-manual-input').remove();
}

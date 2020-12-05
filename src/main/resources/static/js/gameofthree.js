$(document).ready(function () {
    $('#startButton').on('click', function () {
        $.ajax({
            type: 'POST',
            url: '/gameofthree/v1/api/games',
            success: function(firstMove) {
                $('#feedback-message').text('My first move: ' + firstMove.result);
            },
            error: function() {
                $('#feedback-message').text('Error starting game');
            }
        })
    });
});
$(document).ready(function () {
    $('#startButton').on('click', function () {
        $.ajax({
            type: 'POST',
            url: '/gameofthree/v1/api/games',
            success: function() {
                $('#feedback-message').text('Game started successfully!');
            },
            error: function() {
                $('#feedback-message').text('Error starting game');
            }
        })
    });
});
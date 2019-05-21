$(document).ready(function () {
    $("#search").on('change', function postinput() {
        var matchvalue = $(this).val(); // this.value
        $.ajax({
            url: '/user/search',
            data: {pattern: matchvalue},
            type: 'post'
        }).done(function (responseData) {
            console.log('Done: ', responseData);
        }).fail(function () {
            console.log('Failed');
        });
    });
});
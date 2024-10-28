document.querySelector(".bottomTop").addEventListener("click", function (event) {
    event.preventDefault();
    document.querySelector("#mainPage").scrollIntoView({ behavior: 'smooth' });
});

document.addEventListener("DOMContentLoaded", function () {
    if (typeof jQuery == 'undefined') {
        var script = document.createElement('script');
        script.src = 'https://code.jquery.com/jquery-3.6.4.min.js';
        script.onload = function () {
            addScrollEvent();
        };
        document.head.appendChild(script);
    } else {
        addScrollEvent();
    }
    function addScrollEvent() {
        $(window).scroll(function () {
            if ($(this).scrollTop() > 700) {
                $('#topBtn').fadeIn();
            } else {
                $('#topBtn').fadeOut();
            }
        });
        $("#topBtn").click(function () {
            $('html, body').animate({
                scrollTop: 0
            }, 400);
            return false;
        });
    }
});
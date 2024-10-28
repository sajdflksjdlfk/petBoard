
document.addEventListener('DOMContentLoaded', function() {
    var loginButton = document.getElementById('login');
    var logoutButton = document.getElementById('logout');
    var signupButton = document.getElementById('signup');

    if (loginButton) {
        loginButton.addEventListener('click', function () {
            console.log("123123");
            window.location.href = '/login';
        });
    }

    if (logoutButton) {
        logoutButton.addEventListener('click', function () {
            window.location.href = '/logout';

        });
    }

    if (signupButton) {
        signupButton.addEventListener('click', function () {
            window.location.href = '/create_account';

        });
    }
});

// 글작성
document.getElementById('write').addEventListener('click', function () {
    window.location.href = 'create_article';
});
// 글작성
document.getElementById('view_article').addEventListener('click', function () {
    window.location.href = 'view_article';
});
document.getElementById('search_detail').addEventListener('click', function () {
    window.location.href = 'search2';
});
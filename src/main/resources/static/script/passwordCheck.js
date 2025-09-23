function checkEquality(password, confirmation) {
    if (password === confirmation) {
        document.getElementById('confirm-message').style.color = 'green';
        document.getElementById('confirm-message').innerHTML = 'matching';
    } else {
        document.getElementById('confirm-message').style.color = 'red';
        document.getElementById('confirm-message').innerHTML = 'not matching';
    }
}

function passwordIsConform(password) {
    const regex = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
    return regex.test(password);
}

const check = function () {
    console.log("typing...");
    let password = document.getElementById('inputPassword').value;
    let confirmation = document.getElementById('inputConfirmation').value;
    if (passwordIsConform(password)) {
        checkEquality(password, confirmation);
    } else {
        document.getElementById('confirm-message').style.color = 'red';
        document.getElementById('confirm-message').innerHTML = 'Password must contain at least 8 characters, including a majuscule, a minuscule, a number and a special character.';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    console.log("password script loaded")
    document.getElementById("#inputPassword").addEventListener("keyup", () => {
        check()
    })
    document.getElementById("#inputConfirmation").addEventListener("keyup", () => {
        check()
    })
})

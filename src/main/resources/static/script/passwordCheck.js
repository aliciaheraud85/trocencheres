function checkEquality(password, confirmation) {
    if (password === confirmation) {
        document.getElementById('confirm-message').style.color = 'green';
        document.getElementById('confirm-message').innerHTML = 'passwords matching ✓';
    } else {
        document.getElementById('confirm-message').style.color = 'red';
        document.getElementById('confirm-message').innerHTML = 'passwords not matching';
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
    const inputPassword = document.getElementById("inputPassword");
    const confirmation = document.getElementById("inputConfirmation");
    if (inputPassword != null) {
        inputPassword.addEventListener("keyup", () => {
            check()
        })
        confirmation.addEventListener("keyup", () => {
            check()
        })
    }
})

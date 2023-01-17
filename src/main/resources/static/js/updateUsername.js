function checkUsername() {
    if (document.getElementById("username").value === "") {
        document.getElementById('button_success_update_username').disabled = true;
    } else {
        document.getElementById('button_success_update_username').disabled = false;
    }
}

function checkUsernameForAdmin() {
    if (document.getElementById("username_for_admin").value === "") {
        document.getElementById('button_success_update_username_for_admin').disabled = true;
    } else {
        document.getElementById('button_success_update_username_for_admin').disabled = false;
    }
}

function checkPassword() {
    if (document.getElementById("password").value === "") {
        document.getElementById('button_success_update_password').disabled = true;
    } else {
        document.getElementById('button_success_update_password').disabled = false;
    }
}
function check() {
    if (document.getElementById("theme_name").value === "") {
        document.getElementById('buttonSuccess').disabled = true;
    } else {
        document.getElementById('buttonSuccess').disabled = false;
    }
}

function checkForGroup() {
    if (document.getElementById("group_name").value === "" || document.getElementById("themes").selectedIndex === 0) {
        document.getElementById('buttonSuccessGroup').disabled = true;
    } else {
        document.getElementById('buttonSuccessGroup').disabled = false;
    }
}
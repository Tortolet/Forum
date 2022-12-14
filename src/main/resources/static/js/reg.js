function success() {
    if(document.getElementById("floatingInput").value==="" || document.getElementById("floatingPassword").value==="" || document.getElementById("floatingPasswordConfirm").value==="") {
        document.getElementById('buttonSuccess').disabled = true;
    } else {
        document.getElementById('buttonSuccess').disabled = false;
    }
}
function checkFormPost() {
    if (document.getElementById("Title").value === "" || document.getElementById("TextArea").value === "") {
        document.getElementById('buttonSuccessPost').disabled = true;
    } else {
        document.getElementById('buttonSuccessPost').disabled = false;
    }
}

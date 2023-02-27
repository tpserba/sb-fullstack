// Call the dataTables jQuery plugin
$(document).ready(function () {
    console.log("ready")
    // On ready
});

async function login() {
    console.log("test");
    let datos = {};
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;
    console.log("got values from textboxes")
    const request = await fetch('/api/v1/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    const response = await request.text();
    console.log(response);
    if (response !== "FAIL") {
        localStorage.token = response;
        localStorage.email = datos.email;
        window.location.href = 'users.html';
    } else {
        alert("Wrong credentials");
    }
};
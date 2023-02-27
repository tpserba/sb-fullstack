// Call the dataTables jQuery plugin
$(document).ready(function () {
    // On ready
});

async function registerUsers() {
    console.log("register users")
    let datos = {};
    datos.name = document.getElementById("txtName").value;
    datos.surname = document.getElementById("txtSurname").value;
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;
    let repeatPassword = document.getElementById("txtRepeatPassword").value;

    if (datos.password !== repeatPassword) {
        alert("Passwords are not the same.");
        return;
    }

    const request = await fetch('/api/v1/user', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    alert("Registered successfully!");
    window.location.href = "login.html";
};
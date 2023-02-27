// Call the dataTables jQuery plugin
$(document).ready(function () {
    loadUsers();
    $('users').DataTable();
});
function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-type': 'application/json',
        'Authorization': localStorage.token
    }
}
async function loadUsers() {
    const request = await fetch('/api/v1/user', {
        method: 'GET',
        headers: getHeaders(),
        // body: JSON.stringify({a:1, b:'aa'})
    });
    const users = await request.json();
    console.log("this is users");
    console.log(users);
    if (users === null || users === undefined || users.length === 0) {
        alert("Error with authentication token (maybe expired)");
        return null;
    }
    let userList = ''
    for (let user of users) {
        let btnRemove = `<a href="#" onClick="removeUser(${user.id})" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>`;
        let userRow = `<tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.email}</td>
                <td>${user.phoneNum === null ? "---" : user.phoneNum}</td>
                <td>
                    ${btnRemove}
                </td></tr>`
        userList += userRow;
    }
    document.querySelector('#users tbody').outerHTML = userList;
};



async function removeUser(id) {
    // Ideally it would search if user exists first, given the id. If it doesn't, send response.
    // Otherwise it would delete it.
    if (!confirm("Delete user?")) {
        return;
    }
    console.log("called");
    const request = await fetch('/api/v1/user/' + id, {
        method: 'DELETE',
        headers: getHeaders(),
    });
    location.reload();
};
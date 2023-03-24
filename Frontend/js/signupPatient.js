document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");

    signupForm.addEventListener("submit", function (event) {
        event.preventDefault();
        // Implement your login logic here
        var [username, password, name, address, gender, contact, dob] = event.target;
        var xhr = new XMLHttpRequest();

        // Define the request parameters
        var url = "http://localhost:8080/api/auth/registerPatient";

        var data = { username: username.value, password: password.value, name: name.value, address: address.value, gender: gender.value, contact: contact.value, dob: dob.value };
        var params = JSON.stringify(data);

        console.log(data);

        // Open the connection
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-type", "application/json");

        // Handle the response
        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 300) {
                alert("Successfully registered");
                window.location = "index.html";
            } else {
                alert("Error with registering the account");
            }
        };

        // Send the request
        xhr.send(params);
    });
});

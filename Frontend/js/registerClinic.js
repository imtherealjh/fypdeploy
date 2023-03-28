document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");

    signupForm.addEventListener("submit", function (event) {
        event.preventDefault();
        // Implement your login logic here
        var [username, password, name, location, openingHrs, closingHrs, apptDuration] = event.target;
        var xhr = new XMLHttpRequest();

        // Define the request parameters
        var url = "http://localhost:8080/api/auth/registerClinic";

        var data = { username: username.value, password: password.value, name: name.value, location: location.value, openingHrs: openingHrs.value, closingHrs: closingHrs.value, apptDuration: apptDuration.value };
        var params = JSON.stringify(data);

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

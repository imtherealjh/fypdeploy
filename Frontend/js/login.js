document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.getElementById("loginForm");

  loginForm.addEventListener("submit", function (event) {
    event.preventDefault();

    var xhr = new XMLHttpRequest();

    // Define the request parameters
    var url = "http://localhost:8080/api/auth/login";
    var username = event.target.username.value;
    var password = event.target.password.value;

    var data = { username: username, password: password };
    var params = JSON.stringify(data);

    // Open the connection
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");

    // Handle the response
    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 300) {
        var data = JSON.parse(this.responseText);
        document.cookie = `cookie=${JSON.stringify(data)}`;
        alert("Sucessfully logged in");
      } else {
        alert("Error with logging into the account");
      }
    };

    // Send the request
    xhr.send(params);
  });
});

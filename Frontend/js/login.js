document.addEventListener("DOMContentLoaded", function () {
  const loginBtn = document.getElementById("loginBtn");
  const loginModal = document.getElementById("loginModal");
  const closeModal = document.querySelector(".close");
  const loginForm = document.getElementById("loginForm");
  const signupPatientBtn = document.getElementById("signupPatientBtn");
  const signupClinicBtn = document.getElementById("signupClinicBtn");

  signupClinicBtn.addEventListener("click", function () {
    window.location = "/signupClinic.html";
  })

  signupPatientBtn.addEventListener("click", function () {
    window.location = "/signupPatient.html";
  })

  loginBtn.addEventListener("click", function () {
    loginModal.style.display = "block";
  });

  closeModal.addEventListener("click", function () {
    loginModal.style.display = "none";
  });

  window.addEventListener("click", function (event) {
    if (event.target == loginModal) {
      loginModal.style.display = "none";
    }
  });

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

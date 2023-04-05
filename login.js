document.addEventListener("DOMContentLoaded", function() {
  const loginBtn = document.getElementById("loginBtn");
  const loginModal = document.getElementById("loginModal");
  const closeModal = document.querySelector(".close");
  const loginForm = document.getElementById("loginForm");

  loginBtn.addEventListener("click", function() {
    loginModal.style.display = "block";
  });

  closeModal.addEventListener("click", function() {
    loginModal.style.display = "none";
  });

  window.addEventListener("click", function(event) {
    if (event.target == loginModal) {
      loginModal.style.display = "none";
    }
  });

  loginForm.addEventListener("submit", function(event) {
    event.preventDefault();
    // Implement your login logic here
    console.log("Username:", event.target.username.value);
    console.log("Password:", event.target.password.value);
  });
});

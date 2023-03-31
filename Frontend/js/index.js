document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".btnSet > button").forEach((btn) => {
        btn.addEventListener("click", (event) => {
            if (event.target.dataset) {
                document.querySelectorAll(".btnSet > button").forEach(e => {
                    e.classList.remove('active');
                    document.getElementById(e.value).style.display = "none";
                });
                btn.classList.add('active');
                document.getElementById(btn.value).style.display = "block";
            }
        });
    });

    const patientBtn = document.getElementById("registerPatientBtn");
    const clinicBtn = document.getElementById("registerClinicBtn");

    patientBtn.addEventListener("click", () => {
        window.location = "register_patient.html";
    });

    clinicBtn.addEventListener("click", () => {
        window.location = "register_clinic.html";
    });

    document.getElementById("patientBtn").click();
});
document.addEventListener("DOMContentLoaded", function () {
    const carousel = document.querySelector(".carousel"),
    firstImg = carousel.querySelectorAll("img")[0],
    arrowIcons = document.querySelectorAll(".wrapper i");

    let scrollWidth = carousel.scrollWidth - carousel.clientWidth;
    const showHideIcons = () => {
        arrowIcons[0].style.display = carousel.scrollLeft == 0 ? "none" : "block";
        arrowIcons[1].style.display = carousel.scrollLeft == scrollWidth ? "none" : "block";
    }

    showHideIcons();

    arrowIcons.forEach(icon => {
        icon.addEventListener("click", () => {
            console.log(carousel.scrollLeft);
            carousel.scrollLeft += icon.classList.contains("fa-angle-left") ? -firstImg.clientWidth : firstImg.clientWidth;
            setTimeout(() => showHideIcons(), 60);
        });
    });

    let isDragStart = false, prevPageX, prevScrollLeft;

    const dragStart = (e) => {
        isDragStart = true;
        prevPageX = e.pageX || e.touches[0].pageX;
        prevScrollLeft = carousel.scrollLeft;
    }

    const dragging = (e) => {
        if (!isDragStart) return;
        e.preventDefault();
        carousel.classList.add("dragging");
        let positionDiff = (e.pageX || e.touches[0].pageX) - prevPageX;
        carousel.scrollLeft = prevScrollLeft - positionDiff;
        showHideIcons();
    }

    const dragStop = () => {
        isDragStart = false;
        carousel.classList.remove("dragging");
    }

    carousel.addEventListener("mousedown", dragStart);
    carousel.addEventListener("touchstart", dragStart);

    carousel.addEventListener("touchmove", dragging);
    carousel.addEventListener("mousemove", dragging);
    
    carousel.addEventListener("touchend", dragStop);
    carousel.addEventListener("mouseup", dragStop);
    carousel.addEventListener("mouseleave", dragStop);
});


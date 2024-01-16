document.addEventListener('DOMContentLoaded', function () {
var swiper = new Swiper(".swiper-container", {
  slidesPerView: 7, // Number of slides to show at a time
  slidesPerGroup: 7, // Number of slides to advance when clicking next
  spaceBetween: 20, // Adjust the spacing between cards as needed
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});
});
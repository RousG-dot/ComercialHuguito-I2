document.addEventListener("DOMContentLoaded", function () {
  const sliderHtml = `
    <section class="banner text-center animate__animated animate__fadeIn">
      <div class="background-slider"></div>
      <div class="container content">
        <h1 class="display-4 mb-4">Contáctanos</h1>
      </div>
    </section>
  `;

  const sliderContainer = document.getElementById("slider");
  if (sliderContainer) {
    sliderContainer.innerHTML = sliderHtml;
  }
});

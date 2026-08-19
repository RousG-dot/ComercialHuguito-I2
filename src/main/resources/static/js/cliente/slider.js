// slider.js
document.addEventListener("DOMContentLoaded", function () {
  const sliderHtml = `
    <section class="banner text-center animate__animated animate__fadeIn">
      <div class="background-slider"></div>
      <div class="container content">
        <h1 class="display-4 mb-4">Bienvenidos a Comercial Huguito</h1>
        <a href="/servicios" class="btn btn-light btn-lg">Ver Productos</a>
      </div>
    </section>
  `;

  const sliderContainer = document.getElementById("slider");
  if (sliderContainer) {
    sliderContainer.innerHTML = sliderHtml;
  }
});

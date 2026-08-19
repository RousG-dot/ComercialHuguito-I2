document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("contacto-container");

  if (container) {
    container.innerHTML = `
      <section id="contacto" class="contact-section py-5">
        <div class="container">
          <h2 class="text-center mb-4 text-white animate__animated animate__fadeInDown">Contáctanos</h2>
          <form class="row g-3 needs-validation contact-form animate__animated animate__fadeInUp" novalidate>
            <div class="col-md-6">
              <label for="nombre" class="form-label text-white">Nombre</label>
              <input type="text" class="form-control" id="nombre" required>
            </div>
            <div class="col-md-6">
              <label for="correo" class="form-label text-white">Correo</label>
              <input type="email" class="form-control" id="correo" required>
            </div>
            <div class="col-md-6">
              <label for="telefono" class="form-label text-white">Teléfono</label>
              <input type="tel" class="form-control" id="telefono" required>
            </div>
            <div class="col-md-6">
              <label for="asunto" class="form-label text-white">Asunto</label>
              <input type="text" class="form-control" id="asunto" required>
            </div>
            <div class="col-12">
              <label for="mensaje" class="form-label text-white">Mensaje</label>
              <textarea class="form-control" id="mensaje" rows="4" required></textarea>
            </div>
            <div class="col-12 text-center">
              <button type="submit" class="btn btn-light px-5 mt-3 animate__animated animate__pulse animate__infinite">Enviar</button>
            </div>
          </form>
        </div>
      </section>
    `;
  }
});

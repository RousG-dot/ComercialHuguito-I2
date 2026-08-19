document.addEventListener("DOMContentLoaded", function () {
  const footer = document.getElementById("footer-container");

  if (footer) {
    footer.innerHTML = `
      <footer class="footer-section text-light py-5" style="background-color: #1a237e;">
        <div class="container">
          <div class="row g-4">
            <div class="col-md-4 animate__animated animate__fadeInLeft">
              <h5 class="fw-bold mb-3">Comercial Huguito</h5>
              <p>Comprometidos con tu bienestar desde 1998.</p>
            </div>
            <div class="col-md-4 animate__animated animate__fadeInUp">
              <h5 class="fw-bold mb-3">Enlaces Rápidos</h5>
              <ul class="list-unstyled">
                <li><a href="/about" class="footer-link text-light text-decoration-none">Sobre Nosotros</a></li>
                <li><a href="/services" class="footer-link text-light text-decoration-none">Servicios</a></li>
                <li><a href="/contact" class="footer-link text-light text-decoration-none">Contacto</a></li>
              </ul>
            </div>
            <div class="col-md-4 animate__animated animate__fadeInRight">
              <h5 class="fw-bold mb-3">Síguenos</h5>
              <div class="d-flex gap-3">
                <a href="#" class="social-icon text-light"><i class="bi bi-facebook"></i></a>
                <a href="#" class="social-icon text-light"><i class="bi bi-instagram"></i></a>
                <a href="#" class="social-icon text-light"><i class="bi bi-twitter-x"></i></a>
              </div>
            </div>
          </div>
          <div class="text-center mt-4 animate__animated animate__fadeInDown">
            <small>&copy; 2025 Comercial Huguito. Todos los derechos reservados.</small>
          </div>
        </div>
      </footer>
    `;
  }
});

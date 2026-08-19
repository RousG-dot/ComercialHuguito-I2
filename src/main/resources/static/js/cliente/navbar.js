document.addEventListener("DOMContentLoaded", function () {
  const navbar = `
  <nav class="navbar navbar-expand-lg navbar-custom">
    <div class="container">
      <a class="navbar-brand" href="/">Comercial Huguito</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon">
          <i class="bi bi-list text-white fs-2"></i>
        </span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link" href="/"><i class="bi bi-house-door-fill me-1"></i>Inicio</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/contacto"><i class="bi bi-telephone-fill me-1"></i>Contacto</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/servicios"><i class="bi bi-box-seam me-1"></i>Productos</a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a href="/register" class="btn btn-light me-2"><i class="bi bi-person-plus me-1"></i>Registro</a>
          </li>
          <li class="nav-item">
            <a href="/login" class="btn btn-outline-light"><i class="bi bi-box-arrow-in-right me-1"></i>Iniciar Sesión</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  `;

  // Inserta el navbar en el div con id="navbar"
  document.getElementById("navbar").innerHTML = navbar;
});

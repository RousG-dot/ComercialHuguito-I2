document.addEventListener("DOMContentLoaded", function () {
  const testimoniosSection = `
    <section class="bg-light py-5">
      <div class="container">
        <h2 class="text-center mb-5 animate-on-scroll">Testimonios de Clientes</h2>
        <div class="row g-4">
          <div class="col-md-4">
            <div class="card p-3 h-100 animate-on-scroll">
              <blockquote class="blockquote mb-0">
                <p>"Compré una máquina de coser y funciona de maravilla, ¡excelente calidad!"</p>
                <footer class="blockquote-footer">Ana Morales</footer>
              </blockquote>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card p-3 h-100 animate-on-scroll">
              <blockquote class="blockquote mb-0">
                <p>"La atención al cliente fue rápida y resolvieron todas mis dudas sobre repuestos."</p>
                <footer class="blockquote-footer">José Pérez</footer>
              </blockquote>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card p-3 h-100 animate-on-scroll">
              <blockquote class="blockquote mb-0">
                <p>"Recomiendo Comercial Huguito, sus productos para costura son de alta durabilidad."</p>
                <footer class="blockquote-footer">Claudia Rivas</footer>
              </blockquote>
            </div>
          </div>
        </div>
      </div>
    </section>
  `;
  document.getElementById("testimoniosContainer").innerHTML = testimoniosSection;
});

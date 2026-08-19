document.addEventListener("DOMContentLoaded", function () {
  const faqHTML = `
    <section class="py-5">
      <div class="container">
        <h2 class="text-center mb-4">Preguntas Frecuentes</h2>
        <div class="accordion" id="faqAccordion">
          <div class="accordion-item">
            <h2 class="accordion-header" id="faq1">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse1">
                ¿Qué tipos de máquinas de coser venden?
              </button>
            </h2>
            <div id="collapse1" class="accordion-collapse collapse show" data-bs-parent="#faqAccordion">
              <div class="accordion-body">
                Comercial Huguito ofrece máquinas familiares, industriales, bordadoras y overlock de diversas marcas reconocidas.
              </div>
            </div>
          </div>
          <div class="accordion-item">
            <h2 class="accordion-header" id="faq2">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse2">
                ¿Tienen repuestos y accesorios?
              </button>
            </h2>
            <div id="collapse2" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
              <div class="accordion-body">
                Sí, contamos con una amplia variedad de repuestos y accesorios para diferentes modelos de máquinas de coser.
              </div>
            </div>
          </div>
          <div class="accordion-item">
            <h2 class="accordion-header" id="faq3">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse3">
                ¿Ofrecen servicio técnico?
              </button>
            </h2>
            <div id="collapse3" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
              <div class="accordion-body">
                Por supuesto, brindamos mantenimiento y reparación profesional para todo tipo de máquinas de coser.
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  `;
  document.getElementById("faqContainer").innerHTML = faqHTML;
});

document.addEventListener("DOMContentLoaded", function () {
  const inputBusqueda = document.getElementById("busquedaNombre");
  const filtroPrecio = document.getElementById("filtroPrecio");
  const contenedor = document.getElementById("productosContainer");
  const cards = Array.from(contenedor.querySelectorAll(".producto-card"));

  function filtrar() {
    const texto = inputBusqueda.value.toLowerCase();
    let filtradas = cards.filter(card =>
      card.dataset.nombre.toLowerCase().includes(texto)
    );

    const orden = filtroPrecio.value;
    if (orden === "menor") {
      filtradas.sort((a, b) => parseFloat(a.dataset.precio) - parseFloat(b.dataset.precio));
    } else if (orden === "mayor") {
      filtradas.sort((a, b) => parseFloat(b.dataset.precio) - parseFloat(a.dataset.precio));
    }

    contenedor.innerHTML = ""; // Limpiar

    filtradas.forEach(card => {
      const col = document.createElement("div");
      col.className = "col-md-3";
      col.appendChild(card);
      contenedor.appendChild(col);
    });
  }

  inputBusqueda.addEventListener("input", filtrar);
  filtroPrecio.addEventListener("change", filtrar);

  // Mostrar modal al hacer clic en botón "Ver más"
  window.abrirModal = function (boton) {
    const card = boton.closest(".producto-card");
    const nombre = card.dataset.nombre;
    const descripcion = card.dataset.descripcion;
    const precio = card.dataset.precio;
    const imagen = card.dataset.imagen;

    document.getElementById("modalNombre").innerText = nombre;
    document.getElementById("modalDescripcion").innerText = descripcion;
    document.getElementById("modalPrecio").innerText = precio;
    document.getElementById("modalImagen").src = imagen;

    const modal = new bootstrap.Modal(document.getElementById('productoModal'));
    modal.show();
  };
});

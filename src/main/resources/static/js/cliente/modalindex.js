document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('.producto-card').forEach(function(card) {
      card.addEventListener('click', function () {
        const nombre = this.dataset.nombre;
        const descripcion = this.dataset.descripcion;
        const precio = this.dataset.precio;
        const imagen = this.dataset.imagen;

        document.getElementById("modalNombre").innerText = nombre;
        document.getElementById("modalDescripcion").innerText = descripcion;
        document.getElementById("modalPrecio").innerText = precio;
        document.getElementById("modalImagen").src = imagen;

        const modal = new bootstrap.Modal(document.getElementById('productoModal'));
        modal.show();
      });
    });
  });
document.addEventListener("DOMContentLoaded", function () {
    console.log("Script de estrellas cargado");

    // Obtenemos los radios ocultos
    const estrellasRadio = document.querySelectorAll("input[type='radio']");
    const estrellasDiv = document.querySelectorAll(".stars span");

    console.log("Inputs de radio:", estrellasRadio);
    console.log("Estrellas visuales:", estrellasDiv);

    // Pintamos las estrellas segun el valor que hay seleccionadoi
    function pintarEstrellas(valor) {
        console.log("Pintando estrellas para el valor:", valor);
        estrellasDiv.forEach(star => {
            const starValue = parseInt(star.getAttribute("data-value"));
            if (starValue <= valor) {
                star.classList.add("seleccionada");
            } else {
                star.classList.remove("seleccionada");
            }
        });
    }

    // Permitimos que el usuario puede cambiar las estrellas
    estrellasDiv.forEach(star => {
        star.addEventListener("click", function () {
            const value = parseInt(this.getAttribute("data-value"));
            console.log("Clic en la estrella con valor:", value);

            const radio = document.querySelector(`input[type='radio'][value='${value}']`);
            if (radio) {

                estrellasRadio.forEach(r => r.checked = false);

                radio.checked = true;

                pintarEstrellas(value);
            }
        });
    });
});
document.addEventListener("DOMContentLoaded", function () {
    console.log("Script de estrellas cargado");

    const estrellasRadio = document.querySelectorAll("table.star-radio input[type='radio']");
    const estrellasDiv = document.querySelectorAll(".stars span");

    console.log("Inputs de radio:", estrellasRadio);
    console.log("Estrellas visuales:", estrellasDiv);

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

    let valorSeleccionado = 0;
    estrellasRadio.forEach(radio => {
        if (radio.checked) {
            valorSeleccionado = parseInt(radio.value);
            console.log("Valor seleccionado al cargar:", valorSeleccionado);
        }
    });

    pintarEstrellas(valorSeleccionado);


    estrellasDiv.forEach(star => {
        star.addEventListener("click", function () {
            const value = parseInt(this.getAttribute("data-value"));
            console.log("Clic en la estrella con valor:", value);


            const radio = document.querySelector(`table.star-radio input[type='radio'][value='${value}']`);
            if (radio) {

                estrellasRadio.forEach(r => r.checked = false);
                radio.checked = true;
                valorSeleccionado = value;
                pintarEstrellas(valorSeleccionado);
            }
        });
    });
});
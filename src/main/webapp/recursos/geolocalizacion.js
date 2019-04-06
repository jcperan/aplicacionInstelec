/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var latitud;
var longitud;

function localizarActual() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(coordenadas, errorLocalizar);
    } else {
        alert('Navegador no soporta geolocalizacion.');
    }
}

function verPosicion() {
    longitud = document.getElementById("formulario:txtLongitud").textContent;
    latitud = document.getElementById("formulario:txtLatitud").textContent;

    currentMarker = new google.maps.Marker({
        position: new google.maps.LatLng(latitud, longitud)
    });
    PF('map').addOverlay(currentMarker);
}

function marcaPosicion() {
    longitud = document.getElementById("formulario:txtLongitud").value;
    latitud = document.getElementById("formulario:txtLatitud").value;

    currentMarker = new google.maps.Marker({
        position: new google.maps.LatLng(latitud, longitud)
    });
    PF('map').addOverlay(currentMarker);
}

function coordenadas(position) {
    latitud = position.coords.latitude;
    longitud = position.coords.longitude;
    document.getElementById("formulario:txtLongitud").value = longitud;
    document.getElementById("formulario:txtLatitud").value = latitud;
    marcaPosicion();
    $("#formulario\\:cmdActualizar").click();
}

function errorLocalizar(err) {
    /*Controlamos los posibles errores */
    if (err.code == 0)
        alert("Oops! Error no Definido.");
    if (err.code == 1)
        alert("Oops! No has aceptado compartir tu posicion");
    if (err.code == 2)
        alert("Oops! No se puede obtener la posicion actual");
    if (err.code == 3)
        alert("Oops! Superado el tiempo de espera");
}

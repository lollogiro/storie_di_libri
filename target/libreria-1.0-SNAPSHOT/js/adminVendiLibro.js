document.addEventListener("DOMContentLoaded", checkSessionAdmin());

function checkSessionAdmin(){
  let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        gestisciRispostaCheckSessionAdmin(this);
    };
    xhttp.open("POST","http://localhost:8080/libreria/CheckSessionAdmin", true);
    xhttp.send();
}

function gestisciRispostaCheckSessionAdmin(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x){}
    else{
        window.location.href = "../";
    }
  }
}

document.addEventListener("DOMContentLoaded", function(){
	document.getElementById("submit").addEventListener('click', inviaDatiVendi);
});

function inviaDatiVendi(){
    let titolo = document.getElementById('titolo').value;
    let autore = document.getElementById('autore').value;
    let genere = document.getElementById('genere').value;
    let prezzo = document.getElementById('prezzo').value;
    let quantita = document.getElementById('quantita').value;

    let jsonRequest = {"titolo": titolo, "autore": autore, "genere": genere, "prezzo": prezzo, "quantita": quantita};

    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseDatiVendi(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/SellLibro", true);
    xhttp.send(stringRequest);
}

function manageResponseDatiVendi(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x != "qOK"){
        alert("Qualcosa è andato storto!")
    }
    else{
        alert("Quantità modificata con successo!");
    }
  }
}
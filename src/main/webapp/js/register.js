document.addEventListener("DOMContentLoaded", checkSession());

function checkSession(){
    
  let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        gestisciRispostaCheckSession(this);
    };
    xhttp.open("POST","http://localhost:8080/libreria/CheckSession", true);
    xhttp.send();
}

function gestisciRispostaCheckSession(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x){
        window.location.href = "../";
        
    }
    else{}
  }
}

document.addEventListener("DOMContentLoaded", function(){
	document.getElementById("submit").addEventListener('click', inviaDatiRegister);
});

function inviaDatiRegister(){
    let nome = document.getElementById('nome').value;
    let cognome = document.getElementById('cognome').value;
    let telefono = document.getElementById('telefono').value;
    let via = document.getElementById('via').value;
    let civico = document.getElementById('civico').value;
    let citta = document.getElementById('citta').value;
    let provincia = document.getElementById('provincia').value;
    let cap = document.getElementById('cap').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    let jsonRequest = {"nome": nome, "cognome": cognome, "telefono": telefono, "via": via, "civico": civico, "citta": citta, "provincia": provincia, "cap": cap, "email": email, "password": password};

    let stringRequest = JSON.stringify(jsonRequest);
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponse(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/ManageRegister", true);
    xhttp.send(stringRequest);
}

function manageResponse(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x === "ok"){
        window.location.href = "./login.html";
    }
    else if(x === "emailEX"){
        alert("E-Mail già utilizzata");
    }
    else{
        alert("Si è verificato un errore durante l'operazione, riprovare");
    }
  }
}
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
    document.getElementById("cerca").addEventListener('click', function(){
        location.href = "./adminCercaLibro.html";
    });
    document.getElementById("inserisci").addEventListener('click', function(){
        location.href = "./adminAddLibro.html";
    });
    document.getElementById("vendi").addEventListener('click', function(){
        location.href = "./adminVendiLibro.html";
    });
    document.getElementById("prestito").addEventListener('click', function(){
        location.href = "./adminVisualizzaPrestiti.html";
    });
    document.getElementById("acquisto").addEventListener('click', function(){
        location.href = "./adminVisualizzaAcquisti.html";
    });
});
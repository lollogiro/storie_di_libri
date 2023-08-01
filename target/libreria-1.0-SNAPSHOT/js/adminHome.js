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
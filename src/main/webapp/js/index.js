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
        document.getElementById("login").classList.add("none");
        document.getElementById("loginTendina").classList.add("none");
        
    }
    else{
        document.getElementById("profilo").classList.add("none");
        document.getElementById("carrello").classList.add("none");
        document.getElementById("logout").classList.add("none");
        document.getElementById("profiloTendina").classList.add("none");
        document.getElementById("carrelloTendina").classList.add("none");
        document.getElementById("logoutTendina").classList.add("none");
    }
  }
}
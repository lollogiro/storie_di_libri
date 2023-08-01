document.addEventListener("DOMContentLoaded", function(){
    checkSession();
    checkSessionAdmin();
});

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
        window.location.href = "./";
        
    }
    else{}
  }
}

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
    if(x){
        window.location.href = "./admin/adminHome.html";
        
    }
    else{}
  }
}

document.addEventListener("DOMContentLoaded", function(){
	document.getElementById("submit").addEventListener('click', inviaDatiLogin);
});

function inviaDatiLogin(){
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    let jsonRequest = {"email": email, "password": password};

    let stringRequest = JSON.stringify(jsonRequest);
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseDatiLogin(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/ManageLogin", true);
    xhttp.send(stringRequest);
}

function manageResponseDatiLogin(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x == "true"){
        window.location.href = "./admin/adminHome.html";
    }
    else if(x == "false"){
        alert("Credenziali errate");
    }
    else{
        window.location.href = "./admin/adminHome.html";
    }
  }
}
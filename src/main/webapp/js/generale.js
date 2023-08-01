function showPassword(){
  var p = document.getElementById("password");
  var open = document.getElementById("eye-open");
  var close = document.getElementById("eye-close");

  if(p.type == 'password'){
      p.type = "text";
      open.style.removeProperty("display");
      close.style.display = "none";
  }
  else{
      p.type = "password";
      open.style.display = "none";
      close.style.removeProperty("display");
  }
}

function showPassword1(){
  var p = document.getElementById("verificaPassword");
  var open = document.getElementById("eye-open1");
  var close = document.getElementById("eye-close1");
  if(p.type == 'password'){
      p.type = "text";
      open.style.removeProperty("display");
      close.style.display = "none";
  }
  else{
      p.type = "password";
      open.style.display = "none";
      close.style.removeProperty("display");
  }
}

function logoutProfile(){
    console.log("entra in logout");
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseLogout(this);
    };    
    xhttp.open("GET", "http://localhost:8080/libreria/ProfileLogout", true);
    xhttp.send();
}

function manageResponseLogout(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x == "success"){
        console.log("Logout effettuato con successo");
    }
    else{
        console.log("Logout error");
    }
  }
}
    
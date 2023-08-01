if (document.readyState == 'loading') {
    document.addEventListener("DOMContentLoaded", function(){
        checkSession();
        mostraLibri();
});
} else {
    checkSession();
    mostraLibri();
}

let session = false;

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
    session = x;
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

function mostraLibri(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        gestisciRispostaMostraLibri(this);
    };
    xhttp.open("GET","http://localhost:8080/libreria/ShowLibri", true);
    xhttp.send();
}

function gestisciRispostaMostraLibri(e){
    if (e.readyState === 4 && e.status === 200) {
        let x = JSON.parse( e.responseText );
        if(x.libri.length > 0){
            if(session == true){
                for(var i in x.libri){
                    if(i == 0){
                        document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                                    "<th>Titolo</th>"+
                                                                                    "<th>Autore</th>"+
                                                                                    "<th>Genere</th>"+
                                                                                    "<th>Prezzo</th>"+
                                                                                    "<th>Quantità</th>"+
                                                                                "</thead>"+    
                                                                                "<tbody>"+
                                                                                    "<tr class='item'>"+
                                                                                        "<td class='item-id'></td>"+
                                                                                        "<td class='item-id'>"+x.libri[i].id_libro+"</td>"+
                                                                                        "<td class='item-titolo'>"+x.libri[i].titolo+"</td>"+
                                                                                        "<td class='item-autore'>"+x.libri[i].autore+"</td>"+
                                                                                        "<td class='item-genere'>"+x.libri[i].genere+"</td>"+
                                                                                        "<td class='item-prezzo'>€ "+x.libri[i].prezzo+"</td>"+
                                                                                        "<td class='item-quantita'>"+x.libri[i].quantita+"</td>"+
                                                                                        
                                                                                        "<td class='item-b'><button class='item-button btn' type='button'>Aggiungi al carrello</button></td>"+
                                                                                        "</td>"+
                                                                                    "</tr>";
                                                                                
                                                                                    
                                                                                    
                    }
                    else{
                        document.getElementById("tabellaMostra").innerHTML +=   "<tr class='item'>"+
                                                                                    "<td class='item-id'></td>"+
                                                                                    "<td class='item-id'>"+x.libri[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.libri[i].titolo+"</td>"+
                                                                                    "<td class='item-autore'>"+x.libri[i].autore+"</td>"+
                                                                                    "<td class='item-genere'>"+x.libri[i].genere+"</td>"+
                                                                                    "<td class='item-prezzo'>€ "+x.libri[i].prezzo+"</td>"+
                                                                                    "<td class='item-quantita'>"+x.libri[i].quantita+"</td>"+
                                                                                    
                                                                                    "<td class='item-b'><button class='item-button btn' type='button'>Aggiungi al carrello</button></td>"+
                                                                                    "</td>"+
                                                                                "</tr>";
                                                                                
                                                                                
                    }
                }
            }
            else{
                for(var i in x.libri){
                    if(i == 0){
                        document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                                    "<th>Titolo</th>"+
                                                                                    "<th>Autore</th>"+
                                                                                    "<th>Genere</th>"+
                                                                                    "<th>Prezzo</th>"+
                                                                                    "<th>Quantità</th>"+
                                                                                "</thead>"+    
                                                                                "<tbody>"+
                                                                                    "<tr>"+
                                                                                        "<td class='item-id'>"+x.libri[i].id_libro+"</td>"+
                                                                                        "<td class='item-titolo'>"+x.libri[i].titolo+"</td>"+
                                                                                        "<td class='item-autore'>"+x.libri[i].autore+"</td>"+
                                                                                        "<td class='item-genere'>"+x.libri[i].genere+"</td>"+
                                                                                        "<td class='item-prezzo'>€ "+x.libri[i].prezzo+"</td>"+
                                                                                        "<td class='item-quantita'>"+x.libri[i].quantita+"</td>"+
                                                                                    "</tr>";
                    }
                    else{
                        document.getElementById("tabellaMostra").innerHTML +=   "<tr>"+
                                                                                    "<td class='item-id'>"+x.libri[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.libri[i].titolo+"</td>"+
                                                                                    "<td class='item-autore'>"+x.libri[i].autore+"</td>"+
                                                                                    "<td class='item-genere'>"+x.libri[i].genere+"</td>"+
                                                                                    "<td class='item-prezzo'>€ "+x.libri[i].prezzo+"</td>"+
                                                                                    "<td class='item-quantita'>"+x.libri[i].quantita+"</td>"+
                                                                                "</tr>";
                    }
                }
            }
            
            document.getElementById("tabellaMostra").innerHTML +=   "</tbody>";
        }
        else{
            document.getElementById("tabellaMostra").innerHTML =   "";
            document.getElementById("emptyDB").innerHTML +=   "<h3>NON CI SONO LIBRI IN QUESTO DATABASE</h3>";
        }
        ready();
    }
}

function filtraLibri(){
    let titolo = document.getElementById("titolo").value;
    let autore = document.getElementById("autore").value;
    let genere = document.getElementById("genere").value;
    
    
    let jsonRequest = {"titolo": titolo, "autore": autore, "genere": genere};

    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseDatiRicerca(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/ShowCercaLibro", true);
    xhttp.send(stringRequest);
}

function manageResponseDatiRicerca(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x.libri.length > 0){
        for(var i in x.libri){
            if(i == 0){
                document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                            "<th>Titolo</th>"+
                                                                            "<th>Autore</th>"+
                                                                            "<th>Genere</th>"+
                                                                            "<th>Prezzo</th>"+
                                                                            "<th>Quantità</th>"+
                                                                        "</thead>"+    
                                                                        "<tbody>"+
                                                                            "<tr class='item'>"+
                                                                                "<td class='item-id'>"+x.libri[i].id_libro+"</td>"+
                                                                                "<td class='item-titolo'>"+x.libri[i].titolo+"</td>"+
                                                                                "<td class='item-autore'>"+x.libri[i].autore+"</td>"+
                                                                                "<td class='item-genere'>"+x.libri[i].genere+"</td>"+
                                                                                "<td class='item-prezzo'>€ "+x.libri[i].prezzo+"</td>"+
                                                                                "<td class='item-quantita'>"+x.libri[i].quantita+"</td>"+
                                                                            "</tr>";



            }
            else{
                document.getElementById("tabellaMostra").innerHTML +=   "<tr class='item'>"+
                                                                            "<td class='item-id'>"+x.libri[i].id_libro+"</td>"+
                                                                            "<td class='item-titolo'>"+x.libri[i].titolo+"</td>"+
                                                                            "<td class='item-autore'>"+x.libri[i].autore+"</td>"+
                                                                            "<td class='item-genere'>"+x.libri[i].genere+"</td>"+
                                                                            "<td class='item-prezzo'>€ "+x.libri[i].prezzo+"</td>"+
                                                                            "<td class='item-quantita'>"+x.libri[i].quantita+"</td>"+
                                                                        "</tr>";


            }
        }
        document.getElementById("tabellaMostra").innerHTML +=   "</tbody>";
        document.getElementById("emptyDB").innerHTML =   "";
    }
    else{
        document.getElementById("tabellaMostra").innerHTML =   "";
        document.getElementById("emptyDB").innerHTML =   "<h3>NON CI SONO LIBRI CHE CORRISPONDONO<br>A QUESTI CRITERI DI RiCERCA</h3>";
    }
  }
}

function ready(){
    const arrClassButton = document.querySelectorAll(".item-button");
    for (let i of arrClassButton) {
      i.addEventListener("click", (e) => {
        addToCart(e);
      });
    }
}

function addToCart(event) {
    let button = event.target;
    let shopItem = button.parentElement.parentElement;
    
    let id = shopItem.getElementsByClassName('item-id')[1].innerText;
    
    let jsonRequest = {"id_libro": id};

    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        gestisciRispostaAddToCart(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/AddToCart", true);
    xhttp.send(stringRequest);
}

function gestisciRispostaAddToCart(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    alert("Libro aggiunto al carrello!");
  }
}
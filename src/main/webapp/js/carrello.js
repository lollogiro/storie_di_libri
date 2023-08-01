if (document.readyState == 'loading') {
    document.addEventListener("DOMContentLoaded", function(){
        checkSession();
        mostraCarrelloLibri();
});
} else {
    checkSession();
    mostraCarrelloLibri();
}

let session = "false";

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
    }
    else{
        window.location.href = "../";
    }
  }
}

function mostraCarrelloLibri(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        gestisciRispostaMostraCarrelloLibri(this);
    };
    xhttp.open("GET","http://localhost:8080/libreria/ShowCartLibri", true);
    xhttp.send();
}

function gestisciRispostaMostraCarrelloLibri(e){
    if (e.readyState === 4 && e.status === 200) {
        let x = JSON.parse( e.responseText );
        if(x.carrellolibri.length > 0){
            for(var i in x.carrellolibri){
                if(i == 0){
                    document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                                "<th>Titolo</th>"+
                                                                                "<th>Autore</th>"+
                                                                                "<th>Genere</th>"+
                                                                                "<th>Prezzo</th>"+
                                                                                "<th>Tipo</th>"+
                                                                                "<th>Quantità</th>"+
                                                                            "</thead>"+    
                                                                            "<tbody>"+
                                                                                "<tr class='item'>"+
                                                                                    "<td class='item-id'>"+x.carrellolibri[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.carrellolibri[i].titolo+"</td>"+
                                                                                    "<td class='item-autore'>"+x.carrellolibri[i].autore+"</td>"+
                                                                                    "<td class='item-genere'>"+x.carrellolibri[i].genere+"</td>"+
                                                                                    "<td class='item-prezzo'>€ "+x.carrellolibri[i].prezzo+"</td>"+
                                                                                    "<td><select id='tipo' class='item-tipo'>"+
                                                                                                                "<option value='"+x.carrellolibri[i].tipo+"' selected>"+x.carrellolibri[i].tipo+"</option>"+
                                                                                                                "<option value='Acquisto'>Acquisto</option>"+
                                                                                                                "<option value='Prestito'>Prestito</option>"+
                                                                                                            "</select></td>"+
                                                                                    "<td><input class='item-number' type='number' id='quantita' [1-"+x.carrellolibri[i].quantita+"]{2} min='1' max='"+x.carrellolibri[i].quantita+"' value='"+x.carrellolibri[i].carrello+"'></td>"+
                                                                                    "<td class='item-b'><button class='item-button btn' type='button'>Rimuovi dal carrello</button></td>"+
                                                                                    "</td>"+
                                                                                "</tr>";



                }
                else{
                    document.getElementById("tabellaMostra").innerHTML +=   "<tr class='item'>"+
                                                                                "<td class='item-id'>"+x.carrellolibri[i].id_libro+"</td>"+
                                                                                "<td class='item-titolo'>"+x.carrellolibri[i].titolo+"</td>"+
                                                                                "<td class='item-autore'>"+x.carrellolibri[i].autore+"</td>"+
                                                                                "<td class='item-genere'>"+x.carrellolibri[i].genere+"</td>"+
                                                                                "<td class='item-prezzo'>€ "+x.carrellolibri[i].prezzo+"</td>"+
                                                                                "<td><select id='tipo' class='item-tipo'>"+
                                                                                                                "<option value='"+x.carrellolibri[i].tipo+"' selected>"+x.carrellolibri[i].tipo+"</option>"+
                                                                                                                "<option value='Acquisto'>Acquisto</option>"+
                                                                                                                "<option value='Prestito'>Prestito</option>"+
                                                                                                        "</select></td>"+
                                                                                "<td><input class='item-number' type='number' id='quantita' [1-"+x.carrellolibri[i].quantita+"]{2} min='1' max='"+x.carrellolibri[i].quantita+"' value='"+x.carrellolibri[i].carrello+"'></td>"+
                                                                                "<td class='item-b'><button class='item-button btn' type='button'>Rimuovi dal carrello</button></td>"+
                                                                                "</td>"+
                                                                            "</tr>";


                }
            }
            document.getElementById("tabellaMostra").innerHTML +=   "</tbody>";
        }
        else{
            document.getElementById("tabellaMostra").innerHTML =   "";
            document.getElementById("emptyDB").innerHTML +=   "<h3>NON CI SONO ANCORA ELEMENTI NEL CARRELLO</h3>";
            document.getElementById("cartTotal").innerHTML =   "";
            document.getElementById("checkout").innerHTML =   "";
        }
        ready();
    }
}
function ready(){
    
    const arrClassNumber = document.querySelectorAll(".item-number");
    for (let i of arrClassNumber) {
      i.addEventListener("change", (e) => {
        updateBookCart(e);
      });
    }
    
    const arrClassSelect = document.querySelectorAll(".item-tipo");
    for (let i of arrClassSelect) {
      i.addEventListener("change", (e) => {
        updateBookCart(e);
      });
    }
    
    const arrClassButton = document.querySelectorAll(".item-button");
    for (let i of arrClassButton) {
      i.addEventListener("click", (e) => {
        deleteBookCart(e);
      });
    }
    if(document.getElementById("tabellaMostra").rows.length > 1){
        showCartTotal();
    }
}

function updateBookCart(event) {
    let input = event.target;
    let shopItem = input.parentElement.parentElement;
    
    let id = shopItem.getElementsByClassName('item-id')[0].innerText;
    
    let clicked = input.value.toString();
    let jsonRequest;
    
    if(clicked == "Acquisto" || clicked == "Prestito"){
        let quantita = shopItem.getElementsByClassName('item-number')[0].value;
        jsonRequest = {"id_libro": id, "quantita": quantita, "tipo": clicked};
    }
    else{
        let tipo = shopItem.getElementsByClassName('item-tipo')[0].value;
        jsonRequest = {"id_libro": id, "quantita": clicked, "tipo": tipo};
    }
    
    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        gestisciRispostaUpdateBookCart(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/UpdateBookCart", true);
    xhttp.send(stringRequest);
    
}

function gestisciRispostaUpdateBookCart(e){
    if (e.status === 200 && e.readyState === 4) {
        let x = JSON.parse(e.responseText);
        mostraCarrelloLibri();
    }
}


function deleteBookCart(event) {
    let button = event.target;
    let shopItem = button.parentElement.parentElement;
    
    let id = shopItem.getElementsByClassName('item-id')[0].innerText;
    let tipo = shopItem.getElementsByClassName('item-tipo')[0].value;
    
    let jsonRequest = {"id_libro": id, "quantita": 0, "tipo": tipo};

    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        gestisciRispostaDeleteBookCart(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/UpdateBookCart", true);
    xhttp.send(stringRequest);
    
}

function gestisciRispostaDeleteBookCart(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    mostraCarrelloLibri();
  }
}

function showCartTotal(){
    var righeCarrello = document.querySelectorAll('.item');
    let totale = 0;
    for (var i = 0; i < righeCarrello.length; i++) {
        var item = righeCarrello[i];
        if(item.querySelectorAll('.item-tipo')[0].value == "Acquisto"){
            var prezzo = item.querySelectorAll('.item-prezzo')[0];
            var prezzoFloat = parseFloat(prezzo.innerText.replace('€', ''));
            var quantita = item.querySelectorAll('.item-number')[0].value;
            totale = totale + (prezzoFloat * quantita);
        }
    }
    totale = Math.round(totale * 100) / 100;
    document.getElementById("cartTotal").innerHTML = "<h5>Totale: €"+totale+"</h5>";
    document.getElementById("checkout").innerHTML =  "<button class='item-button btn' type='button'>Vai al checkout</button>";
    document.getElementById("checkout").addEventListener("click", function(){
        window.location.href = "./checkout.html";
    });
}

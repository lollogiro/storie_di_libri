if (document.readyState == 'loading') {
    document.addEventListener("DOMContentLoaded", function(){
        checkSession();
        mostraCheckoutLibri();
    });
} else {
    checkSession();
    mostraCheckoutLibri();
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

function mostraCheckoutLibri(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        gestisciRispostaMostraCheckoutLibri(this);
    };
    xhttp.open("GET","http://localhost:8080/libreria/ShowCheckout", true);
    xhttp.send();
}

function gestisciRispostaMostraCheckoutLibri(e){
    if (e.readyState === 4 && e.status === 200) {
        let x = JSON.parse( e.responseText );
        if(x.checkout.length > 0){
            for(var i in x.checkout){
                if(i == 0){
                    if(x.checkout[i].tipo_acquisto == "Prestito"){
                        document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                                "<th>Titolo</th>"+
                                                                                "<th>Quantità</th>"+
                                                                                "<th>Tipo Acquisizione</th>"+
                                                                                "<th>Tipo Ritiro</th>"+
                                                                                "<th>Tipo Consegna</th>"+
                                                                            "</thead>"+    
                                                                            "<tbody>"+
                                                                                "<tr class='item'>"+
                                                                                    "<td class='item-id'>"+x.checkout[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.checkout[i].titolo+"</td>"+
                                                                                    "<td class='item-quantita'>"+x.checkout[i].quantita+"</td>"+
                                                                                    "<td class='item-acquisto'>"+x.checkout[i].tipo_acquisto+"</td>"+
                                                                                    "<td><select id='ritiro' class='item-ritiro'>"+
                                                                                                                "<option value='"+x.checkout[i].tipo_ritiro+"' selected>"+x.checkout[i].tipo_ritiro+"</option>"+
                                                                                                                "<option value='Negozio'>Negozio</option>"+
                                                                                                                "<option value='Domicilio'>Domicilio</option>"+
                                                                                                            "</select></td>"+
                                                                                    "<td><select id='consegna' class='item-consegna'>"+
                                                                                                                "<option value='"+x.checkout[i].tipo_consegna+"' selected>"+x.checkout[i].tipo_consegna+"</option>"+
                                                                                                                "<option value='Negozio'>Negozio</option>"+
                                                                                                                "<option value='Domicilio'>Domicilio</option>"+
                                                                                                            "</select></td>"+
                                                                                "</tr>";
                    }
                    else{
                        document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                                "<th>Titolo</th>"+
                                                                                "<th>Quantità</th>"+
                                                                                "<th>Tipo</th>"+
                                                                                "<th>Tipo Ritiro</th>"+
                                                                                "<th>Tipo Consegna</th>"+
                                                                            "</thead>"+    
                                                                            "<tbody>"+
                                                                                "<tr class='item'>"+
                                                                                    "<td class='item-id'>"+x.checkout[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.checkout[i].titolo+"</td>"+
                                                                                    "<td class='item-quantita'>"+x.checkout[i].quantita+"</td>"+
                                                                                    "<td class='item-acquisto'>"+x.checkout[i].tipo_acquisto+"</td>"+
                                                                                    "<td><select id='ritiro' class='item-ritiro'>"+
                                                                                                                "<option value='"+x.checkout[i].tipo_ritiro+"' selected>"+x.checkout[i].tipo_ritiro+"</option>"+
                                                                                                                "<option value='Negozio'>Negozio</option>"+
                                                                                                                "<option value='Domicilio'>Domicilio</option>"+
                                                                                                            "</select></td>"+
                                                                                    "<td><input type='hidden'/></td>"+
                                                                                    
                                                                                "</tr>";
                    }    
                }
                else{
                    if(x.checkout[i].tipo_acquisto == "Prestito"){
                        document.getElementById("tabellaMostra").innerHTML +=    "<tbody>"+
                                                                                "<tr class='item'>"+
                                                                                    "<td class='item-id'>"+x.checkout[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.checkout[i].titolo+"</td>"+
                                                                                    "<td class='item-quantita'>"+x.checkout[i].quantita+"</td>"+
                                                                                    "<td class='item-acquisto'>"+x.checkout[i].tipo_acquisto+"</td>"+
                                                                                    "<td><select id='ritiro' class='item-ritiro'>"+
                                                                                                                "<option value='"+x.checkout[i].tipo_ritiro+"' selected>"+x.checkout[i].tipo_ritiro+"</option>"+
                                                                                                                "<option value='Negozio'>Negozio</option>"+
                                                                                                                "<option value='Domicilio'>Domicilio</option>"+
                                                                                                            "</select>"+
                                                                                    "</td>"+
                                                                                    "<td><select id='consegna' class='item-consegna'>"+
                                                                                                                "<option value='"+x.checkout[i].tipo_consegna+"' selected>"+x.checkout[i].tipo_consegna+"</option>"+
                                                                                                                "<option value='Negozio'>Negozio</option>"+
                                                                                                                "<option value='Domicilio'>Domicilio</option>"+
                                                                                                            "</select>"+
                                                                                    "</td>"+
                                                                                "</tr>";
                    }
                    else{
                        document.getElementById("tabellaMostra").innerHTML +=    "<tbody>"+
                                                                                "<tr class='item'>"+
                                                                                    "<td class='item-id'>"+x.checkout[i].id_libro+"</td>"+
                                                                                    "<td class='item-titolo'>"+x.checkout[i].titolo+"</td>"+
                                                                                    "<td class='item-quantita'>"+x.checkout[i].quantita+"</td>"+
                                                                                    "<td class='item-acquisto'>"+x.checkout[i].tipo_acquisto+"</td>"+
                                                                                    "<td><select id='ritiro' class='item-ritiro'>"+
                                                                                                                "<option value='"+x.checkout[i].tipo_ritiro+"' selected>"+x.checkout[i].tipo_ritiro+"</option>"+
                                                                                                                "<option value='Negozio'>Negozio</option>"+
                                                                                                                "<option value='Domicilio'>Domicilio</option>"+
                                                                                                            "</select></td>"+
                                                                                    "<td></td>"+
                                                                                "</tr>";
                    }
                }
            }
            document.getElementById("tabellaMostra").innerHTML +=   "</tbody>";
            document.getElementById("emptyDB").innerHTML +=   "";
            document.getElementById("checkout").innerHTML =     "<form method='POST' class='center'>"+
                                                                    '<div class="txt-field">'+
                                                                        '<input type="text" id="intestatario" required>'+
                                                                        '<span></span>'+
                                                                        '<label for="intestatario">Intestatario/a della carta</label>'+
                                                                    '</div>'+
                                                                    '<div class="txt-field">'+
                                                                        '<input type="text" id="numero" required>'+
                                                                        '<span></span>'+
                                                                        '<label for="numero">Numero della carta</label>'+
                                                                    '</div>'+
                                                                    '<div class="txt-field">'+
                                                                        '<input type="text" id="scadenza" required>'+
                                                                        '<span></span>'+
                                                                        '<label for="scadenza">Data di scadenza della carta</label>'+
                                                                    '</div>'+
                                                                    '<div class="txt-field">'+
                                                                        '<input type="text" id="cvv" required>'+
                                                                        '<span></span>'+
                                                                        '<label for="cvv">CVV</label>'+
                                                                    '</div>'+
                                                                '</form>'+
                                                                "<button id='concludi' class='item-button btn' type='button'>Concludi ordine</button>";
            ready();
        }
        else{
            document.getElementById("tabellaMostra").innerHTML =   "";
            document.getElementById("emptyDB").innerHTML +=   "<h3>NON CI SONO ANCORA ELEMENTI NEL CARRELLO</h3>";
            document.getElementById("checkout").innerHTML =   "";
        }
        
    }
}

function ready(){
    const arrClassSelect = document.querySelectorAll(".item-ritiro");
    for (let i of arrClassSelect) {
      i.addEventListener("change", (e) => {
        updateCheckout(e);
      });
    }
    
    const arrClassSelect1 = document.querySelectorAll(".item-consegna");
    for (let i of arrClassSelect1) {
      i.addEventListener("change", (e) => {
        updateCheckout(e);
      });
    }
    
    document.getElementById('concludi').addEventListener('click', function(){
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            gestisciRispostaFinishCheckout(this);
        };    
        xhttp.open("POST", "http://localhost:8080/libreria/Checkout", true);
        xhttp.send();
    });
    
    function gestisciRispostaFinishCheckout(e){
        if (e.status === 200 && e.readyState === 4) {
            let x = JSON.parse(e.responseText);
            if(x){
                alert("Ordine concluso con successo");
                window.location.href = "./dashboard.html";
            }
            else{
                alert("Ordine non concluso a causa di un errore");
            }
        }
    }   
    
    
}

function updateCheckout(event){
    let input = event.target;
    let shopItem = input.parentElement.parentElement;
    
    let id = shopItem.getElementsByClassName('item-id')[0].innerText;
    
    let jsonRequest;
    if(input.className.toString() == 'item-ritiro'){
        if(shopItem.getElementsByClassName('item-acquisto')[0].innerText == "Prestito"){
            jsonRequest = {"id_libro": id, "tipo_ritiro": input.value.toString(), "tipo_consegna": shopItem.getElementsByClassName("item-consegna")[0].value.toString()};
        }
        else{
            jsonRequest = {"id_libro": id, "tipo_ritiro": input.value.toString(), "tipo_consegna": "null"};
        }
    }
    else{
            jsonRequest = {"id_libro": id, "tipo_ritiro": shopItem.getElementsByClassName("item-ritiro")[0].value.toString(), "tipo_consegna": input.value.toString()};
    }
    
    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        gestisciRispostaUpdateCheckout(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/UpdateCheckout", true);
    xhttp.send(stringRequest);
}

function gestisciRispostaUpdateCheckout(e){
    if (e.status === 200 && e.readyState === 4) {
        let x = JSON.parse(e.responseText);
        mostraCheckoutLibri();
    }
}
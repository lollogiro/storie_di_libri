if (document.readyState == 'loading') {
    document.addEventListener("DOMContentLoaded", function(){
        checkSession();
        visualizzaPrestiti();
        visualizzaAcquisti();
});
} else {
    checkSession();
    visualizzaPrestiti();
    visualizzaAcquisti();
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

function visualizzaPrestiti(){
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseDatiVisualizza(this);
    };    
    xhttp.open("GET", "http://localhost:8080/libreria/ShowPrestitiUtente", true);
    xhttp.send();
}

function manageResponseDatiVisualizza(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x.prestiti.length > 0){
        for(var i in x.prestiti){
            if(i == 0){
                document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                            "<th>Titolo</th>"+
                                                                            "<th>Data Inizio</th>"+
                                                                            "<th>Data Fine</th>"+
                                                                            "<th>Quantità</th>"+
                                                                            "<th>Tipo Ritiro</th>"+
                                                                            "<th>Tipo Consegna</th>"+
                                                                        "</thead>"+    
                                                                        "<tbody>"+
                                                                            "<tr>"+
                                                                                "<td>"+x.prestiti[i].titolo+"</td>"+
                                                                                "<td>"+x.prestiti[i].data_inizio+"</td>"+
                                                                                "<td>"+x.prestiti[i].data_fine+"</td>"+
                                                                                "<td>"+x.prestiti[i].quantita+"</td>"+
                                                                                "<td>"+x.prestiti[i].tipo_ritiro+"</td>"+
                                                                                "<td>"+x.prestiti[i].tipo_consegna+"</td>"+
                                                                                //"<td class='item-b'><input type='button' class='item-button btn' value='Sono in negozio'></td>"+
                                                                            "</tr>";
                                                                        //if(x.prestiti[i].tipo_ritiro == "Negozio" || x.prestiti[i].tipo_consegna == "Negozio"){
                                                                        //    document.getElementById("tabellaMostra").innerHTML +=   "<td class='item-b'><input type='button' value='Sono in negozio'></td>";
                                                                                                                                                    
                                                                        //}
                                                                        //else{
                                                                        //    document.getElementById("tabellaMostra").innerHTML += "</tr>";
                                                                        //}



            }
            else{
                document.getElementById("tabellaMostra").innerHTML +=   "<tr>"+
                                                                                "<td>"+x.prestiti[i].titolo+"</td>"+
                                                                                "<td>"+x.prestiti[i].data_inizio+"</td>"+
                                                                                "<td>"+x.prestiti[i].data_fine+"</td>"+
                                                                                "<td>"+x.prestiti[i].quantita+"</td>"+
                                                                                "<td>"+x.prestiti[i].tipo_ritiro+"</td>"+
                                                                                "<td>"+x.prestiti[i].tipo_consegna+"</td>"+
                                                                                //"<td class='item-b'><input type='button'  class='item-button btn' value='Sono in negozio'></td>"+
                                                                        "</tr>";
                                                                        //if(x.prestiti[i].tipo_ritiro == "Negozio" || x.prestiti[i].tipo_consegna == "Negozio"){
                                                                        //    document.getElementById("tabellaMostra").innerHTML +=   "<td class='item-b'><input type='button' value='Sono in negozio'></td>";
                                                                                                                                                    
                                                                        //}
                                                                        //else{
                                                                        //    document.getElementById("tabellaMostra").innerHTML += "</tr>";
                                                                        //}


            }
        }
        document.getElementById("tabellaMostra").innerHTML +=   "</tbody>";
        document.getElementById("emptyDB").innerHTML =  " ";
    }
    else{
        document.getElementById("tabellaMostra").innerHTML =   "";
        document.getElementById("emptyDB").innerHTML =   "<h3>NON SONO STATI EFFETTUATI PRESTITI</h3>";
    }
  }
}

function visualizzaAcquisti(){
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseDatiVisualizzaAcquisti(this);
    };    
    xhttp.open("GET", "http://localhost:8080/libreria/ShowAcquistiUtente", true);
    xhttp.send();
}

function manageResponseDatiVisualizzaAcquisti(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x.acquisti.length > 0){
        for(var i in x.acquisti){
            if(i == 0){
                document.getElementById("tabellaMostra1").innerHTML =    "<thead>"+
                                                                            "<th>Titolo</th>"+
                                                                            "<th>Data Acquisto</th>"+
                                                                            "<th>Quantità</th>"+
                                                                            "<th>Tipo Ritiro</td>"+
                                                                        "</thead>"+    
                                                                        "<tbody>"+
                                                                            "<tr>"+
                                                                                "<td>"+x.acquisti[i].titolo+"</td>"+
                                                                                "<td>"+x.acquisti[i].data_inizio+"</td>"+
                                                                                "<td>"+x.acquisti[i].quantita+"</td>"+
                                                                                "<td>"+x.acquisti[i].tipo_ritiro+"</td>"+
                                                                                //"<td class='item-b'><input type='button'  class='item-button btn' value='Sono in negozio'></td>"+
                                                                            "</tr>";



            }
            else{
                document.getElementById("tabellaMostra1").innerHTML +=   "<tr>"+
                                                                                "<td>"+x.acquisti[i].titolo+"</td>"+
                                                                                "<td>"+x.acquisti[i].data_inizio+"</td>"+
                                                                                "<td>"+x.acquisti[i].quantita+"</td>"+
                                                                                "<td>"+x.acquisti[i].tipo_ritiro+"</td>"+
                                                                                //"<td class='item-b'><input type='button'  class='item-button btn' value='Sono in negozio'></td>"+
                                                                        "</tr>";


            }
        }
        document.getElementById("tabellaMostra1").innerHTML +=   "</tbody>";
        document.getElementById("emptyDB1").innerHTML =  " ";
    }
    else{
        document.getElementById("tabellaMostra1").innerHTML =   "";
        document.getElementById("emptyDB1").innerHTML =   "<h3>NON SONO STATI EFFETTUATI ACQUISTI</h3>";
    }
  }
}
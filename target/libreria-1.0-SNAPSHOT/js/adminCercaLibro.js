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

function inviaDatiRicerca(){
    let titolo = document.getElementById('titolo').value;
    let autore = document.getElementById('autore').value;
    let genere = document.getElementById('genere').value;

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
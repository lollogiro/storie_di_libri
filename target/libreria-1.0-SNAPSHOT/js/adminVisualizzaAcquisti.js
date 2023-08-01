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
    document.getElementById("submit").addEventListener('click', inviaDatiVisualizza);
});

function inviaDatiVisualizza(){
    var criteri = document.getElementsByName('criterio');
    var criterio;
    for(var i = 0; i < criteri.length; i++){
        if(criteri[i].checked){
            criterio = criteri[i].value;
        }
    }
    
    let jsonRequest;
    if(criterio == "email"){
        let email = document.getElementById('email').value;
        jsonRequest = {"email": email, "data": "null"};
    }
    else{
        let data = document.getElementById('data').value;
        jsonRequest = {"data": data, "email": "null"};
    }

    let stringRequest = JSON.stringify(jsonRequest);
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        manageResponseDatiVisualizza(this);
    };    
    xhttp.open("POST", "http://localhost:8080/libreria/ShowAcquisti", true);
    xhttp.send(stringRequest);
}

function manageResponseDatiVisualizza(e){
  if (e.status === 200 && e.readyState === 4) {
    let x = JSON.parse(e.responseText);
    if(x.acquisti.length > 0){
        for(var i in x.acquisti){
            if(i == 0){
                document.getElementById("tabellaMostra").innerHTML =    "<thead>"+
                                                                            "<th>Titolo</th>"+
                                                                            "<th>Utente</th>"+
                                                                            "<th>Data Acquisto</th>"+
                                                                            "<th>Quantità</th>"+
                                                                            "<th>Tipo Ritiro</td>"+
                                                                        "</thead>"+    
                                                                        "<tbody>"+
                                                                            "<tr>"+
                                                                                "<td>"+x.acquisti[i].titolo+"</td>"+
                                                                                "<td>"+x.acquisti[i].utente+"</td>"+
                                                                                "<td>"+x.acquisti[i].data_inizio+"</td>"+
                                                                                "<td>"+x.acquisti[i].quantita+"</td>"+
                                                                                "<td>"+x.acquisti[i].tipo_ritiro+"</td>"+
                                                                            "</tr>";



            }
            else{
                document.getElementById("tabellaMostra").innerHTML +=   "<tr>"+
                                                                                "<td>"+x.acquisti[i].titolo+"</td>"+
                                                                                "<td>"+x.acquisti[i].utente+"</td>"+
                                                                                "<td>"+x.acquisti[i].data_inizio+"</td>"+
                                                                                "<td>"+x.acquisti[i].quantita+"</td>"+
                                                                                "<td>"+x.acquisti[i].tipo_ritiro+"</td>"+
                                                                        "</tr>";


            }
        }
        document.getElementById("tabellaMostra").innerHTML +=   "</tbody>";
        document.getElementById("emptyDB").innerHTML =  " ";
    }
    else{
        document.getElementById("tabellaMostra").innerHTML =   "";
        document.getElementById("emptyDB").innerHTML =   "<h3>NON CI SONO ACQUISTI CHE CORRISPONDONO<br>A QUESTI CRITERI DI RiCERCA</h3>";
    }
  }
}



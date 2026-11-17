var object_name = document.getElementById('object_name');
var search_button = document.getElementById('search_button');

var results_table = document.getElementById('results_table');
var alt_row = false;

function getResults(e) {
    console.log(object_name.value);

    $.getJSON( "http://localhost:8080/helloJson", { name: "Archie", age: "68" } )
    .done(function( json ) {
        console.log( "JSON Data: " + json.name );
        console.log( "JSON Data: " + json.age );
        displayResults(json.name);
    })
    .fail(function( jqxhr, textStatus, error ) {
        var err = textStatus + ", " + error;
        console.log( "Request Failed: " + err );
    });
}

function displayResults(param) {
    var tr = document.createElement('tr');

    var td = document.createElement('td');
    var td_text = document.createTextNode(param);
    td.appendChild(td_text);
    tr.appendChild(td);

    var td2 = document.createElement('td');
    var td_text2 = document.createTextNode(param+2);
    td2.appendChild(td_text2);
    tr.appendChild(td2);

    if(alt_row) {
        tr.classList.add("alt_row");
    }
    alt_row = !alt_row;

    results_table.appendChild(tr);
}

search_button.addEventListener('click', function(e) {
    getResults(e);
}, false);
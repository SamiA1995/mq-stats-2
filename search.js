var object_name = document.getElementById('object_name');
var queue_manager_name = document.getElementById('queue_manager_name');
var search_button = document.getElementById('search_button');

var results_table = document.getElementById('results_table');
var alt_row = false;

function getResults(e) {
    $.getJSON( "http://localhost:8080/objectSearch", { object: object_name.value, queue_manager: queue_manager_name.value } )
    .done(function( json ) {
        clearTable();
        for(var i = 0; i < json.length; i++) {
            displayResults(json[i].object, json[i].queue_manager);
        }
    })
    .fail(function( jqxhr, textStatus, error ) {
        var err = textStatus + ", " + error;
        console.log( "Request Failed: " + err );
    });
}

function displayResults(object, queue_manager) {
    var tr = document.createElement('tr');

    var td = document.createElement('td');
    var td_text = document.createTextNode(object);
    td.appendChild(td_text);
    tr.appendChild(td);

    var td2 = document.createElement('td');
    var td_text2 = document.createTextNode(queue_manager);
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

function clearTable() {
    console.log(results_table.childElementCount);
    var count = results_table.childElementCount;
    for(var i = 0; i < count-1; i++) {
        var table_row = results_table.firstChild.nextSibling.nextSibling;
        results_table.removeChild(table_row);
    }
}
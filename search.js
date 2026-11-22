var object = document.getElementById('object');
var queue_manager = document.getElementById('queue_manager');
var host = document.getElementById('host');
var host_location = document.getElementById('host_location');
var platform = document.getElementById('platform');
var search_button = document.getElementById('search_button');

var results_table = document.getElementById('results_table');
var alt_row = false;

function getResults(e) {
    $.getJSON( "http://localhost:8080/objectSearch", { object: object.value, queue_manager: queue_manager.value, host: host.value, host_location: host_location.value, 
        platform: platform.value } )
    .done(function( json ) {
        clearTable();
        for(var i = 0; i < json.length; i++) {
            displayResults(json[i].object, json[i].queue_manager, json[i].host, json[i].host_location, json[i].platform);
        }
    })
    .fail(function( jqxhr, textStatus, error ) {
        var err = textStatus + ", " + error;
        console.log( "Request Failed: " + err );
    });
}

function displayResults(object, queue_manager, host, host_location, platform) {
    var tr = document.createElement('tr');

    var td = document.createElement('td');
    var td_text = document.createTextNode(object);
    td.appendChild(td_text);
    tr.appendChild(td);

    var td2 = document.createElement('td');
    var td_text2 = document.createTextNode(queue_manager);
    td2.appendChild(td_text2);
    tr.appendChild(td2);

    var td3 = document.createElement('td');
    var td_text3 = document.createTextNode(host);
    td3.appendChild(td_text3);
    tr.appendChild(td3);

    var td4 = document.createElement('td');
    var td_text4 = document.createTextNode(host_location);
    td4.appendChild(td_text4);
    tr.appendChild(td4);

    var td5 = document.createElement('td');
    var td_text5 = document.createTextNode(platform);
    td5.appendChild(td_text5);
    tr.appendChild(td5);

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
    var count = results_table.childElementCount;
    for(var i = 0; i < count-1; i++) {
        var table_row = results_table.firstChild.nextSibling.nextSibling;
        results_table.removeChild(table_row);
    }
}
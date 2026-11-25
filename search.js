var object = document.getElementById('object');
var queue_manager = document.getElementById('queue_manager');
var host = document.getElementById('host');
var host_location = document.getElementById('host_location');
var platform = document.getElementById('platform');
var environment = document.getElementById('environment');
var application = document.getElementById('application');
var app_id = document.getElementById('app_id');
var object_type = document.getElementById('object_type');
var search_button = document.getElementById('search_button');

var results_table = document.getElementById('results_table');
var alt_row = false;

function getResults(e) {
    $.getJSON( "http://localhost:8080/objectSearch", { object: object.value, queue_manager: queue_manager.value, host: host.value, 
        host_location: host_location.value, platform: platform.value, environment: environment.value, application: application.value,
        app_id: app_id.value, object_type: object_type.value } )
    .done(function( json ) {
        clearTable();
        for(var i = 0; i < json.length; i++) {
            displayResults(json[i].object, json[i].queue_manager, json[i].host, json[i].host_location, json[i].platform, json[i].environment,
            json[i].application, json[i].app_id, json[i].object_type
            );
        }
    })
    .fail(function( jqxhr, textStatus, error ) {
        var err = textStatus + ", " + error;
        console.log( "Request Failed: " + err );
    });
}

function displayResults(object, queue_manager, host, host_location, platform, environment, application, app_id, object_type) {
    var tr = document.createElement('tr');

    var td9 = document.createElement('td');
    var td_text9 = document.createTextNode(object_type);
    td9.appendChild(td_text9);
    tr.appendChild(td9);

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

    var td6 = document.createElement('td');
    var td_text6 = document.createTextNode(environment);
    td6.appendChild(td_text6);
    tr.appendChild(td6);

    var td7 = document.createElement('td');
    var td_text7 = document.createTextNode(application);
    td7.appendChild(td_text7);
    tr.appendChild(td7);

    var td8 = document.createElement('td');
    var td_text8 = document.createTextNode(app_id);
    td8.appendChild(td_text8);
    tr.appendChild(td8);

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
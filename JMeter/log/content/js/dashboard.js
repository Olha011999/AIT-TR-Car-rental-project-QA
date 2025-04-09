/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 27.272727272727273, "KoPercent": 72.72727272727273};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.13106060606060607, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.0, 500, 1500, "HomePage Request"], "isController": false}, {"data": [0.6583333333333333, 500, 1500, "HTTP Request all cars"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request Login"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request Cars"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request delete"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request customers"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request bookings"], "isController": false}, {"data": [0.7833333333333333, 500, 1500, "HTTP Request car brand for rent"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request Login User"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request update"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request Admin Authorization"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 660, 480, 72.72727272727273, 415.6000000000003, 0, 2759, 169.0, 1192.7999999999975, 2338.699999999998, 2729.7799999999997, 113.79310344827587, 157.1221713362069, 139.0490301724138], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["HomePage Request", 60, 0, 0.0, 2313.483333333334, 1563, 2759, 2373.5, 2730.8, 2746.35, 2759.0, 21.528525296017225, 29.160219321851454, 2.901305166846071], "isController": false}, {"data": ["HTTP Request all cars", 60, 0, 0.0, 660.2499999999999, 64, 1873, 602.5, 1083.6999999999998, 1495.649999999999, 1873.0, 22.62443438914027, 118.0270786199095, 131.70337245475113], "isController": false}, {"data": ["HTTP Request Login", 60, 60, 100.0, 203.7166666666666, 39, 1382, 170.0, 337.9, 427.7, 1382.0, 32.39740820734342, 31.511541576673864, 8.795390118790497], "isController": false}, {"data": ["HTTP Request Cars", 60, 60, 100.0, 168.1, 32, 875, 111.5, 352.9999999999999, 746.4499999999985, 875.0, 31.071983428275505, 27.73417270844122, 12.865743138270325], "isController": false}, {"data": ["HTTP Request delete", 60, 60, 100.0, 150.33333333333331, 74, 299, 147.0, 207.9, 292.44999999999993, 299.0, 22.463496817671285, 20.050425870460504, 5.769433264694872], "isController": false}, {"data": ["HTTP Request customers", 60, 60, 100.0, 0.0, 0, 0, 0.0, 0.0, 0.0, 0.0, 23.183925811437405, 27.553552453632147, 0.0], "isController": false}, {"data": ["HTTP Request bookings", 60, 60, 100.0, 159.13333333333335, 31, 486, 114.5, 272.49999999999994, 393.39999999999986, 486.0, 30.06012024048096, 26.74293900300601, 159.89987788076152], "isController": false}, {"data": ["HTTP Request car brand for rent", 60, 0, 0.0, 470.3333333333334, 93, 1036, 435.5, 706.4, 837.65, 1036.0, 20.696791997240428, 21.181873059675752, 8.367648327009313], "isController": false}, {"data": ["HTTP Request Login User", 60, 60, 100.0, 150.14999999999995, 65, 347, 122.5, 199.8, 281.89999999999975, 347.0, 20.442930153321974, 19.88394378194208, 5.549936115843271], "isController": false}, {"data": ["HTTP Request update", 60, 60, 100.0, 128.35, 87, 287, 110.0, 202.0, 209.74999999999997, 287.0, 22.288261515601782, 19.894014673105495, 5.963851225854383], "isController": false}, {"data": ["HTTP Request Admin Authorization", 60, 60, 100.0, 167.75, 35, 891, 105.5, 378.5999999999999, 777.65, 891.0, 32.15434083601286, 28.700261254019292, 9.012007636655948], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in authority at index 8: https:// car-rental-cymg8.ondigitalocean.app/api/customers", 60, 12.5, 9.090909090909092], "isController": false}, {"data": ["401/Unauthorized", 420, 87.5, 63.63636363636363], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 660, 480, "401/Unauthorized", 420, "Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in authority at index 8: https:// car-rental-cymg8.ondigitalocean.app/api/customers", 60, "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request Login", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request Cars", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request delete", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request customers", 60, 60, "Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in authority at index 8: https:// car-rental-cymg8.ondigitalocean.app/api/customers", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request bookings", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request Login User", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request update", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request Admin Authorization", 60, 60, "401/Unauthorized", 60, "", "", "", "", "", "", "", ""], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});

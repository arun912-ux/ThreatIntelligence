
const input = document.getElementById("input");
console.log(input);

const bodyy = document.querySelector("body");











const ip = document.getElementById("ip")
ip.addEventListener("click", () => {
    getData("ip")
});


// const url = document.getElementById("url")
// url.addEventListener("click", function(){
//     getData("url");
// });


const domain = document.getElementById("domain")
domain.addEventListener("click", function(){
    getData("domain");
});




function getData(obj){


    document.body.innerHTML = "<h1>Loading...</h1>"

    const resJson = httpGet("http://localhost:8080/rest/api/" + obj);
    const j = JSON.parse(resJson)
    let jsonPretty = JSON.stringify(j, null, 4)
    printJSON(jsonPretty)

    {
        $(function() {
            $("#table").dataTable({
                "iDisplayLength": 10,
                "aLengthMenu": [[10, 25, 50, 100,  -1], [10, 25, 50, 100, "All"]]
            });
        });
        // $(document).ready(function(){
        //     $('#table').after('<div id="nav"></div>');
        //     var rowsShown = 10;
        //     var rowsTotal = $('#table tbody tr').length;
        //     var numPages = rowsTotal/rowsShown;
        //     for(i = 0; i < numPages; i++) {
        //         var pageNum = i + 1;
        //         $('#nav').append('<a href="#" rel="'+i+'"> ' +pageNum+' </a> ');
        //     }
        //     $('#table tbody tr').hide();
        //     $('#table tbody tr').slice(0, rowsShown).show();
        //     $('#nav a:first').addClass('active');
        //     $('#nav a').bind('click', function(){

        //         $('#nav a').removeClass('active');
        //         $(this).addClass('active');
        //         var currPage = $(this).attr('rel');
        //         var startItem = currPage * rowsShown;
        //         var endItem = startItem + rowsShown;
        //         $('#table tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
        //         css('display','table-row').animate({opacity:1}, 300);
        //     });
        // });

        // const tableFilter = document.getElementById("table_filter");
        // const searchBarWithInDiv = tableFilter.getElementsByTagName("input");
        // searchBarWithInDiv.id = "search";
        // console.log(searchBarWithInDiv);
    }

};




let searchTag = null;
function addSearchBar(){

    const div2 = document.getElementById("div2");
    const searchBar = "<input id=\"search\" type=\"text\" placeholder=\"Search here\"" +
        "onkeyup='keyup()'" + ">";

    div2.innerHTML = searchBar;
    searchTag = document.getElementById("search");
}


function addTableFromData(data){




    const div1 = document.getElementById("div1");

    const title = data[0].Domain === undefined? "IP" : "Domain";
    let text = "<table border='1' id='table'>";
    text += "<thead><tr>" +
        "<th>" + title + "</th>"+
        "<th>Description</th>" +
        "<th>Produced Time</th>" +
        "<th>Indicator Type</th>" +
        "</tr></thead>";

    text+="<tbody id='tbody'>";

    for (let x in data){
        text += "<tr>";
        if(data[x].Domain === undefined){
            text += "<td style='font-weight:bolder'>" + data[x].IP + "</td>";
        }else{
            text += "<td style='font-weight:bolder'>" + data[x].Domain + "</td>";
        }
        text += "<td id='desc'>" + data[x].Description + "</td>";
        text += "<td id='time'>" + data[x].Produced_Time + "</td>";
        text += "<td id='type1'>" + data[x].Indicator_Type + "</td>";

        text += "</tr>";
    }
    text+="</tbody>";


    div1.innerHTML = text;
}


function divInner(obj){
    // console.table(obj)

    addSearchBar();

    data = JSON.parse(obj)
    console.log(data)
    const len = data.length;
    console.log(len);

    addTableFromData(data);

    // const title = data[0].Domain === undefined? "IP" : "Domain";
    // let text = "<table border='1' id='table'>";
    // text += "<thead><tr>" +
    //         "<th>" + title + "</th>"+
    //         "<th>Description</th>" +
    //         "<th>Produced Time</th>" +
    //         "<th>Indicator Type</th>" +
    //         "</tr></thead>";

    // for (let x in data){
    //     text += "<tr>";
    //     if(data[x].Domain === undefined){
    //         text += "<td style='font-weight:bolder'>" + data[x].IP + "</td>";
    //     }else{
    //         text += "<td style='font-weight:bolder'>" + data[x].Domain + "</td>";
    //     }
    //     text += "<td id='desc'>" + data[x].Description + "</td>";
    //     text += "<td id='time'>" + data[x].Produced_Time + "</td>";
    //     text += "<td id='type1'>" + data[x].Indicator_Type + "</td>";

    //     text += "</tr>";
    // }
    // text+="</text>";


    // div1.innerHTML = text;

}



function printJSON(obj){
    // console.log(obj)
    const toPrint = "<pre id='pre'><span>" +  obj +"</span></pre>";
    document.body.innerHTML = toPrint;
    const div1 = document.createElement("div");
    div1.id="div2";
    bodyy.appendChild(div1);
    const div2 = document.createElement("div");
    div2.id="div1";
    bodyy.appendChild(div2);
    divInner(obj);

};





function httpGet(theUrl) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;
};


function httpPost(theUrl, body) {
    const data = JSON.stringify(body);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            console.log(this.responseText);
        }
    });
    xhr.open("POST", theUrl, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(data);
    return xhr.responseText;

};


let form = document.getElementById('theForm');

form.addEventListener('submit', function(event){
    event.preventDefault();
    console.log("inside form listener");

    const type = document.getElementById('type').value;
    console.log(type)
    const text = document.getElementById('input').value;
    console.log(text)

    const body = {
        "type" : type.toString(),
        "input" : text.toString()
    };

    console.log(body)
    console.log(typeof body);
    const resp = httpPost("http://localhost:8080/rest/api/form/", body);
    console.log(resp)
    // printJSON(resp)
    const toPrint = "<pre id='pre' style='display:block;'><span>" +  resp +"</span></pre>";
    document.body.innerHTML = toPrint;

});












let i=0;
function keyup(){
    console.log("inside function  " + i++);
    // console.log(searchTag);

    var input, filter, table, tr, td, i;
    filter = searchTag.value.toUpperCase().trim();
    // console.log(filter);
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");

    // // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td");
        for(j=0 ; j<td.length; j++){
            let tdata = td[j];
            if (tdata) {
                if (tdata.innerHTML.toUpperCase().trim().indexOf(filter) > -1){
                    tr[i].style.display = "";
                    break ;
                } else{
                    tr[i].style.display = "none";
                }
            }
        }
    }
}























//
// const input = document.getElementById("input");
// console.log(input);
//
// const bodyy = document.querySelector("body");
//
//
//
//
//
//
//
//
//
// function getData(obj){
//     const resJson = httpGet("http://localhost:8080/rest/api/" + obj);
//     const j = JSON.parse(resJson)
//     let jsonPretty = JSON.stringify(j, null, 4)
//     printJSON(jsonPretty)
// };
//
//
// const ip = document.getElementById("ip")
// ip.addEventListener("click", () => {
//     getData("ip")
// });
//
//
// // const url = document.getElementById("url")
// // url.addEventListener("click", function(){
// //     getData("url");
// // });
//
//
// const domain = document.getElementById("domain")
// domain.addEventListener("click", function(){
//     getData("domain");
// });
//
//
//
//
//
// function divTable(){
//     let div1 = document.getElementById("div1");
//     div1.innerText="";
// }
//
//
// function printJSON(obj){
//     console.log(obj)
//     const toPrint = "<pre><span>" +  obj +"</span></pre>" + "<div id='div1'>h1</div>";
//     document.body.innerHTML = toPrint;
//     divTable();
// }
//
//
// function httpGet(theUrl) {
//     let xmlHttp = new XMLHttpRequest();
//     xmlHttp.open("GET", theUrl, false);
//     xmlHttp.send(null);
//     return xmlHttp.responseText;
// }
//
// function httpPost(theUrl, body) {
//     const data = JSON.stringify(body);
//     const xhr = new XMLHttpRequest();
//     xhr.addEventListener("readystatechange", function() {
//         if(this.readyState === 4) {
//             console.log(this.responseText);
//         }
//     });
//     xhr.open("POST", theUrl, false);
//     xhr.setRequestHeader("Content-Type", "application/json");
//     xhr.send(data);
//     return xhr.responseText;
//
// };
//
//
// let form = document.getElementById('theForm');
//
// form.addEventListener('submit', function(event){
//     event.preventDefault();
//     console.log("inside form listener");
//
//     const type = document.getElementById('type').value;
//     console.log(type)
//     const text = document.getElementById('input').value;
//     console.log(text)
//
//     const body = {
//         "type" : type.toString(),
//         "input" : text.toString()
//     };
//
//     console.log(body)
//     console.log(typeof body);
//     const resp = httpPost("http://localhost:8080/rest/api/form/", body);
//     console.log(resp)
//     printJSON(resp)
//
// });
//
//
//












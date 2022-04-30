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
//
//
//
//
//
//
//
//
//
//

const input = document.getElementById("input");
console.log(input);

const bodyy = document.querySelector("body");







const ip = document.getElementById("ip")
ip.addEventListener("click", () => {
    getData("ip")
});



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
}










async function divInner(obj){
    // console.table(obj)
    data = JSON.parse(obj)
    console.log(data)
    const div1 = document.getElementById("div1");
    const len = data.length;
    console.log(len);

    const title = data[0].Domain === undefined? "IP" : "Domain";
    let text = "<table border='1' id='table'>";
    text += "<thead><tr>" +
        "<th>" + title + "</th>"+
        "<th>Description</th>" +
        "<th>Produced Time</th>" +
        "<th>Indicator Type</th>" +
        "</tr></thead>";

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
    text+="</text>";


    div1.innerHTML = text;

}



async function printJSON(obj){
    // console.log(obj)
    const toPrint = "<pre id='pre'><span>" +  obj +"</span></pre> <div id='div1'>h1</div>";
    document.body.innerHTML = toPrint;
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











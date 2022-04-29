
const input = document.getElementById("input");
console.log(input);

const bodyy = document.querySelector("body");









function getData(obj){
    const resJson = httpGet("http://localhost:8080/rest/api/" + obj);
    const j = JSON.parse(resJson)
    let jsonPretty = JSON.stringify(j, null, 4)
    printJSON(jsonPretty)
};


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








function printJSON(obj){
    console.log(obj)
    document.body.innerHTML = "<pre><span>" +  obj +"</span></pre>";
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
    printJSON(resp)

});














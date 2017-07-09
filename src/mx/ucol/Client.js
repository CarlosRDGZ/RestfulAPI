function createCORSRequest(method, url) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        // Check if the XMLHttpRequest object has a "withCredentials" property.
        // "withCredentials" only exists on XMLHTTPRequest2 objects.
        xhr.open(method, url, true);
    } else {
        // Otherwise, CORS is not supported by the browser.
        xhr = null;
    }
    return xhr;
}

//HTTP GET method
function getMethod() {
   var url = 'http://localhost:3000/employees/';
   var xhr = createCORSRequest('GET', url);
   if (!xhr) {
       alert('CORS not supported');
       return;
   }
  // Response handlers.
  xhr.onload = function() {
    var text = xhr.responseText;
    //alert('Response from CORS request to ' + url + ': ' + "JavaServer");
	document.getElementById("text").innerHTML = text;
  };

  xhr.onerror = function() {
    alert('Woops, there was an error making the request.');
  };
  xhr.send();
}
/*
function postMethod () {
    
}
*/

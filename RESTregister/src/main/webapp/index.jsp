<html>
<body>
    <h2>REST API to register unicode Strings </h2>
    <p><a href="api/strings/id/2078">Sample Get Request</a>
    <p>
    	To register a Unicode string please POST a request using following details:
    	<br>URL: http://localhost:8080/RESTregister/api/strings<br>
    	Content-Type: application/json
    	<br>Sample PayLoad : {"unicode":"\u0048\u0065\u006C\u006C\u006F \u0050\u0061\u0079\u0070\u0061\u006c"}
    </p>
     <p>
    	<br>To get the strings matching the string ID,please submit GET request using following details:
    	<br>URL: http://localhost:8080/RESTregister/api/strings/id/{id}<br>
    	Content-Type: application/json
    </p>
</body>
</html>

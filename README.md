# web-server
A simple multi threaded web server (file based) that supports storage and retrieval of static files like html, json, xml, png, jpeg, jpg to name a few.
POST, PUT, DELETE,GET,HEAD methods are supported for all the above mime types. Server Supports HTTP 1.1

Takes the below properties from the web-server.properties file placed alongside the jar files
port -> port
threads -> maxNumberOfThreads
root  -> web root folder

## Available Methods ;

POST, PUT, GET, DELETE, OPTIONS, HEAD, CONNECT, TRACE

Currently supported are POST, PUT, GET, DELETE, HEAD.

## Http Response Status Codes:

OK(200, "OK"),CREATED(201, "Created"),NO_CONTENT(204,"deleted"),BAD_REQUEST(400, "Bad request"),
FORBIDDEN(403, "Forbidden"),NOT_FOUND(404, "Not found"),METHOD_NOT_SUPPORTED(405, "Not found"),
INTERNAL_SERVER_ERROR(500, "Internal server error"),HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");


##Steps to Build:

mvn clean package

Running this command will build a release version of the jar along with all the dependencies after executing the test cases.

##Steps to Run:

place the jar (the one witht he dependencies built in) and the server-config.properties in the same folder

cd in to the folder

execute java -jar <jar> 
example - java -jar java -Jar web-server-1.0-RELEASE.jar

You should see the below logs :
0 [main] INFO com.gans.server.Starter - Loading server properties
0 [main] INFO com.gans.server.Starter - Initializing server parameters
3 [main] INFO com.gans.server.Starter - starting server




you can

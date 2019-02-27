# web-server
A simple multi threaded web server (file based) that supports storage and retrieval of static files like html, json, xml, png, jpeg, jpg to name a few.
POST, PUT, DELETE,GET,HEAD methods are supported for all the above mime types. Server Supports HTTP 1.1

png, xml, json files are downloaded. jpg, html files are rendered in the browser chrome when tested.

Takes the below properties from the web-server.properties file placed alongside the jar files
port -> port
threads -> maxNumberOfThreads
root  -> web root folder



# Available Methods

POST, PUT, GET, DELETE, OPTIONS, HEAD, CONNECT, TRACE

Currently supported are POST, PUT, GET, DELETE, HEAD

## Http Response Status Codes

OK(200, "OK"),CREATED(201, "Created"),NO_CONTENT(204,"deleted"),BAD_REQUEST(400, "Bad request"),
FORBIDDEN(403, "Forbidden"),NOT_FOUND(404, "Not found"),METHOD_NOT_SUPPORTED(405, "Not found"),
INTERNAL_SERVER_ERROR(500, "Internal server error"),HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported")

## Locking:

File locking feature is implemented which will ensure the other thread will wait until the current thread is done with using the file and release it. The same is tested with Junits too. 

## Steps to Build

mvn clean package

Running this command will build a release version of the jar along with all the dependencies after executing the test cases.

## Steps to Run

#### place the jar (the one with the dependencies built in) and the server-config.properties in the same folder

or

#### Unzip the web-server.zip file to get all the required files.

cd in to the folder

execute java -jar <jar>
example - java -jar java -Jar web-server-1.0-RELEASE.jar

You should see the below logs :
0 [main] INFO com.gans.server.Starter - Loading server properties
0 [main] INFO com.gans.server.Starter - Initializing server parameters
3 [main] INFO com.gans.server.Starter - starting server

Example Static file operatrions through rest client.

## Steps to Test with Rest Client

Add file as a part of the post request
specify the content type
specify the filename in the url to save the image under the root folder with that name. e.g. localhost:8080/1.jpg



POST :

![image](https://user-images.githubusercontent.com/24922421/53456127-009cfc80-3a25-11e9-9832-c818938aea9a.png)

##**********************************************************************************************************************
##**********************************************************************************************************************

HEAD:

![image](https://user-images.githubusercontent.com/24922421/53456188-4e196980-3a25-11e9-91bd-1ff77776d1fe.png)

##**********************************************************************************************************************
##**********************************************************************************************************************

GET:

![image](https://user-images.githubusercontent.com/24922421/53456216-73a67300-3a25-11e9-919a-d4ca62669bec.png)

##**********************************************************************************************************************
##**********************************************************************************************************************

PUT:

![image](https://user-images.githubusercontent.com/24922421/53456261-9c2e6d00-3a25-11e9-9e78-97ae0f4fb812.png)

##**********************************************************************************************************************
##**********************************************************************************************************************

DELETE:

![image](https://user-images.githubusercontent.com/24922421/53456290-ba946880-3a25-11e9-845d-b0b4649cfb66.png)

##**********************************************************************************************************************
##**********************************************************************************************************************

GET:

![image](https://user-images.githubusercontent.com/24922421/53456318-ddbf1800-3a25-11e9-8d7d-1d8926b07c8f.png)

##**********************************************************************************************************************c
##**********************************************************************************************************************

GET at ROOT :

![image](https://user-images.githubusercontent.com/24922421/53456524-ae5cdb00-3a26-11e9-8cff-ce4a08777a8b.png)

##**********************************************************************************************************************
##**********************************************************************************************************************

## JMETER TESTING:

Tested the Server performance with JMeter 100 threads with a ramp of 2 seconds.
![image](https://user-images.githubusercontent.com/24922421/53457179-49ef4b00-3a29-11e9-91af-f36d17d36156.png)


## Reference:

Though I referred to the below repositories for idea, I coded the entire solution. Few Methods under the class HttpResponseWriter are copied from the source. authors name and comments are left intact in the code.

The Links referred are :

https://github.com/warchildmd/webserver (Went thorugh the source code to get an idea of the approach)
https://github.com/sorinslavic/file-based-web-server (used few methods with respect to response writing)
Opensource server - https://github.com/NanoHttpd/nanohttpd (went through the source to get an idea of the approach)


Though I have used a few methods it was not completely as is. I have made a lot of changes to the original code.
For example The author used String based approach to get all bytes and write. However, It wont work for binary files like images.
So improvised to read with byte array from the input stream. Also changed the logs and added validation checks wherever required.



## Improvements:

1) Would like to improve the logging. Build a framework to monitor the health of the Server
2) Implement options,trace, connect methods.
3) Improve the junit test coverage.

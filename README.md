# web-server
A simple web server (file based) that supports storage and retrieval of static files like html, json, xml, png, jpeg, jpg to name a few.
POST, PUT, DELETE,GET,HEAD methods are supported for all the above mime types. Server Supports HTTP 1.1


## Available Methods ;

POST, PUT, GET, DELETE, OPTIONS, HEAD, CONNECT, TRACE

Currently supported are POST, PUT, GET, DELETE, HEAD.

## Status Codes:

OK(200, "OK"),CREATED(201, "Created"),NO_CONTENT(204,"deleted"),BAD_REQUEST(400, "Bad request"),
FORBIDDEN(403, "Forbidden"),NOT_FOUND(404, "Not found"),METHOD_NOT_SUPPORTED(405, "Not found"),
INTERNAL_SERVER_ERROR(500, "Internal server error"),HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");

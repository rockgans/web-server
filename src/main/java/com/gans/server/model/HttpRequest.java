package com.gans.server.model;

import java.io.File;
import java.util.HashMap;

/**
 * Created by gmohan on 23/02/19.
 */
public class HttpRequest {

    /**
     * Request line containing protocol version, URI, and request method
     */
    private String requestLine;
    private String method;
    private String uri;
    private String version;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Request headers
     */
    private HashMap<String, String> headers;

    /**
     * Query parameters
     */
    private HashMap<String, String> params;

    /**
     * URL path part
     */
    private String path;

    /**
     * URL query part
     */
    private String query;

    /**
     * Flag for keep-alive requests
     */
    private boolean keepAlive = false;

    public HttpRequest() {
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    public String getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void headerAppend(String fieldName, String valueToAppend) {
        String prevValue = this.getHeaders().get(fieldName);
        this.getHeaders().put(fieldName, prevValue + " " + valueToAppend);
    }
}

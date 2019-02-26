package com.gans.server.utility;

import com.gans.server.exeptions.BadRequestException;
import com.gans.server.exeptions.ConnectionClosedException;
import com.gans.server.model.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Parses the incoming request from the inputstream. It identifies the uri, query parameters, body and the headers.
 * Created by gmohan on 23/02/19.
 */
public class HttpRequestParser {

    private static final int BUFFER_SIZE = 8096;
    private static Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);


    /**
     * Method that does the extraction of the data from the input stream. Uses a Datainputstream to read the data.
     * It can read both binary as well string based data. processes and handles request that includes, html,txt,
     * xml, json, jpeg, png files.
     * @param inputStream
     * @return
     * @throws BadRequestException
     * @throws ConnectionClosedException
     */
    public static HttpRequest parse(InputStream inputStream) throws BadRequestException, ConnectionClosedException {
        try {
            HttpRequest request = new HttpRequest();

            DataInputStream reader = new DataInputStream(inputStream);

            request.setRequestLine(reader.readLine());
            if (request.getRequestLine() == null) {
                throw new ConnectionClosedException();
            }

            String[] requestLineParts = request.getRequestLine().split(" ", 3);

            if (StringUtils.isEmpty(requestLineParts[0]) && StringUtils.isEmpty(requestLineParts[0]) && StringUtils.isEmpty(requestLineParts[0])) {
                throw new BadRequestException("Invalid Request Formation, method, uri and version not clearly formed");
            }
            logger.info("Incoming Request : {}",request.getRequestLine());
            request.setMethod(requestLineParts[0]);
            request.setUri(requestLineParts[1]);
            request.setVersion(requestLineParts[2]);

            String line = reader.readLine();
            while (!line.equals("")) {
                String[] lineParts = line.split(":", 2);
                if (lineParts.length == 2) {
                    request.getHeaders().put(lineParts[0].toLowerCase(), lineParts[1]);
                }
                line = reader.readLine();
            }

            // Query params
            String[] uriParts = request.getUri().split("\\?", 2);
            if (uriParts.length == 2) {
                request.setPath(uriParts[0]);
                request.setQuery(uriParts[1]);

                String[] keyValuePairs = request.getQuery().split("&");
                for (String keyValuePair : keyValuePairs) {
                    String[] keyValue = keyValuePair.split("=", 2);
                    if (keyValue.length == 2) {
                        request.getParams().put(keyValue[0].toLowerCase(), keyValue[1]);
                    }
                }
            } else {
                request.setPath(request.getUri());
                request.setQuery(StringUtils.EMPTY);
            }

            String contentLength = request.getHeaders().get("content-length");
            logger.debug("Content of size {}",contentLength);
            String connection = request.getHeaders().get("connection");
            int expectedContentLength = 0;
            if ("keep-alive".equals(connection.trim())) {
                if (StringUtils.isNotBlank(contentLength)) {
                    expectedContentLength = Integer.valueOf(contentLength.trim());
                }
            }

            if ("".equals(line) && expectedContentLength > 0) {

                byte[] buffer = new byte[BUFFER_SIZE];
                File tempFile = File.createTempFile(request.getUri(), ".tmp");
                OutputStream os = new FileOutputStream(tempFile);

                while (reader.available() > 0) {
                    os.write(buffer, 0, reader.read(buffer));
                    os.flush();
                }
                os.close();
                request.setFile(tempFile);
            }

            return request;
        } catch (ConnectionClosedException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Invalid Input Data");
        }
    }
}

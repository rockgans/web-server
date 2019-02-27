package com.gans.server.utility;

import com.gans.server.constants.HttpStatus;
import com.gans.server.webserver.WebServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * HTTP responses follow the following structure;</br></br>
 * <p>
 * STATUS LINE - HTTP Version StatusCode StatusReason </br>
 * HEADERS - Key: Value</br>
 * new line</br>
 * CONTENT</br>
 * <p>
 * The new line is expected to be CRLF
 *
 * @author Sorin.Slavic
 */
public class HttpResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseWriter.class);

    private final OutputStream outputStream;

    private static final String CRLF = "\r\n";

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    /**
     * Write the file to the response. We will infer the Content-Type header based
     * on the mime type of the file.
     * <p>
     * We will also include the Content-Length header representing the size of the file
     *
     * @param file
     * @throws java.io.IOException
     */
    public void writeFile(File file) throws IOException {
        // Though the base of this taken from the above author, I have made changes to the way the streams are handled.
        // the original implementation was based on chars and String, which wont help for binary files like images.
        // So I changed them to BufferedOutputSteam.
        logger.info("writing file {}", file.getName());
        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Length", file.length() + "");
        headers.put("Content-Type", new MimetypesFileTypeMap().getContentType(file));

        try (PrintWriter writer = writeHeaderResponse(HttpStatus.OK, headers)) {
            writer.flush();

            BufferedOutputStream outputStream = new BufferedOutputStream(getOutputStream());
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[8096];
            while (inputStream.available() > 0) {
                outputStream.write(buffer, 0, inputStream.read(buffer));
            }
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            logger.error("Error while writing File");
            if (logger.isDebugEnabled()) {
                logger.debug("error {}", e.getStackTrace());
            }
        }
    }

    /**
     * Method specifically used for writing response for Head method.
     * @param file
     * @throws IOException
     */
    public void writeforHeadMethod(HttpStatus status, File file) throws IOException {
        String statusLine = WebServer.HTTP_VERSION + " " + status.getCode() + " " + status.getReason();
        logger.info("Response: " + statusLine);

        PrintWriter writer = new PrintWriter(outputStream);

        // system specific \n or \r\n but always the HTTP standard \r\n
        writer.print(statusLine + CRLF);

        addHeaders(writer, new HashMap<String,String>());

        writer.print("Content-Length: " + file.length() + CRLF);
        writer.print("Content-Type: " + new MimetypesFileTypeMap().getContentType(file) + CRLF);
        writer.print(CRLF);

        writer.flush();
    }

    /**
     * Writes Headers
     *
     * @param status
     * @param headers
     * @return
     * @throws IOException
     */
    public PrintWriter writeHeaderResponse(HttpStatus status, Map<String, String> headers) {
        String statusLine = WebServer.HTTP_VERSION + " " + status.getCode() + " " + status.getReason();

        PrintWriter writer = new PrintWriter(outputStream);

        // system specific \n or \r\n but always the HTTP standard \r\n
        writer.print(statusLine + CRLF);

        addHeaders(writer, headers);

        writer.print(CRLF);
        return writer;
    }

    /**
     * Write "text" content response;
     * If the content is not empty we will include the header for Content-Length, or 0 if empty
     *
     * @param status
     * @param headers
     * @param content
     * @throws java.io.IOException
     */
    public void writeResponse(HttpStatus status, Map<String, String> headers, String content) {
        String statusLine = WebServer.HTTP_VERSION + " " + status.getCode() + " " + status.getReason();
        logger.info("Response: " + statusLine);

        PrintWriter writer = new PrintWriter(outputStream);

        // system specific \n or \r\n but always the HTTP standard \r\n
        writer.print(statusLine + CRLF);

        addHeaders(writer, headers);
        if(StringUtils.isNotEmpty(content))
        writer.print("Content-Length: " + content.length() + CRLF);
        writer.print(CRLF);
        if (StringUtils.isNotEmpty(content)) {
            addContent(writer, content);
        }
        writer.flush();

    }

    /**
     * Append the content after the HTTP specific empty line (CRLF)
     *
     * @param writer
     * @param content
     */
    protected void addContent(PrintWriter writer, String content) {
        writer.print(content);
    }

    /**
     * Apart from the specific content and method headers we always want to include
     * the name of the server we are running: SorinSServer and the
     * Date of the response;
     * <p>
     * The HTTP date pattern is: <day-name>, <day> <month> <year> <hour>:<minute>:<second> GMT
     *
     * @param writer
     * @param headers
     */
    protected void addHeaders(PrintWriter writer, Map<String, String> headers) {
        writer.print("Server:Simple-Server" + CRLF);
        writer.print("Date: " + getDateInHttp() + CRLF);

        headers.forEach((key, value) -> writer.print(key + ": " + value + CRLF));

        logger.debug("addHeaders - wrote " + (2 + headers.size()) + " headers");
    }

    /**
     * @return the current system date in GMT timezone - formatted per HTTP specific: <day-name>, <day> <month> <year> <hour>:<minute>:<second> GMT
     */
    private String getDateInHttp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        // cannot define SimpleDateFormat as class member since it is not thread safe

        return sdf.format(calendar.getTime());
    }
}

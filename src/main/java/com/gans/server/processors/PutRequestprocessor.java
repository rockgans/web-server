package com.gans.server.processors;

import com.gans.server.lock.FileLockManager;
import com.gans.server.model.HttpRequest;
import com.gans.server.constants.HttpStatus;
import com.gans.server.utility.HttpResponseWriter;
import com.gans.server.config.ServerConfig;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all the Put Request
 * Created by gmohan on 23/02/19.
 */
public class PutRequestprocessor implements RequestProcessorStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PostRequestprocessor.class);
    @Override
    public void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException {

        Path requestedFile = null;

        try {
            String path = httpRequest.getPath();
            String root = ServerConfig.getInstance().getWebRoot();
            requestedFile = Paths.get(root, path);
            if (requestedFile.normalize().startsWith(Paths.get(root).normalize())) {

                if (!Files.isDirectory(requestedFile)) {
                    if (Files.exists(requestedFile)) {
                        FileLockManager.tryLock(requestedFile.toString());
                        Files.write(requestedFile, FileUtils.readFileToByteArray(httpRequest.getFile()));
                    } else {
                        Files.write(requestedFile, FileUtils.readFileToByteArray(httpRequest.getFile()), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
                    }
                    httpRequest.getFile().deleteOnExit();
                }
            }
            writer.writeResponse(HttpStatus.CREATED, new HashMap<String,String>(), "PUT successful for file: " + requestedFile.getFileName());
        }catch (InterruptedException e) {
            logger.error("error while trying to access a file.");
        } finally {
            if(requestedFile != null)
            FileLockManager.releaseLock(requestedFile.toString());
        }
    }
}

package com.gans.server.processors;

import com.gans.server.config.ServerConfig;
import com.gans.server.constants.HttpStatus;
import com.gans.server.lock.FileLockManager;
import com.gans.server.model.HttpRequest;
import com.gans.server.utility.HttpResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Handle the delete requests to delete a file.
 * Created by gmohan on 23/02/19.
 */
public class DeleteRequestProcessor implements RequestProcessorStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DeleteRequestProcessor.class);

    @Override
    public void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException {

        Path requestedFile = null;
        try {
            String path = httpRequest.getPath();
            String root = ServerConfig.getInstance().getWebRoot();
            requestedFile = Paths.get(root, path);
            if (requestedFile.normalize().startsWith(Paths.get(root).normalize())) {
                if (Files.exists(requestedFile)) {
                    if (!Files.isDirectory(requestedFile)) {
                        FileLockManager.tryLock(requestedFile.toString());
                        File file = new File(Paths.get(root, path).toString());
                        file.delete();
                        logger.info("file at {} is deleted", requestedFile);
                        writer.writeResponse(HttpStatus.NO_CONTENT, new HashMap<>(), "File: " + path + " is deleted!");
                    } else {
                        writer.writeResponse(HttpStatus.FORBIDDEN, new HashMap<>(), "Folder Cannot be deleted!");
                    }
                }else{
                    writer.writeResponse(HttpStatus.NOT_FOUND, new HashMap<>(), "File Not Found");
                }
            }
        } catch (InterruptedException e) {
            logger.error("error while trying to access a file.");
        } finally {
            if (requestedFile != null)
                FileLockManager.releaseLock(requestedFile.toString());
        }
    }
}

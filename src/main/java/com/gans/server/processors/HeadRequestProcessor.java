package com.gans.server.processors;

import com.gans.server.config.ServerConfig;
import com.gans.server.lock.FileLockManager;
import com.gans.server.model.HttpRequest;
import com.gans.server.constants.HttpStatus;
import com.gans.server.utility.HttpResponseWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Handles all the head Request
 * Created by gmohan on 23/02/19.
 */
public class HeadRequestProcessor implements RequestProcessorStrategy {

    @Override
    public void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException {

        Path requestedFile = null;
        try {
            String path = httpRequest.getPath();
            String root = ServerConfig.getInstance().getWebRoot();
            requestedFile = Paths.get(root, path);

            //TODO Templates be moved to a singleton
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
            Template template = cfg.getTemplate("directory.ftl");

            if (path != null && requestedFile.normalize().startsWith(Paths.get(root).normalize())) {
                if (Files.exists(requestedFile)) {
                    if (!Files.isDirectory(requestedFile)) {
                        //acquire lock on file
                        FileLockManager.tryLock(requestedFile.toString());
                        File file = new File(Paths.get(root, path).toString());
                        writer.writeforHeadMethod(HttpStatus.OK,file );
                    }
                }else{
                    writer.writeResponse(HttpStatus.NOT_FOUND, new HashMap<>(), "File Not Found");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //release lock
            if (requestedFile != null)
                FileLockManager.releaseLock(requestedFile.toString());
        }
    }
}

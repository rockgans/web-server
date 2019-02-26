package com.gans.server.processors;

import com.gans.server.model.HttpRequest;
import com.gans.server.constants.HttpStatus;
import com.gans.server.utility.HttpResponseWriter;
import com.gans.server.config.ServerConfig;
import com.gans.server.lock.FileLockManager;
import com.gans.server.model.DirectoryListing;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Handles the Get Request, Supplies the list of files in a folder or a file
 * Created by gmohan on 23/02/19.
 */
public class GetRequestprocessor implements RequestProcessorStrategy {
    private static final Logger logger = LoggerFactory.getLogger(GetRequestprocessor.class);
    @Override
    public void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException {


        Path requestedFile = null;
        try{
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
                        writer.writeFile(file);
                    }else{
                        File[] files = requestedFile.toFile().listFiles();
                        if(files != null) {
                            String currPath = requestedFile.toString()
                                    .replace(ServerConfig.getInstance().getWebRoot(), "");
                            DirectoryListing directoryListing = new DirectoryListing(
                                    currPath + "/", Arrays.asList(files));
                            StringWriter stringWriter = new StringWriter();

                            template.process(directoryListing, stringWriter);

                            writer.writeResponse(HttpStatus.OK, new HashMap<>(), stringWriter.toString());
                            }
                    }
                }else{
                    writer.writeResponse(HttpStatus.NOT_FOUND, new HashMap<>(), "File Not Found");
                }
            }

        } catch (InterruptedException e) {
            logger.error("error while accessing file on lock");
        } catch (TemplateException e) {
            logger.error("error while rendering the templates for folder structure display");
        } finally{
            //release lock
            if(requestedFile != null)
            FileLockManager.releaseLock(requestedFile.toString());
        }
    }
}

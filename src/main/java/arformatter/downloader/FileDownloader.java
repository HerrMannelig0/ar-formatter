package arformatter.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@Component
public class FileDownloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloader.class);

    public void download(HttpServletResponse response, File file) throws IOException {
        if (!file.exists()) {
            LOGGER.error("File {} doesn't exist!", file.getAbsolutePath());
            return;
        }
        setResponseContent(response, file);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    private void setResponseContent(HttpServletResponse response, File file) {
        response.setContentType(findMimeType(file));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLength((int) file.length());
    }

    private String findMimeType(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }

}

package arformatter.downloader;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@Controller
public class DownloadController {

    @GetMapping("/file/{fileName:.+}")
    public void download(HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
        File file = new File("src/main/resources/" + fileName);
        if (!file.exists()) {
            return;
        }
        response.setContentType(findMimeType(file));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLength((int) file.length());

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    public static void download1(HttpServletResponse response, String fileName) throws IOException {
        File file = new File("src/main/resources/" + fileName);
        if (!file.exists()) {
            System.err.println("File " + file.getAbsolutePath() + " doesn't exist!");
            return;
        }
        response.setContentType(findMimeType(file));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLength((int) file.length());

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    private static String findMimeType(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }

}

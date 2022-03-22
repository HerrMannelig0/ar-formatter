package arformatter.controller;

import arformatter.downloader.DownloadController;
import arformatter.formatter.WordFormatter;
import arformatter.reader.WordReader;
import arformatter.writer.WordWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class StartController {

    private final WordReader wordReader;
    private final WordFormatter wordFormatter;
    private final WordWriter wordWriter;

    @Autowired
    public StartController(WordReader wordReader,
                           WordFormatter wordFormatter,
                           WordWriter wordWriter) {
        this.wordReader = wordReader;
        this.wordFormatter = wordFormatter;
        this.wordWriter = wordWriter;
    }

    @PostMapping("/")
    public ResponseEntity<List<String>> handleForm(HttpServletResponse response, @RequestParam("namesFile") MultipartFile file) throws IOException {
        List<String> lines = wordReader.read(file);
        List<String> formattedNames = wordFormatter.format(lines);
        wordWriter.write(formattedNames);
        DownloadController.download1(response, "abc.txt");
        return ResponseEntity.ok().body(formattedNames);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

}

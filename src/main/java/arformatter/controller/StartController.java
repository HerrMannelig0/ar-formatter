package arformatter.controller;

import arformatter.cleaner.FileCleaner;
import arformatter.downloader.FileDownloader;
import arformatter.formatter.WordFormatter;
import arformatter.reader.WordReader;
import arformatter.writer.WordWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class StartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartController.class);

    private final WordReader wordReader;
    private final WordFormatter wordFormatter;
    private final WordWriter wordWriter;
    private final FileDownloader fileDownloader;
    private final FileCleaner fileCleaner;

    @Autowired
    public StartController(WordReader wordReader,
                           WordFormatter wordFormatter,
                           WordWriter wordWriter,
                           FileDownloader fileDownloader,
                           FileCleaner fileCleaner) {
        this.wordReader = wordReader;
        this.wordFormatter = wordFormatter;
        this.wordWriter = wordWriter;
        this.fileDownloader = fileDownloader;
        this.fileCleaner = fileCleaner;
    }

    @PostMapping("/")
    public ResponseEntity<String> handleForm(HttpServletResponse response, @RequestParam("namesFile") MultipartFile file) throws IOException {
        LOGGER.info("Formatting data from file {}", file.getOriginalFilename());
        var lines = wordReader.read(file);
        var formattedNames = wordFormatter.format(lines);
        var outputFile = new File("src/main/resources/" + "formatted_" + file.getOriginalFilename());
        wordWriter.write(formattedNames, outputFile);
        fileDownloader.download(response, outputFile);
        fileCleaner.clean(outputFile);
        return ResponseEntity.ok(formattedNames.toString());
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

}

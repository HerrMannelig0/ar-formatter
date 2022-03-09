package arformatter.controller;

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

import java.util.List;

@Controller
public class StartController {

    private final WordReader wordReader;
    private final WordFormatter wordFormatter;
    private final WordWriter wordWriter;

    public StartController(@Autowired WordReader wordReader,
                           @Autowired WordFormatter wordFormatter,
                           @Autowired WordWriter wordWriter) {
        this.wordReader = wordReader;
        this.wordFormatter = wordFormatter;
        this.wordWriter = wordWriter;
    }

    @PostMapping("/")
    public ResponseEntity<List<String>> handleForm(@RequestParam("namesFile") MultipartFile file,
                                                   @RequestParam("directoryName") String directory) {
        List<String> lines = wordReader.read(file);
        List<String> formattedNames = wordFormatter.format(lines);
        wordWriter.write(formattedNames, directory);
        return ResponseEntity.ok().body(formattedNames);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

}

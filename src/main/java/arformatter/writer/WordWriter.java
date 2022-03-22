package arformatter.writer;

import arformatter.formatter.NameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WordWriter {

    private NameProvider nameProvider;

    @Autowired
    public WordWriter(NameProvider nameProvider) {
        this.nameProvider = nameProvider;
    }

    public void write(List<String> names) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/abc.txt"))) {
            names.forEach(name -> writeName(writer, name));
        } catch (IOException e) {
            throw new FileWritingException("Error during writing to file", e);
        }
    }

    private void writeName(BufferedWriter writer, String name) {
        try {
            writer.write(name + "\n");
        } catch (IOException e) {
            throw new FileWritingException("Error during writing name " + name, e);
        }
    }


}

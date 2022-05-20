package arformatter.writer;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class WordWriter {

    public void write(List<String> names, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
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

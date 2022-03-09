package arformatter.writer;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WordWriter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyMMdd_HHmmss");

    public void write(List<String> names, String directory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(createName(directory)))) {
            names.forEach(name -> writeName(writer, name));
        } catch (IOException e) {
            throw new FileWritingException("Error during writing to file", e);
        }
    }

    private String createName(String directory) {
        return defaultIfEmpty(directory) + "/format_" + getActualTimeFormatted() + ".txt";
    }

    private String defaultIfEmpty(String directory) {
        return directory == null || "".equals(directory) ?
                "saved" :
                directory;
    }

    private String getActualTimeFormatted() {
        return FORMATTER.format(LocalDateTime.now());
    }

    private void writeName(BufferedWriter writer, String name) {
        try {
            writer.write(name + "\n");
        } catch (IOException e) {
            throw new FileWritingException("Error during writing name " + name, e);
        }
    }


}

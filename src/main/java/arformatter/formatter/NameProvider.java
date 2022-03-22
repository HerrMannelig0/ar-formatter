package arformatter.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NameProvider {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyMMdd_HHmmss");

    public String createName() {
        return defaultIfEmpty("src/main/resources/") + "/format_" + getActualTimeFormatted() + ".txt";
    }

    private String defaultIfEmpty(String directory) {
        return directory == null || "".equals(directory) ?
                "saved" :
                directory;
    }

    private String getActualTimeFormatted() {
        return FORMATTER.format(LocalDateTime.now());
    }



}

package arformatter.reader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Component
public class WordReader {

    public List<String> read(MultipartFile file)  {
        List<String> lines = new LinkedList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new FileReadingException("Error during reading from file", e);
        }
        return lines;
    }

}

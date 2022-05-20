package arformatter.cleaner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCleaner.class);

    public void clean(File file) {
        if (file.delete()) {
            LOGGER.info("File {} deleted", file.getName());
        }
    }


}

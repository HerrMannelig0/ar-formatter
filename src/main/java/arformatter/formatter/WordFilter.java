package arformatter.formatter;

import org.springframework.stereotype.Component;

@Component
class WordFilter {

    private WordFilter() {
        // Utility class
    }

    static String filer(String name) {
        return name
                .replaceAll("Sz.p", "")
                .replaceAll("SZ.P", "")
                .replaceAll("sz.p.", "")
                .replaceAll("[\\d\\.]", "");
    }

}

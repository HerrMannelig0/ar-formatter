package arformatter.formatter;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WordFormatter {

    public List<String> format(List<String> lines) {
        return lines.stream()
                .map(WordFilter::filer)
                .map(this::formatName)
                .toList();
    }

    private String formatName(String name) {
        String lowerCaseName = name.toLowerCase();
        return Arrays.stream(lowerCaseName.split(" "))
                .map(this::capitalizeFirstLetter)
                .reduce("", (partialString, element) -> partialString + " "  + element)
                .trim();
    }

    private String capitalizeFirstLetter(String namePart) {
        List<String> notToCapitalize = List.of("", "i", "wraz", "z", "dziećmi", "osobą", "towarzyszącą");
        if (notToCapitalize.contains(namePart)) {
            return namePart;
        }
        String firstLetter = namePart.substring(0, 1);
        String restOfNamePart = namePart.substring(1);

        return firstLetter.toUpperCase() + restOfNamePart;
    }
}

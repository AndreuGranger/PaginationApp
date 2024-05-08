import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private static final int MAX_LINE_LENGTH = 80;
    private static final int MAX_LINES_PER_PAGE = 25;

    public List<String> paginateText(File file) throws IOException {
        List<String> lines = readFileLines(file);
        List<String> paginatedLines = new ArrayList<>();

        for (String line : lines) {
            // Split the line into words
            String[] words = line.split("\\s+");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                // Check if the word fits in the current line (with a space)
                if (currentLine.length() + word.length() + 1 <= MAX_LINE_LENGTH) {
                    // Add word to current line with a space if it's not the first word
                    if (!currentLine.isEmpty()) {
                        currentLine.append(" ");
                    }
                    currentLine.append(word);
                } else {
                    // Add current line to paginated lines
                    paginatedLines.add(currentLine.toString());
                    // Start a new line with the current word
                    currentLine = new StringBuilder(word);
                }
            }
            // Add remaining part of the line to paginated lines
            if (!currentLine.isEmpty()) {
                paginatedLines.add(currentLine.toString());
            }
        }
        // Return the formatted pages
        return formatPages(paginatedLines);
    }

    private List<String> readFileLines(File file) throws IOException {
        // Reads all lines from the file
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private List<String> formatPages(List<String> lines) {
        // Formats the lines into pages with a maximum number of lines
        List<String> formattedPages = new ArrayList<>();
        StringBuilder currentPage = new StringBuilder();
        int lineNumber = 0;
        // Page number starts at 1
        int pageNumber = 1;

        // Add each line to the current page
        for (String line : lines) {
            currentPage.append(line).append("\n");
            lineNumber++;

            // Check if the current page is full
            if (lineNumber == MAX_LINES_PER_PAGE) {
                currentPage.append("\n");
                currentPage.append("--- Page ").append(pageNumber).append(" ---\n");
                currentPage.append("\n");
                formattedPages.add(currentPage.toString());
                currentPage = new StringBuilder();
                pageNumber++;
                lineNumber = 0;
            }
        }
        // Add the last page if it's not empty
        if (!currentPage.isEmpty()) {
            currentPage.append("--- Page ").append(pageNumber).append(" ---\n");
            formattedPages.add(currentPage.toString());
        }
        return formattedPages;
    }

    // Was not sure if the call for this method was needed in the Main class
    public void writeFormattedPagesToFile(List<String> formattedPages, String outputFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String page : formattedPages) {
                writer.write(page);
            }
        }
    }
}

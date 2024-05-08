import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Main frame
            JFrame frame = new JFrame("Pagination Application");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(500, 250));

            // Panel for storing components
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            frame.add(mainPanel);

            // Vertical spacing for presentation
            mainPanel.add(Box.createVerticalStrut(50));

            // Title label
            JLabel titleLabel = new JLabel("Pagination Application");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(titleLabel);

            // Vertical spacing for presentation
            mainPanel.add(Box.createVerticalStrut(40));

            // Button to open the file chooser
            JButton openButton = new JButton("Open File");
            openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            openButton.addActionListener(e -> {
                // File chooser to select a text file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("./DOCS"));
                fileChooser.setDialogTitle("Select a Text File");
                // Filter to show only text files to avoid errors
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files (*.txt)", "txt");
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Get the chosen file
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Pagination pagination = new Pagination();
                        // Paginate the text from the selected file
                        List<String> paginatedPages = pagination.paginateText(selectedFile);

                        // Create the outputs directory if it does not exist
                        createDirectoryIfNotExists("./DOCS/outputs");

                        // Save the paginated text to a new file in the DOCS/outputs folder
                        String outputFileName = "./DOCS/outputs/paginated_" + selectedFile.getName();
                        // I send it to this directory to avoid overwriting
                        pagination.writeFormattedPagesToFile(paginatedPages, outputFileName);

                        // Shows the user a message with the paginated file name
                        JOptionPane.showMessageDialog(frame, "Paginated document: " + outputFileName,
                                "File Saved", JOptionPane.INFORMATION_MESSAGE);

                        // Display the paginated text in a text area
                        displayPaginatedText(paginatedPages);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            mainPanel.add(openButton);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void displayPaginatedText(List<String> paginatedPages) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        // This for was required in order to avoid printing "[" and "]" in the text area
        for (String page : paginatedPages) {
            textArea.append(page);
        }

        // Scroll pane to allow navigation through the text
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 600));

        JOptionPane.showMessageDialog(null, scrollPane, "Paginated Text", JOptionPane.PLAIN_MESSAGE);
    }

    // The parameter in this method is not necessary, but it gives usability to the program
    private static void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        // Check if the directory does not exist
        if (!directory.exists()) {
            // Attempt to create the directory including
            directory.mkdirs();

        }
    }
}

package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for generating reports in the application.
 */
public class GenerateReportsController {

    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private ScrollPane reportScrollPane;

    @FXML
    private VBox reportContainer;

    private final List<String> currentReportData = new ArrayList<>();
    private ReportStrategy reportStrategy; // Reference to the Strategy interface

    /**
     * Initializes the controller and populates the ComboBox with report types.
     */
    @FXML
    public void initialize() {
        reportTypeComboBox.setItems(FXCollections.observableArrayList(
                "Scholarships Overview",
                "Student Participation",
                "System Usage Statistics"
        ));
    }

    /**
     * Handles the Generate Report button click event.
     */
    @FXML
    private void generateReport() {
        reportContainer.getChildren().clear();
        currentReportData.clear();

        String selectedReport = reportTypeComboBox.getValue();

        if (selectedReport == null) {
            reportContainer.getChildren().add(new Label("Please select a report type."));
            return;
        }

        // Use Strategy Pattern to select the appropriate report generation strategy
        switch (selectedReport) {
            case "Scholarships Overview":
                reportStrategy = new ScholarshipsOverviewReportStrategy();
                break;
            case "Student Participation":
                reportStrategy = new StudentParticipationReportStrategy();
                break;
            case "System Usage Statistics":
                reportStrategy = new SystemUsageStatisticsReportStrategy();
                break;
            default:
                reportContainer.getChildren().add(new Label("Unknown report type selected."));
                return;
        }

        // Generate the report using the selected strategy
        reportStrategy.generateReport(currentReportData);
        displayReport();
    }

    /**
     * Displays the current report in the UI.
     */
    private void displayReport() {
        reportContainer.getChildren().clear();
        for (String line : currentReportData) {
            reportContainer.getChildren().add(new Label(line));
        }
    }

    /**
     * Handles the Download Report button click event.
     */
    @FXML
    private void downloadReport() {
        if (currentReportData.isEmpty()) {
            reportContainer.getChildren().add(new Label("No report to download. Please generate a report first."));
            return;
        }

        // Step 1: Prompt the user to select the file type
        List<String> choices = new ArrayList<>();
        choices.add("CSV");
        choices.add("TXT");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("CSV", choices);
        dialog.setTitle("Select File Type");
        dialog.setHeaderText("Choose the file format for downloading the report:");
        dialog.setContentText("File Type:");

        // Show the dialog and capture the user's choice
        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            // User canceled the dialog
            return;
        }

        String selectedType = result.get();

        // Step 2: Configure the FileChooser based on the selected file type
        FileChooser fileChooser = new FileChooser();
        if (selectedType.equals("CSV")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("report.csv");
        } else if (selectedType.equals("TXT")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            fileChooser.setInitialFileName("report.txt");
        } else {
            // Unknown file type selected
            reportContainer.getChildren().add(new Label("Unknown file type selected."));
            return;
        }

        fileChooser.setTitle("Save Report as " + selectedType);
        File file = fileChooser.showSaveDialog(reportScrollPane.getScene().getWindow());

        if (file != null) {
            try {
                if (selectedType.equals("CSV")) {
                    writeCSV(file);
                } else if (selectedType.equals("TXT")) {
                    writeTXT(file);
                }

                reportContainer.getChildren().add(new Label("Report successfully downloaded as " + selectedType + " to: " + file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
                reportContainer.getChildren().add(new Label("Failed to download the report as " + selectedType + ". Please try again."));
            }
        }
    }

    /**
     * Writes the current report data to a CSV file.
     *
     * @param file The file to write to.
     * @throws IOException If an I/O error occurs.
     */
    private void writeCSV(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : currentReportData) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

    /**
     * Writes the current report data to a TXT file.
     *
     * @param file The file to write to.
     * @throws IOException If an I/O error occurs.
     */
    private void writeTXT(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : currentReportData) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

    /**
     * Handles the Back to Dashboard button click event.
     */
    @FXML
    private void goBackToDashboard() {
        try {
            Stage currentStage = (Stage) reportScrollPane.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Admin Dashboard");
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
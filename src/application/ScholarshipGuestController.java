package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ScholarshipGuestController {

    @FXML
    private TextField searchBar;

    @FXML
    private GridPane scholarshipGrid;

    private List<Scholarship> scholarships;

    @FXML
    public void initialize() {
        try {
            // Fetch all scholarships from the database
            scholarships = DatabaseUtil.fetchAllScholarships();
            populateScholarshipGrid(scholarships);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String query = searchBar.getText().toLowerCase();
        List<Scholarship> filteredScholarships = scholarships.stream()
                .filter(scholarship -> scholarship.getName().toLowerCase().contains(query)
                        || scholarship.getDescription().toLowerCase().contains(query))
                .collect(Collectors.toList());
        populateScholarshipGrid(filteredScholarships);
    }

    private void populateScholarshipGrid(List<Scholarship> scholarshipsToDisplay) {
        scholarshipGrid.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Scholarship scholarship : scholarshipsToDisplay) {
            VBox scholarshipBox = createScholarshipBox(scholarship);
            scholarshipGrid.add(scholarshipBox, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private VBox createScholarshipBox(Scholarship scholarship) {
        VBox box = new VBox(5);
        box.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-radius: 5; -fx-background-radius: 5;");
        box.setPrefSize(200, 120);

        Text name = new Text(scholarship.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Text description = new Text(scholarship.getDescription());
        description.setWrappingWidth(180);

        javafx.scene.control.Button detailsButton = new javafx.scene.control.Button("View Details");
        detailsButton.setOnAction(event -> showScholarshipDetails(scholarship));

        // Exclude Apply button for guests
        box.getChildren().addAll(name, description, detailsButton);
        return box;
    }

    private void showScholarshipDetails(Scholarship scholarship) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Scholarship Details");

        // Main layout
        VBox detailsLayout = new VBox(15); // Increase spacing
        detailsLayout.setStyle("-fx-padding: 20; -fx-background-color: #FFFFFF; -fx-border-color: #007BFF; -fx-border-radius: 10; -fx-border-width: 2;");
        detailsLayout.setPrefSize(450, 500);

        // Header section
        Text header = new Text("Scholarship Details");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #007BFF; -fx-text-alignment: center;");

        // Scholarship name
        Text name = new Text("Name: " + scholarship.getName());
        name.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #2A2A2A;");

        // Description
        Text description = new Text("Description:\n" + scholarship.getDescription());
        description.setStyle("-fx-font-size: 14px; -fx-fill: #555555;");
        description.setWrappingWidth(400);

        // Eligibility
        Text eligibility = new Text("Eligibility:\n" + scholarship.getEligibilityCriteria());
        eligibility.setStyle("-fx-font-size: 14px; -fx-fill: #555555;");
        eligibility.setWrappingWidth(400);

        // Requirements
        Text requirements = new Text("Requirements:\n" + scholarship.getApplicationRequirements());
        requirements.setStyle("-fx-font-size: 14px; -fx-fill: #555555;");
        requirements.setWrappingWidth(400);

        // Other scholarship details
        Text lastDate = new Text("Last Date to Apply: " + scholarship.getLastDate());
        lastDate.setStyle("-fx-font-size: 14px; -fx-fill: #555555;");

        Text country = new Text("Country: " + scholarship.getCountry());
        country.setStyle("-fx-font-size: 14px; -fx-fill: #555555;");

        Text academicLevel = new Text("Level: " + scholarship.getAcademicLevel());
        academicLevel.setStyle("-fx-font-size: 14px; -fx-fill: #555555;");

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-radius: 5;");
        closeButton.setOnAction(event -> detailsStage.close());

        // Footer section for the button
        HBox footer = new HBox(closeButton);
        footer.setStyle("-fx-alignment: center; -fx-padding: 10;");

        // Add all elements to the layout
        detailsLayout.getChildren().addAll(
            header,
            name,
            description,
            eligibility,
            requirements,
            lastDate,
            country,
            academicLevel,
            footer
        );

        // Set the scene and show the stage
        detailsStage.setScene(new Scene(detailsLayout));
        detailsStage.show();
    }

    
    @FXML
    public void goBackToLogin() {
        try {
            // Close the current window
            Stage currentStage = (Stage) searchBar.getScene().getWindow();
            currentStage.close();

            // Load the Login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Show the Login stage
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

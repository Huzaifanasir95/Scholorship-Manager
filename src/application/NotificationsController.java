package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
public class NotificationsController implements Observer {

    @FXML
    private VBox newScholarshipsBox;

    @FXML
    private VBox applicationResponsesBox;

    @FXML
    public void initialize() {
        loadNotifications();
    }

    private void loadNotifications() {
        try {
            populateNewScholarships();
            populateApplicationResponses();
        } catch (Exception e) {
            showError("An error occurred while loading notifications.");
        }
    }

    private void populateNewScholarships() throws Exception {
        List<Notification> newScholarships = DatabaseUtil.getNewScholarshipNotifications();
        displayNotifications(newScholarships, newScholarshipsBox, "No new scholarships available.");
    }

    private void populateApplicationResponses() throws Exception {
        int userId = SessionManager.getLoggedInUserId();
        List<Notification> responses = DatabaseUtil.getApplicationResponseNotifications(userId);
        displayNotifications(responses, applicationResponsesBox, "No application responses at the moment.");
    }

    private void displayNotifications(List<Notification> notifications, VBox box, String noDataMessage) {
        box.getChildren().clear();  // Clear existing content
        if (notifications.isEmpty()) {
            showNoDataMessage(box, noDataMessage);
        } else {
            for (Notification notification : notifications) {
                Label label = createNotificationLabel(notification);
                box.getChildren().add(label);
            }
        }
    }

    private Label createNotificationLabel(Notification notification) {
        Label label = new Label("• " + notification.getMessage());
        label.setStyle("-fx-font-size: 14px;");
        return label;
    }

    private void showNoDataMessage(VBox box, String message) {
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: gray;");
        box.getChildren().add(label);
    }

    private void showError(String errorMessage) {
        // Show error message on console or UI
        System.err.println(errorMessage);
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            closeCurrentWindow();
            openDashboard();
        } catch (Exception e) {
            showError("Failed to navigate back to the dashboard.");
        }
    }

    private void closeCurrentWindow() {
        Stage currentStage = (Stage) newScholarshipsBox.getScene().getWindow();
        currentStage.close();
    }

    private void openDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();

        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard");
        dashboardStage.setScene(new Scene(root));
        dashboardStage.show();
    }

    @Override
    public void update() {
        // Whenever the NotificationService calls notifyObservers(), this method will be executed.
        // We reload the notifications from the NotificationService and update the UI.
        loadNotifications();
    }
}


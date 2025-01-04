package application;

import java.sql.SQLException;
import java.util.List;

public class StudentParticipationReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<String> currentReportData) {
        try {
            currentReportData.add("Student Participation Report");
            List<String> studentParticipation = DatabaseUtil.getStudentParticipation();

            for (String participation : studentParticipation) {
                currentReportData.add(participation);
            }
        } catch (SQLException e) {
            currentReportData.add("Failed to generate Student Participation report.");
            e.printStackTrace();
        }
    }
}

package application;

import java.sql.SQLException;
import java.util.List;

public class ScholarshipsOverviewReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<String> currentReportData) {
        try {
            currentReportData.add("Scholarships Overview Report");
            List<String> scholarships = DatabaseUtil.getScholarshipsOverview();

            for (String scholarship : scholarships) {
                currentReportData.add(scholarship);
            }
        } catch (SQLException e) {
            currentReportData.add("Failed to generate Scholarships Overview report.");
            e.printStackTrace();
        }
    }
}

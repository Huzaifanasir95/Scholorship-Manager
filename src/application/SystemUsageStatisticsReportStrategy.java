package application;

import java.sql.SQLException;
import java.util.List;

public class SystemUsageStatisticsReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<String> currentReportData) {
        try {
            currentReportData.add("System Usage Statistics Report");
            List<String> systemStats = DatabaseUtil.getSystemUsageStatistics();

            for (String stat : systemStats) {
                currentReportData.add(stat);
            }
        } catch (SQLException e) {
            currentReportData.add("Failed to generate System Usage Statistics report.");
            e.printStackTrace();
        }
    }
}

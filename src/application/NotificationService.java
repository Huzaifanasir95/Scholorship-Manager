package application;
import java.util.ArrayList;
import java.util.List;
public class NotificationService implements Subject {

    private List<Observer> observers;
    private List<Notification> newScholarships;
    private List<Notification> applicationResponses;

    public NotificationService() {
        observers = new ArrayList<>();
        newScholarships = new ArrayList<>();
        applicationResponses = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setNewNotifications(List<Notification> scholarships, List<Notification> responses) {
        this.newScholarships = scholarships;
        this.applicationResponses = responses;
        notifyObservers();  // Notify observers when new notifications are set
    }

    // Getter methods for the notifications
    public List<Notification> getNewScholarships() {
        return newScholarships;
    }

    public List<Notification> getApplicationResponses() {
        return applicationResponses;
    }
}
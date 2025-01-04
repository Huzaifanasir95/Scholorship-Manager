package application;

public class UserFacade {
    private UserService userService;

    public UserFacade() {
        this.userService = new UserService();  // Use the UserService internally
    }

    // Facade method to handle user login
    public UserDTO login(String email, String password) throws Exception {
        return userService.login(email, password);  // Simplified call to UserService
    }

    // Facade method to handle user signup
    public boolean signup(String username, String email, String password) throws Exception {
        return userService.signup(username, email, password);  // Simplified call to UserService
    }
}

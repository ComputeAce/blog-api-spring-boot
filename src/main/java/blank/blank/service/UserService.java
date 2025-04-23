package blank.blank.service;
import blank.blank.models.UserModel;

public interface UserService {
    UserModel registerUser(UserModel userModel);
    
    String login(String email, String password);
    
    
}

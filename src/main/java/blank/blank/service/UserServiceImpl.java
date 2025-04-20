package blank.blank.service;

import blank.blank.models.UserModel;
import blank.blank.rep.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRep userRep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserModel registerUser(UserModel user) {
        if (userRep.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRep.save(user);
    }
    
}

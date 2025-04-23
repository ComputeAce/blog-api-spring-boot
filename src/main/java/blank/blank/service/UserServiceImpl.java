package blank.blank.service;

import blank.blank.models.UserModel;
import blank.blank.rep.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRep userRep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public UserModel registerUser(UserModel user) {
        if (userRep.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRep.save(user);
    }

        @Override
        public String login(String username, String rawPassword) {
            UserModel user = userRep.getUserByUsername(username)
                    .orElse(null);

            if (user == null) {
                return null; 
            }

            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return jwtService.generateToken(username);
            } else {
                return "";
            }
        }


    }

    


package it.klotski.web.game.services;

import it.klotski.web.game.domain.User;
import it.klotski.web.game.exceptions.UserAlreadyPresentException;
import it.klotski.web.game.payload.requests.RegisterRequest;
import it.klotski.web.game.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                List.of());
    }

    public void createUser(RegisterRequest registerRequest) {
        if(userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyPresentException();
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);
    }

}

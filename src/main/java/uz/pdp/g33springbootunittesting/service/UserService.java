package uz.pdp.g33springbootunittesting.service;

import org.springframework.stereotype.Service;
import uz.pdp.g33springbootunittesting.domain.User;
import uz.pdp.g33springbootunittesting.dto.UserRegistrationDTO;
import uz.pdp.g33springbootunittesting.exception.UserAlreadyExistsException;
import uz.pdp.g33springbootunittesting.repo.UserRepository;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(final UserRegistrationDTO dto) {
        //false && false
        if (userRepository.findByEmail(dto.email()).isEmpty() && userRepository.findByUsername(dto.username()).isEmpty()) {
            return userRepository.save(new User(dto));
        }

        throw new UserAlreadyExistsException("User with email or username already exists");
    }
}

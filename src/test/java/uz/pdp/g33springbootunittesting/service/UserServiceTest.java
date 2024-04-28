package uz.pdp.g33springbootunittesting.service;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import uz.pdp.g33springbootunittesting.domain.User;
import uz.pdp.g33springbootunittesting.dto.UserRegistrationDTO;
import uz.pdp.g33springbootunittesting.exception.UserAlreadyExistsException;
import uz.pdp.g33springbootunittesting.repo.UserRepository;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testSaveUserThatThrowsExceptionWithExistenceEmail() {
        final var dto = new UserRegistrationDTO(
                "test@gmail.com",
                "test",
                "test"
        );

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        final var exception = assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(dto));
        assertEquals(exception.getMessage(),"User with email or username already exists");

//        verify(userRepository, times(1)).findByEmail(dto.email());
        verify(userRepository, only()).findByEmail(dto.email());
        verify(userRepository, never()).findByUsername(dto.username());
        verify(userRepository, never()).save(new User());
    }

    @Test
    void testSaveUserThatThrowsExceptionWithExistenceUsername() {}

    @Test
    void testSaveUserThatSuccessfullySavesUser() {
        final var dto = new UserRegistrationDTO(
                "test@gmail.com",
                "test",
                "test"
        );

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        assertDoesNotThrow(() -> userService.saveUser(dto));

        verify(userRepository, only()).findByEmail(anyString());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
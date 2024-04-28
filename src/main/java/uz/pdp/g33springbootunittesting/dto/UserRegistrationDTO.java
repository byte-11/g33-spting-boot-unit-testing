package uz.pdp.g33springbootunittesting.dto;

public record UserRegistrationDTO(
        String email,
        String username,
        String password
) {
}

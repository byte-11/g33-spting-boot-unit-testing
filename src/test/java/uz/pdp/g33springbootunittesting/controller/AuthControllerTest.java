package uz.pdp.g33springbootunittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uz.pdp.g33springbootunittesting.domain.User;
import uz.pdp.g33springbootunittesting.dto.UserRegistrationDTO;
import uz.pdp.g33springbootunittesting.service.UserService;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void register() throws Exception {
        final var dto = new UserRegistrationDTO("email@email.com", "alex", "1234");
        final var user = new User(1L, dto.username(), dto.email(), dto.password());

        when(userService.saveUser(dto)).thenReturn(user);

        MvcResult result = mockMvc.perform(
                        post("/register")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andReturn();
        final var json = result.getResponse().getContentAsString();
        System.out.println("json: " + json);
        User responseUser = objectMapper.readValue(json, User.class);

        assertEquals(dto.username(), responseUser.getUsername());
        assertEquals(dto.email(), responseUser.getEmail());
        assertEquals(dto.password(), responseUser.getPassword());

        verify(userService, times(1)).saveUser(dto);
    }
}
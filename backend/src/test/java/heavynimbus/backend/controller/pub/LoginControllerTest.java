package heavynimbus.backend.controller.pub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTests;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@Order(1)
@DisplayName("[PUBLIC] Authentication")
public class LoginControllerTest extends IntegrationTests {

  @Autowired
  public LoginControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    super(mockMvc, objectMapper);
  }

  @Test
  @Order(1)
  @DisplayName("POST /public/login - 200 - OK")
  public void should_successfully_authenticate() throws Exception {
    LoginRequest loginRequest = LoginRequest.builder().username("User").password("pass").build();
    MvcResult result =
        mockMvc
            .perform(
                post("/public/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jwtToken").exists())
            .andReturn();
    LoginResponse loginResponse =
        objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class);
    JWT_TOKEN = loginResponse.jwtToken();
  }

  @Test
  @Order(2)
  @DisplayName("POST /public/login - 403 - Wrong username")
  public void should_throw_403_on_authenticate_due_to_wrong_username() throws Exception {
    LoginRequest loginRequest =
        LoginRequest.builder().username("Wrong username").password("pass").build();

    mockMvc
        .perform(
            post("/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.status").value("FORBIDDEN"))
        .andExpect(jsonPath("$.message").value("Wrong username or password"));
  }

  @Test
  @Order(3)
  @DisplayName("POST /public/login - 403 - Wrong password")
  public void should_throw_403_on_authenticate_due_to_wrong_password() throws Exception {
    LoginRequest loginRequest =
        LoginRequest.builder().username("User").password("Wrong pass").build();

    mockMvc
        .perform(
            post("/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.status").value("FORBIDDEN"))
        .andExpect(jsonPath("$.message").value("Wrong username or password"));
  }

  @Test
  @Order(4)
  @DisplayName("POST /public/sign-up - 200 - OK")
  public void should_successfully_sign_up() throws Exception {
    LoginRequest loginRequest =
        LoginRequest.builder().username("HeavyNimbus").password("myPassword").build();

    mockMvc
        .perform(
            post("/public/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.jwtToken").exists());
  }

  @Test
  @Order(5)
  @DisplayName("POST /public/sign-up - 409 - Username already exists")
  public void should_throw_409_on_sign_up_due_to_already_exists_username() throws Exception {
    LoginRequest loginRequest =
        LoginRequest.builder().username("HeavyNimbus").password("myPassword").build();

    mockMvc
        .perform(
            post("/public/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.status").value("CONFLICT"))
        .andExpect(jsonPath("$.message").value("Account with username HeavyNimbus already exists"));
  }
}

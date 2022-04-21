package heavynimbus.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTest;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class UserControllersTest extends IntegrationTest {
  public static String JWT_TOKEN;

  @Autowired
  public UserControllersTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    super(mockMvc, objectMapper);
  }


  @Nested
  @Order(1)
  @DisplayName("[PUBLIC] Authentication")
  class LoginUserStep {
    @Test
    @DisplayName("User Authentication step")
    public void userAuthentication() throws Exception {
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
  }

  @Nested
  @Order(2)
  @DisplayName("[PUBLIC] Commands")
  class CommandControllerTest {
    @Test
    public void test() {
      log.info("jwt token: {}", JWT_TOKEN);
    }
  }
}

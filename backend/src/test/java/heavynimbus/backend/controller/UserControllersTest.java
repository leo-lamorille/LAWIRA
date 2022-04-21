package heavynimbus.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTest;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
public class UserControllersTest extends IntegrationTest {
  public static String JWT_TOKEN;
  public static CommandResponse ANY_COMMAND;

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

    @Nested
    @Order(1)
    @DisplayName("GET /user/commands")
    class GetAllCommandTest {
      @Test
      @Order(1)
      @DisplayName("/ - 401 - No token")
      public void should_throw_401_on_get_all_command_due_to_no_token() throws Exception {
        mockMvc
            .perform(get("/user/commands"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
            .andExpect(
                jsonPath("$.message").value("You are not authorized to access to this resource"))
            .andExpect(jsonPath("$.data.url").value("/user/commands"));
      }

      @Test
      @Order(2)
      @DisplayName("/ - 200 - OK")
      public void should_successfully_get_all_commands() throws Exception {
        MvcResult result =
            mockMvc
                .perform(get("/user/commands").header("Authorization", "Bearer " + JWT_TOKEN))
                .andExpect(jsonPath("$.size()").value(2))
                .andReturn();
        List<CommandResponse> commandResponses =
            objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {});
        ANY_COMMAND = commandResponses.stream().findAny().orElseThrow();
      }

      @Test
      @Order(3)
      @DisplayName("?status=CREATED - 200 - OK")
      public void should_successfully_get_all_created_commands() throws Exception {
        mockMvc
            .perform(
                get("/user/commands?status=CREATED").header("Authorization", "Bearer " + JWT_TOKEN))
            .andExpect(jsonPath("$.size()").value(1));
      }

      @Test
      @Order(4)
      @DisplayName("?status=PENDING - 200 - OK")
      public void should_successfully_get_all_pending_commands() throws Exception {
        mockMvc
            .perform(
                get("/user/commands?status=PENDING").header("Authorization", "Bearer " + JWT_TOKEN))
            .andExpect(jsonPath("$.size()").value(0));
      }

      @Test
      @Order(5)
      @DisplayName("?status=DONE - 200 - OK")
      public void should_successfully_get_all_done_commands() throws Exception {
        mockMvc
            .perform(
                get("/user/commands?status=DONE").header("Authorization", "Bearer " + JWT_TOKEN))
            .andExpect(jsonPath("$.size()").value(1));
      }
    }

    @Nested
    @Order(2)
    @DisplayName("GET /user/commands/{commandId}")
    class GetCommandById {
      @Test
      @Order(1)
      @DisplayName("401 - No token")
      public void should_throw_401_on_get_command_by_id_due_to_no_token() throws Exception {
        mockMvc
            .perform(get("/user/commands/{commandId}", ANY_COMMAND.getId()))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
            .andExpect(
                jsonPath("$.message").value("You are not authorized to access to this resource"))
            .andExpect(jsonPath("$.data.url").value("/user/commands/" + ANY_COMMAND.getId()));
      }

      @Test
      @Order(2)
      @DisplayName("404 - Command not found")
      public void should_throw_404_on_get_command_by_id_due_to_command_not_found()
          throws Exception {
        mockMvc
            .perform(
                get("/user/commands/{commandId}", "00000000-0000-0000-0000-000000000000")
                    .header("Authorization", "Bearer " + JWT_TOKEN))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
            .andExpect(
                jsonPath("$.message")
                    .value("command with [id = 00000000-0000-0000-0000-000000000000] not found"))
            .andExpect(jsonPath("$.data.entity").value("command"))
            .andExpect(jsonPath("$.data.parameter").value("id"))
            .andExpect(jsonPath("$.data.value").value("00000000-0000-0000-0000-000000000000"));
      }

      @Test
      @Order(2)
      @DisplayName("200 - OK")
      public void should_successfully_get_command_by() throws Exception {
        mockMvc
            .perform(
                get("/user/commands/{commandId}", ANY_COMMAND.getId())
                    .header("Authorization", "Bearer " + JWT_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ANY_COMMAND.getId().toString()))
            .andExpect(jsonPath("$.status").value(ANY_COMMAND.getStatus().toString()))
            .andExpect(jsonPath("$.quantity").value(ANY_COMMAND.getQuantity()))
            .andExpect(jsonPath("$.values.size()").value(ANY_COMMAND.getValues().size()));
      }
    }
  }
}

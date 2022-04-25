package heavynimbus.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTest;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import heavynimbus.backend.dto.product.AttributeResponse;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class PublicControllersTest extends IntegrationTest {
  public static AttributeResponse ANY_ATTRIBUTE;
  public static AttributeOptionResponse ANY_OPTION;
  public static String JWT_TOKEN;

  @Autowired
  public PublicControllersTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    super(mockMvc, objectMapper);
  }

  @Nested
  @Order(1)
  @DisplayName("[PUBLIC] Authentication")
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  class LoginControllerTest {

    @Test
    @Order(1)
    @DisplayName("POST /public/login - 200 - OK")
    public void should_successfully_authenticate() throws Exception {
      LoginRequest loginRequest = LoginRequest.builder().username("User").password("pass").build();
      mockMvc
          .perform(
              post("/public/login")
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(loginRequest)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.jwtToken").exists())
          .andReturn();
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
          .andExpect(
              jsonPath("$.message").value("Account with username HeavyNimbus already exists"));
    }
  }

  @Nested
  @Order(2)
  @DisplayName("[PUBLIC] Attributes")
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  class AttributeControllerTest {
    @Test
    @Order(1)
    @DisplayName("GET /public/attributes - 200 - OK")
    public void should_successfully_get_all_attributes() throws Exception {
      MvcResult result =
          mockMvc
              .perform(get("/public/attributes"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()").value(6))
              .andReturn();
      List<AttributeResponse> attributeResponses =
          objectMapper.readValue(
              result.getResponse().getContentAsString(), new TypeReference<>() {});
      ANY_ATTRIBUTE = attributeResponses.stream().findAny().orElseThrow();
      ANY_OPTION = ANY_ATTRIBUTE.getOptions().stream().findAny().orElseThrow();
    }

    @Test
    @Order(2)
    @DisplayName("GET /public/attributes/{attributeId} - 200 - OK")
    public void should_successfully_get_attribute_by_id() throws Exception {
      mockMvc
          .perform(get("/public/attributes/{attributeID}", ANY_ATTRIBUTE.getId()))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(ANY_ATTRIBUTE.getId().toString()))
          .andExpect(jsonPath("$.name").value(ANY_ATTRIBUTE.getName()))
          .andExpect(jsonPath("$.description").value(ANY_ATTRIBUTE.getDescription()))
          .andExpect(jsonPath("$.options.size()").value(ANY_ATTRIBUTE.getOptions().size()));
    }

    @Test
    @Order(3)
    @DisplayName("GET /public/attributes/{attributeId} - 404 - Attribute not found")
    public void should_throw_404_on_get_attribute_by_id_due_to_attribute_not_found()
        throws Exception {
      mockMvc
          .perform(get("/public/attributes/{attributeID}", "00000000-0000-0000-0000-000000000000"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.status").value("NOT_FOUND"))
          .andExpect(
              jsonPath("$.message")
                  .value("attribute with [id = 00000000-0000-0000-0000-000000000000] not found"))
          .andExpect(jsonPath("$.data.value").value("00000000-0000-0000-0000-000000000000"))
          .andExpect(jsonPath("$.data.entity").value("attribute"))
          .andExpect(jsonPath("$.data.parameter").value("id"));
    }
  }

  @Nested
  @Order(3)
  @DisplayName("[PUBLIC] Attribute options")
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  class AttributeOptionControllerTest {

    @Test
    @Order(1)
    @DisplayName("GET /public/attributes/{attributeId}/options - 200 - OK")
    public void should_successfully_get_all_options_by_attribute() throws Exception {
      mockMvc
          .perform(get("/public/attributes/{attributeId}/options", ANY_ATTRIBUTE.getId()))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()").value(ANY_ATTRIBUTE.getOptions().size()));
    }

    @Test
    @Order(2)
    @DisplayName("GET /public/attributes/{attributeId}/options - 404 - Attribute not found")
    public void should_throw_404_on_get_all_options_by_attribute_due_to_attribute_not_found()
        throws Exception {
      mockMvc
          .perform(
              get(
                  "/public/attributes/{attributeID}/options",
                  "00000000-0000-0000-0000-000000000000"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.status").value("NOT_FOUND"))
          .andExpect(
              jsonPath("$.message")
                  .value("attribute with [id = 00000000-0000-0000-0000-000000000000] not found"))
          .andExpect(jsonPath("$.data.value").value("00000000-0000-0000-0000-000000000000"))
          .andExpect(jsonPath("$.data.entity").value("attribute"))
          .andExpect(jsonPath("$.data.parameter").value("id"));
    }

    @Test
    @Order(3)
    @DisplayName("GET /public/attributes/{attributeId}/options/{optionId} - 200 - OK")
    public void should_successfully_get_option_by_id_and_attribute_id() throws Exception {
      System.out.println("ANY_ATTRIBUTE = " + ANY_ATTRIBUTE);
      System.out.println("ANY_OPTION = " + ANY_OPTION);
      mockMvc
          .perform(
              get(
                  "/public/attributes/{attributeId}/options/{optionId}",
                  ANY_ATTRIBUTE.getId(),
                  ANY_OPTION.getId()))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(ANY_OPTION.getId().toString()))
          .andExpect(jsonPath("$.value").value(ANY_OPTION.getValue()))
          .andExpect(jsonPath("$.type").value(ANY_OPTION.getType().toString()));
    }

    @Test
    @Order(4)
    @DisplayName(
        "GET /public/attribute/{attributeId}/options/{optionId} - 404 - Attribute not found")
    public void should_throw_404_on_get_option_by_id_and_attribute_id_due_to_attribute_not_found()
        throws Exception {
      mockMvc
          .perform(
              get(
                  "/public/attributes/{attributeId}/options/{optionId}",
                  "00000000-0000-0000-0000-000000000000",
                  ANY_OPTION.getId()))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.status").value("NOT_FOUND"))
          .andExpect(
              jsonPath("$.message")
                  .value("attribute with [id = 00000000-0000-0000-0000-000000000000] not found"))
          .andExpect(jsonPath("$.data.value").value("00000000-0000-0000-0000-000000000000"))
          .andExpect(jsonPath("$.data.entity").value("attribute"))
          .andExpect(jsonPath("$.data.parameter").value("id"));
    }

    @Test
    @Order(4)
    @DisplayName(
        "GET /public/attribute/{attributeId}/options/{optionId} - 404 - Attribute option not found")
    public void
        should_throw_404_on_get_option_by_id_and_attribute_id_due_to_attribute_option_not_found()
            throws Exception {
      mockMvc
          .perform(
              get(
                  "/public/attributes/{attributeId}/options/{optionId}",
                  ANY_ATTRIBUTE.getId(),
                  "00000000-0000-0000-0000-000000000000"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.status").value("NOT_FOUND"))
          .andExpect(
              jsonPath("$.message")
                  .value(
                      "Attribute option with [id = 00000000-0000-0000-0000-000000000000] not found"))
          .andExpect(jsonPath("$.data.value").value("00000000-0000-0000-0000-000000000000"))
          .andExpect(jsonPath("$.data.entity").value("Attribute option"))
          .andExpect(jsonPath("$.data.parameter").value("id"));
    }
  }
}

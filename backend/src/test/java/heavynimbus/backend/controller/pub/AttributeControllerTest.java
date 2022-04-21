package heavynimbus.backend.controller.pub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTests;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.dto.product.AttributeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@Order(2)
@DisplayName("[PUBLIC] Attributes")
public class AttributeControllerTest extends IntegrationTests {

  private static AttributeResponse ANY_ATTRIBUTE;
  private final AttributeRepository attributeRepository;

  @Autowired
  public AttributeControllerTest(
      MockMvc mockMvc, ObjectMapper objectMapper, AttributeRepository attributeRepository) {
    super(mockMvc, objectMapper);
    this.attributeRepository = attributeRepository;
  }

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
        objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
    ANY_ATTRIBUTE = attributeResponses.stream().findAny().orElseThrow();
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
        .andDo(print())
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

package heavynimbus.backend.controller.pub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTests;
import heavynimbus.backend.dto.product.AttributeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public class AttributeControllerTest extends IntegrationTests {

  @Autowired
  public AttributeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    super(mockMvc, objectMapper);
  }

  @Test
  public void should_successfully_get_all_attributes() throws Exception {
    mockMvc.perform(get("/public/attributes")).andExpect(status().isOk());
    /*    List<AttributeResponse> attributeResponses =
            objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {});
    */
    //  attributeResponses.forEach(System.out::println);
  }
}

package heavynimbus.backend.controller.pub;

import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.IntegrationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class AttributeOptionControllerTest extends IntegrationTests {

  @Autowired
  public AttributeOptionControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    super(mockMvc, objectMapper);
  }
}

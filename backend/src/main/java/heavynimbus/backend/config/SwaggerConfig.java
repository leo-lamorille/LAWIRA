package heavynimbus.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {
  private Info computeApiInfo() {
    Info apiInfo = new Info();
    apiInfo.setTitle("LAWIRA Backend");
    apiInfo.setVersion("v0.0.1");
    apiInfo.setDescription("This api documentation is destined to technical purposes for the LAWIRA project.");
    return apiInfo;
  }

  @Bean
  public OpenAPI openAPI() {
    var api = new OpenAPI();
    api.setInfo(computeApiInfo());
    return api;
  }

  @Bean
  public OpenApiCustomiser sortTagsAlphabetically() {
    return openApi ->
        openApi.setTags(
            openApi.getTags().stream()
                .sorted(Comparator.comparing(tag -> StringUtils.stripAccents(tag.getName())))
                .collect(Collectors.toList()));
  }
}

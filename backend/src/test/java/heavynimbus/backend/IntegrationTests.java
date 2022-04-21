package heavynimbus.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.io.File;

@Log4j2
@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class IntegrationTests {
  public static String JWT_TOKEN;
  protected final MockMvc mockMvc;
  protected final ObjectMapper objectMapper;

  @AfterAll
  public static void after() {
    File h2Database = new File("./test-db.mv.db");
    File h2Trace = new File("test-db.trace.db");
    boolean deleted = h2Database.delete();
    if (deleted) log.info("H2 database cleaned");
    else log.error("Cannot clean H2 database");
    deleted = h2Trace.delete();
    if (deleted) log.info("H2 trace cleaned");
    else log.error("Cannot clean H2 trace");
  }
}

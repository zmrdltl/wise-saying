package wise.saying;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WiseSayingControllerTest {
  private WiseSayingController controller;
  private WiseSayingService service;
  private WiseSayingRepository repository;
  private ByteArrayOutputStream output;

  @BeforeEach
  void setUp() {
    repository = new WiseSayingRepository();
    service = new WiseSayingService(repository);
    controller = new WiseSayingController(service);

    // System.out 출력 캡처
    output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
  }

  @Test
  void runShouldRegisterSayingWhenGivenRegisterCommand() {
    // Mock 사용자 입력
    String input = "등록\nTest content\nTest author\n종료\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    controller.run();

    // 출력 확인 (아무것도 출력되지 않아야 함)
    assertEquals("== 명언 앱 ==\n" + //
            "명령) 명언 : 작가 : 명령) 명령) 명령)", output.toString().trim());
  }

  @Test
  void runShouldNotThrowExceptionOnValidCommand() {
    // Mock 사용자 입력
    String input = "등록\nTest content\nTest author\n목록\n종료\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // 컨트롤러 실행
    controller.run();

    // 출력이 없더라도 예외가 발생하지 않아야 함
    assertTrue(true); // 예외 발생 여부 확인용
  }
}

package wise.saying;

import java.util.Scanner;

public class WiseSayingController {
  private final WiseSayingService service;

  public WiseSayingController(WiseSayingService service) {
    this.service = service;
  }

  public void run() {
    System.out.println("== 명언 앱 ==");
    try (Scanner scanner = new Scanner(System.in)) {
    while (true) {
      System.out.print("명령) ");
      String operation = scanner.next();
      if (operation.equals("등록")) {
        System.out.print("명언 : ");
        String words = scanner.next();
        System.out.print("작가 : ");
        String writer = scanner.next();
        service.registerWiseSaying(words, writer);
      } else if (operation.equals("목록")) {
        service.printWiseSaying();
      } else if (operation.startsWith("삭제")) {
          Integer eqIndex = operation.indexOf("=");
          Integer id = Integer.valueOf(operation.substring(eqIndex + 1));
          if (service.wiseWordExists(id)) {
            service.deleteWiseSaying(id);
          }
      } else if (operation.startsWith("수정")) {
        Integer eqIndex = operation.indexOf("=");
        Integer id = Integer.valueOf(operation.substring(eqIndex + 1));
        if (service.wiseWordExists(id)) {
          System.out.print("명언 : ");
          String words = scanner.next();
          System.out.print("작가 : ");
          String writer = scanner.next();
          service.updateWiseSaying(id, words, writer);
        }
      } else if (operation.equals("빌드")) {
        service.buildWiseWordsIntoOneFile();
      }
      if (operation.equals("종료")) {
          service.close();
          break;
        }
      }
    } catch (Exception e) {
       System.out.println(e.getMessage());
    }
  }
}

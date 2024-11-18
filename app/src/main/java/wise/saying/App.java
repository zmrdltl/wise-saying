package wise.saying;

import java.util.Scanner;

public class App {
    private void start() {
        System.out.println("== 명언 앱 ==");;
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
        int sequence = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("명령) ");
            String operation = scanner.next();
            
            if (operation.equals("등록")) {
                System.out.print("명언 : ");
                String word = scanner.next();

                System.out.print("작가 : ");
                String writer = scanner.next();
                sequence++;
                System.out.println(sequence + "번 명언이 등록되었습니다.");
            }
            if (operation.equals("종료")) {
                break;
            }
        }
    }
}

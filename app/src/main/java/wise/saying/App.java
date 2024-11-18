package wise.saying;

import java.util.Scanner;

public class App {
    private void start() {
        System.out.println("== 명언 앱 ==");;
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("명령) ");
            String operation = scanner.next();
            
            if (operation.equals("등록")) {
                System.out.print("명언 : ");
                String word = scanner.next();

                System.out.print("작가 : ");
                String writer = scanner.next();
            }
            if (operation.equals("종료")) {
                break;
            }
        }
    }
}

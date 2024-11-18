/*
 * This source file was generated by the Gradle 'init' task
 */
package wise.saying;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class App {
    private void start() {
        System.out.println("== 명언 앱 ==");;
    }

    public class WiseWord {
        private String words;
        private String writer;

        public WiseWord(String words, String writer) {
            this.words = words;
            this.writer = writer;
        }

        public String getWords() {
            return words;
        }

        public String getWriter() {
            return writer;
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
        int sequence = 0;
        HashMap<String, WiseWord> wiseWords = new HashMap<String, WiseWord>();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("명령) ");
                String operation = scanner.next();
                if (operation.equals("등록")) {
                    System.out.print("명언 : ");
                    String words = scanner.next();
                    System.out.print("작가 : ");
                    String writer = scanner.next();
                    sequence++;
                    wiseWords.put(String.valueOf(sequence), app.new WiseWord(words, writer));
                    System.out.println(sequence + "번 명언이 등록되었습니다.");
                } else if (operation.equals("목록")) {
                    for (Entry <String, WiseWord> entry : wiseWords.entrySet()) {
                        String number = entry.getKey();
                        WiseWord wiseWord = entry.getValue();
                        System.out.println(number + " / " + wiseWord.getWriter() + " / " + wiseWord.getWords());
                    }
                }
                
                if (operation.equals("종료")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

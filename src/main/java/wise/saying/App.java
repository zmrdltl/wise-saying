package wise.saying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App {
    private static final String BASE_DIR = "db/wiseSaying/";
    private static final String LAST_ID_FILE = BASE_DIR + "lastId.txt";

    private void buildWiseWordsIntoOneFile(HashMap<String, WiseWord> wiseWords) {
        try (Writer writer = new FileWriter(BASE_DIR + "data.json")) {
            List<WiseWord> wiseWordList = new ArrayList<>(wiseWords.values());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(wiseWordList, writer);
        } catch (Exception e) {
            System.out.println("data.json 파일 저장 오류: " + e.getMessage());
        }
    }
    
    private void saveWiseWord(WiseWord wiseWord) {
        int id = wiseWord.getId();
        try (Writer writer = new FileWriter(BASE_DIR + id + ".json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(wiseWord, writer);
        } catch (Exception e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
    }

    private void saveLastId(int id) {
        try (FileWriter writer = new FileWriter(LAST_ID_FILE)) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            System.out.println("마지막 ID 저장 오류: " + e.getMessage());
        }
    }

    private int loadLastId() {
        File file = new File(LAST_ID_FILE);
        if (!file.exists()) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("마지막 ID 로딩 오류: " + e.getMessage());
            return 0;
        }
    }

    private HashMap<String, WiseWord> loadWiseWords() {
        HashMap<String, WiseWord> wiseWords = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
        File directory = new File(BASE_DIR);
        if (!directory.exists() || !directory.isDirectory()) {
          System.out.println("Directory does not exist: " + BASE_DIR);
          return wiseWords;
        }
    
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
          System.out.println("No JSON files found in directory: " + BASE_DIR);
          return wiseWords;
        }
    
        for (File file : files) {
          try (FileReader reader = new FileReader(file)) {
            String fileName = file.getName();
            if (fileName.equals("data.json")) {
              continue;
            }
            WiseWord wiseWord = gson.fromJson(reader, WiseWord.class);
            
            wiseWords.put(fileName.substring(0, fileName.length() - 5), wiseWord);
          } catch (Exception e) {
            System.out.println("Failed to load file: " + file.getName() + " -> " + e.getMessage());
          }
        }
    
        return wiseWords;
    }

    private void deleteWiseWordFile(String number) {
        File file = new File(BASE_DIR + number + ".json");
        if (file.exists() && file.delete()) {
          System.out.println(number + ".json 파일이 삭제되었습니다.");
        }
    }

    private void start() {
        System.out.println("== 명언 앱 ==");
        new File(BASE_DIR).mkdirs();
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
        int sequence = app.loadLastId();
        HashMap<String, WiseWord> wiseWords = app.loadWiseWords();

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
                    wiseWords.put(String.valueOf(sequence), new WiseWord(sequence, words, writer));
                    System.out.println(sequence + "번 명언이 등록되었습니다.");
                } else if (operation.equals("목록")) {
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");

                    for (Entry <String, WiseWord> entry : wiseWords.entrySet()) {
                        String number = entry.getKey();
                        WiseWord wiseWord = entry.getValue();
                        System.out.println(number + " / " + wiseWord.getAuthor() + " / " + wiseWord.getContent());
                    }
                } else if (operation.startsWith("삭제")) {
                    Integer eqIndex = operation.indexOf("=");
                    String number = operation.substring(eqIndex + 1);
                    if (wiseWords.get(number) == null) {
                        System.out.println(number + "번 명언은 존재하지 않습니다.");
                        continue;
                    }
                    wiseWords.remove(number);
                    app.deleteWiseWordFile(number);
                    System.out.println(number + "번 명언이 삭제되었습니다.");
                } else if (operation.startsWith("수정")) {
                    Integer eqIndex = operation.indexOf("=");
                    String number = operation.substring(eqIndex + 1);
                    if (wiseWords.get(number) == null) {
                        System.out.println(number + "번 명언은 존재하지 않습니다.");
                        continue;
                    }
                    WiseWord currentWiseWord = wiseWords.get(number);
                    System.out.println("명언(기존) : " + currentWiseWord.getContent());
                    System.out.print("명언 : ");
                    String newWords = scanner.next();
                    System.out.println("작가(기존) : " + currentWiseWord.getAuthor());
                    System.out.print("작가 : ");
                    String newWriter = scanner.next();
                    WiseWord updatedWiseWord = new WiseWord(Integer.valueOf(number), newWords, newWriter);
                    wiseWords.put(number, updatedWiseWord);
                } else if (operation.equals("빌드")) {
                    app.buildWiseWordsIntoOneFile(wiseWords);
                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                }
                
                if (operation.equals("종료")) {
                    for (Entry <String, WiseWord> entry : wiseWords.entrySet()) {
                        String number = entry.getKey();
                        WiseWord wiseWord = entry.getValue();
                        app.saveWiseWord(wiseWord);
                    }
                    app.saveLastId(sequence);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

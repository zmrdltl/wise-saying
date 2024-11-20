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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WiseSayingRepository {
  public WiseSayingRepository() {
    new File(Config.BASE_DIR).mkdirs();
  }
  
  public void buildWiseWordsIntoOneFile(HashMap<Integer, WiseSaying> wiseWords) {
    try (Writer writer = new FileWriter(Config.BASE_DIR + "data.json")) {
      List<WiseSaying> wiseWordList = new ArrayList<>(wiseWords.values());
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(wiseWordList, writer);
    } catch (Exception e) {
      System.out.println("data.json 파일 저장 오류: " + e.getMessage());
    }
  }
  
  public void saveWiseWords(HashMap<Integer, WiseSaying> wiseWords) {
    for (Entry<Integer, WiseSaying> entry : wiseWords.entrySet()) {
      WiseSaying wiseWord = entry.getValue();
      this.saveWiseWord(wiseWord);
    }
  }

  public void saveWiseWord(WiseSaying wiseWord) {
    Integer id = wiseWord.getId();
    try (Writer writer = new FileWriter(Config.BASE_DIR + id + ".json")) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(wiseWord, writer);
    } catch (Exception e) {
      System.out.println("파일 저장 오류: " + e.getMessage());
    }
  }

  public void saveLastId(int id) {
    try (FileWriter writer = new FileWriter(Config.LAST_ID_FILE)) {
      writer.write(String.valueOf(id));
    } catch (IOException e) {
      System.out.println("마지막 ID 저장 오류: " + e.getMessage());
    }
  }

  public int loadLastId() {
    File file = new File(Config.LAST_ID_FILE);
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

  public HashMap<Integer, WiseSaying> loadWiseWords() {
    HashMap<Integer, WiseSaying> wiseWords = new HashMap<>();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    File directory = new File(Config.BASE_DIR);
    if (!directory.exists() || !directory.isDirectory()) {
      System.out.println("해당 폴더가 존재하지 않습니다. : " + Config.BASE_DIR);
      return wiseWords;
    }

    File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
    if (files == null || files.length == 0) {
      System.out.println("폴더 내 파일이 없습니다. : " + Config.BASE_DIR);
      return wiseWords;
    }

    for (File file : files) {
      try (FileReader reader = new FileReader(file)) {
        String fileName = file.getName();
        if (fileName.equals("data.json")) {
          continue;
        }
        WiseSaying wiseWord = gson.fromJson(reader, WiseSaying.class);
        String fileNameExceptExtension = fileName.substring(0, fileName.indexOf("."));
        wiseWords.put(Integer.valueOf(fileNameExceptExtension), wiseWord);
      } catch (Exception e) {
        System.out.println("파일을 로드할 수 없습니다. : " + file.getName() + " -> " + e.getMessage());
      }
    }

    return wiseWords;
  }

  public void deleteWiseWordFile(Integer number) {
    File file = new File(Config.BASE_DIR + number + ".json");
    if (file.exists() && file.delete()) {
      System.out.println(number + ".json 파일이 삭제되었습니다.");
    }
  }
}

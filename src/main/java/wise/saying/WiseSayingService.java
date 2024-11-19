package wise.saying;

import java.util.HashMap;
import java.util.Map.Entry;
import wise.saying.WiseSaying;

public class WiseSayingService {
  private final WiseSayingRepository repository;
  private final HashMap<Integer, WiseSaying> wiseWords;
  private int sequence;

  public WiseSayingService(WiseSayingRepository repository) {
    this.repository = repository;
    this.wiseWords = repository.loadWiseWords();
    this.sequence = repository.loadLastId();
  }

  public void registerWiseSaying(String content, String author) {
    sequence++;
    WiseSaying wiseWord = new WiseSaying(sequence, content, author);
    wiseWords.put(sequence, wiseWord);
  }

  public HashMap<Integer, WiseSaying> getAllWiseSayings() {
    return wiseWords;
  }

  public void deleteWiseSaying(int id) {
    wiseWords.remove(id);
    repository.deleteWiseWordFile(id);
    System.out.println(id + "번 명언이 삭제되었습니다.");
  }

  public WiseSaying updateWiseSaying(int id, String content, String author) {
    if (!wiseWords.containsKey(id)) return null;

    WiseSaying updatedWiseWord = new WiseSaying(id, content, author);
    wiseWords.put(id, updatedWiseWord);
    return updatedWiseWord;
  }

  public void close() {
    repository.saveLastId(sequence);
    repository.saveWiseWords(wiseWords);
  }

  public void printWiseSaying() {
    System.out.println("번호 / 작가 / 명언");
    System.out.println("----------------------");
    for (Entry <Integer, WiseSaying> entry : wiseWords.entrySet()) {
      Integer number = entry.getKey();
      WiseSaying wiseWord = entry.getValue();
      System.out.println(number + " / " + wiseWord.getAuthor() + " / " + wiseWord.getContent());
    }
  }

  public boolean wiseWordExists(int id) {
    if (!wiseWords.containsKey(id)) {
      System.out.println(id + "번 명언은 존재하지 않습니다.");
      return false;
    }
    return true;
  }

  public void buildWiseWordsIntoOneFile() {
    repository.buildWiseWordsIntoOneFile(wiseWords);
    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
  }
}

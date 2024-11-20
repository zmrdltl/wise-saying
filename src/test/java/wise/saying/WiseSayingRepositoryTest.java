package wise.saying;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WiseSayingRepositoryTest {
  private WiseSayingRepository repository;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(WiseSayingRepository.class);
  }

  @Test
  void saveAndLoadLastIdShouldPersistId() {
    doNothing().when(repository).saveLastId(7);
    when(repository.loadLastId()).thenReturn(7);


    repository.saveLastId(7);
    int lastId = repository.loadLastId();
    assertEquals(7, lastId);

    verify(repository,times(1)).saveLastId(7);
    verify(repository,times(1)).loadLastId();
  }

  @Test
  void saveAndLoadWiseWordsShouldPersistData() {
    WiseSaying wiseSaying = new WiseSaying(1, "Test content", "Test author");
    HashMap<Integer, WiseSaying> wiseSayings = new HashMap<>();
    wiseSayings.put(1, wiseSaying);

    doNothing().when(repository).saveWiseWords(wiseSayings);
    when(repository.loadWiseWords()).thenReturn(wiseSayings);

    repository.saveWiseWords(wiseSayings);
    HashMap<Integer, WiseSaying> loadedWiseSayings = repository.loadWiseWords();
    assertEquals(1, loadedWiseSayings.size());
    assertEquals("Test content", loadedWiseSayings.get(1).getContent());
    assertEquals("Test author", loadedWiseSayings.get(1).getAuthor());

    verify(repository, times(1)).saveWiseWords(wiseSayings);
    verify(repository, times(1)).loadWiseWords();
  }
}

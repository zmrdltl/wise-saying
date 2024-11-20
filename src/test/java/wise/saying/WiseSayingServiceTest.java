package wise.saying;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class WiseSayingServiceTest {
    private WiseSayingService service;
    private WiseSayingRepository repository;

    @BeforeEach
    void setUp() {
      repository = Mockito.mock(WiseSayingRepository.class);

      service = new WiseSayingService(repository);

      when(repository.loadWiseWords()).thenReturn(new HashMap<>());
      when(repository.loadLastId()).thenReturn(0);

      doNothing().when(repository).saveWiseWords(any());
    }

    @Test
    void registerWiseSayingShouldAddNewSaying() {
      doNothing().when(repository).saveWiseWords(any());

      service.registerWiseSaying("Test content", "Test author");
      HashMap<Integer, WiseSaying> sayings = service.getAllWiseSayings();
      assertEquals(1, sayings.size());
      assertEquals("Test content", sayings.get(1).getContent());
      assertEquals("Test author", sayings.get(1).getAuthor());
    }

    @Test
    void deleteWiseSayingShouldRemoveSaying() {
      service.registerWiseSaying("Test content", "Test author");
      service.deleteWiseSaying(1);
      HashMap<Integer, WiseSaying> sayings = service.getAllWiseSayings();
      assertFalse(sayings.containsKey(1));
    }

    @Test
    void updateWiseSayingShouldModifyExistingSaying() {
      service.registerWiseSaying("Old content", "Old author");
      WiseSaying updated = service.updateWiseSaying(1, "New content", "New author");
      assertNotNull(updated);
      assertEquals("New content", updated.getContent());
      assertEquals("New author", updated.getAuthor());
    }

    @Test
    void wiseWordExistsShouldReturnTrueForExistingId() {
      service.registerWiseSaying("Test content", "Test author");
      assertTrue(service.wiseWordExists(1));
    }

    @Test
    void wiseWordExistsShouldReturnFalseForNonExistingId() {
      assertFalse(service.wiseWordExists(1));
    }
}

package wise.saying;

public class App {
  public static void main(String[] args) {
    WiseSayingRepository repository = new WiseSayingRepository();
    WiseSayingService service = new WiseSayingService(repository);
    WiseSayingController controller = new WiseSayingController(service);
    controller.run();
  }
}

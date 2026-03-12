import Configuration.TestBaseClass;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Selenide.open;

public class TestOpenWebsite extends TestBaseClass {



        @Test
        void openGoogle() {
            open("https://google.com");

        }
    }


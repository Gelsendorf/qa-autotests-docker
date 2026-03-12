package Configuration;


import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Main {

    public void SelectBrowser() {
        // Take browser in jenkins
        String browser = System.getenv("BROWSER");

        // Initialize ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        if (Configuration.headless) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--force-device-scale-factor=1");
            options.addArguments("--disable-dev-shm-usage");
        }

        // Set desired capabilities and merge with ChromeOptions
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        if (browser == null) {
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
        } else {
            switch (browser) {
                case "EDGE":
                    WebDriverManager.edgedriver().setup();
                    Configuration.browser = "edge";
                    break;
                case "FIREFOX":
                    WebDriverManager.firefoxdriver().setup();
                    Configuration.browser = "firefox";
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    Configuration.browser = "chrome";
            }
        }

        Configuration.browserCapabilities = capabilities;
    }

    // Set headless mode for the browser
    public static void SetHeadlessMode(String headlessState) {
        Configuration.headless = !"OFF".equalsIgnoreCase(headlessState);
        Configuration.browserSize = "1920x1080";
    }
}

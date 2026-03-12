package Configuration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.TextReportExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.selenide.videorecorder.junit5.VideoRecorderExtension;

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

@ExtendWith({TextReportExtension.class})
@ExtendWith(VideoRecorderExtension.class)

public class TestBaseClass {


    static Main main = new Main();

    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(false).savePageSource(true));
    }

    @BeforeAll
    public static void RunningSettings() {
        main.SelectBrowser();
        main.SetHeadlessMode("OFF");
        Configuration.timeout = 50000;
        Selenide.clearBrowserCookies();
        clearBrowserCache();

    }


    @AfterEach
    public void TearDown() {

        Selenide.closeWebDriver();
    }
}

package network;

import com.codeborne.selenide.Configuration;
import network.pages.SpeedtestPage;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Scanner;
import java.util.concurrent.*;

import static com.codeborne.selenide.Selenide.*;

public class MakeSpeedtestReports {

    private ScheduledExecutorService executor;
    private final SpeedtestPage speedtestPage = new SpeedtestPage();
    private final long PERIOD_IN_MINUTES = 10;


    public static void main(String[] args) {
        MakeSpeedtestReports reports = new MakeSpeedtestReports();
        reports.setConfigs();
        reports.makeSpeedtestReports();
    }

    public void makeSpeedtestReports() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting speed test reports...");
        System.out.println("Type 'stop' to end tests.");

        executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleWithFixedDelay(this::runSpeedTest, 0, PERIOD_IN_MINUTES, TimeUnit.MINUTES);

        waitForStopCommand(scanner);
    }

    private void runSpeedTest() {
        try {
            open("https://www.speedtest.net");
            speedtestPage.startSpeedtestAndPrintResults();
            speedtestPage.writeReportToFile();
        } catch (WebDriverException e) {
            System.err.println("WebDriver error during speed test: " + e.getMessage());
        } finally {
            closeWebDriverSafely();
        }
    }

    private void waitForStopCommand(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim().toLowerCase();
            if ("stop".equals(input)) {
                shutdownExecutor();
                closeWebDriver();
                System.out.println("Stopping the tests...");
                System.exit(0);
            }
        }
    }

    private void shutdownExecutor() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    private void closeWebDriverSafely() {
        try {
            closeWebDriver();
        } catch (Exception e) {
            System.err.println("Error closing WebDriver: " + e.getMessage());
        }
    }

    public void setConfigs() {
        Configuration.browser = "chrome";
        Configuration.headless = true;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.browserCapabilities = capabilities;
    }
}

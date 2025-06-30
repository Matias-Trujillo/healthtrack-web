package org.healthtrack.healthtrackweb;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless"); // Opcional: evita abrir ventana
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        String ciEnv = System.getenv("CI");

        if ("true".equals(ciEnv)) {
            // 👷 Modo GitHub Actions (usa Selenium Grid)
            driver = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), options);
        } else {
            // 🖥️ Modo local (usa ChromeDriver local)
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testActualizarPeso() throws InterruptedException {
        driver.get("http://localhost:8080/reset");
        Thread.sleep(300);

        driver.get("http://localhost:8080");

        WebElement pesoActual = driver.findElement(By.id("pesoActual"));
        System.out.println("Peso inicial mostrado: " + pesoActual.getText());
        assertTrue(pesoActual.getText().contains("70"), "El peso inicial debería ser 70 kg");

        WebElement inputPeso = driver.findElement(By.id("pesoInput"));
        inputPeso.clear();
        inputPeso.sendKeys("74.3");

        WebElement botonActualizar = driver.findElement(By.id("actualizarPesoBtn"));
        botonActualizar.click();

        WebElement nuevoPeso = driver.findElement(By.id("pesoActual"));
        System.out.println("Peso actualizado mostrado: " + nuevoPeso.getText());
        assertTrue(nuevoPeso.getText().contains("74.3"), "El peso debería actualizarse a 74.3 kg");

        WebElement mensaje = driver.findElement(By.xpath("//p[contains(text(),'Peso actualizado correctamente')]"));
        assertNotNull(mensaje);
    }
}

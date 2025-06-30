package org.healthtrack.healthtrackweb;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testActualizarPeso() throws InterruptedException {
        // 🔄 Reiniciar el peso
        driver.get("http://localhost:8080/reset");
        Thread.sleep(300); // Espera un poco para que el backend actualice

        // 🌐 Ir al formulario
        driver.get("http://localhost:8080");

        WebElement pesoActual = driver.findElement(By.id("pesoActual"));
        System.out.println("Peso inicial mostrado: " + pesoActual.getText()); // <--- Aquí

        assertTrue(pesoActual.getText().contains("70"), "El peso inicial debería ser 70 kg");

        WebElement inputPeso = driver.findElement(By.id("pesoInput"));
        inputPeso.clear();
        inputPeso.sendKeys("74.3");

        WebElement botonActualizar = driver.findElement(By.id("actualizarPesoBtn"));
        botonActualizar.click();
        Thread.sleep(1000); // Espera para que la página actualice

        WebElement nuevoPeso = driver.findElement(By.id("pesoActual"));
        System.out.println("Peso actualizado mostrado: " + nuevoPeso.getText()); // <--- Y aquí

        assertTrue(nuevoPeso.getText().contains("74.3"), "El peso debería actualizarse a 74.3 kg");

        WebElement mensaje = driver.findElement(By.xpath("//p[contains(text(),'Peso actualizado correctamente')]"));
        assertNotNull(mensaje);
    }
}

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumE2ETest {

    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeAll
    fun setup() {
        driver = ChromeDriver()
        wait = WebDriverWait(driver, Duration.ofSeconds(5))
    }

    @AfterAll
    fun teardown() {
        driver.quit()
    }

    @BeforeEach
    fun goToHome() {
        driver.get("http://localhost:3000/")
    }

    @Test
    fun testProductListDisplaysCorrectly() {
        val productList = wait.until {
            it!!.findElement(By.id("product-list"))
        }
        val items = productList.findElements(By.tagName("li"))

        assertTrue(items.isNotEmpty())
        assertTrue(items[0].text.contains("Zapatos"))
        assertTrue(items[1].text.contains("Camisa"))
        assertTrue(items[2].text.contains("Pantalones"))
    }


    @Test
    fun testAddToCartAndCheckCart() {
        val button = wait.until { it!!.findElement(By.tagName("button")) }
        button.click()

        val cartLink = driver.findElement(By.linkText("Przejdź do koszyka"))
        cartLink.click()

        val cartItems = wait.until {
            it!!.findElement(By.id("cart-items")).findElements(By.tagName("li"))
        }
        assertEquals(1, cartItems.size)
        // wyrpintuj zawartosc cartItems
        for (element in cartItems) {
            println(element.text)
        }
        assertTrue(cartItems[0].text.contains("Zapatos"))
    }

    @Test
    fun testClearCart() {
        driver.findElement(By.linkText("Przejdź do koszyka")).click()
        driver.findElement(By.tagName("button")).click()

        val emptyText = driver.findElement(By.tagName("p")).text
        assertEquals("Twój koszyk jest pusty.", emptyText)
    }

    @Test
    fun testProceedToPaymentPage() {
        driver.findElement(By.tagName("button")).click()
        driver.findElement(By.linkText("Przejdź do koszyka")).click()
        driver.findElement(By.linkText("Przejdź do płatności")).click()

        val header = driver.findElement(By.tagName("h2")).text
        assertEquals("Płatności", header)
    }

    @Test
    fun testPaymentProcessing() {
//        driver.findElement(By.tagName("button")).click()
        driver.findElement(By.linkText("Przejdź do koszyka")).click()
        driver.findElement(By.linkText("Przejdź do płatności")).click()
        driver.findElement(By.tagName("button")).click()

        val message = wait.until {
            it!!.findElement(By.tagName("p"))
        }
        assertTrue(message.text.contains("Total:"))
    }
}

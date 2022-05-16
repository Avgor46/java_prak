import MyWebApp.dao.Impl.*;
import MyWebApp.entity.clients;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelenuimTests {

    private final clientsDao dclients = new clientsDao();

    private final tripsDao dtrips = new tripsDao();

    private final stations_on_tripDao dstations_on_trip = new stations_on_tripDao();

    private ordersDao dorders = new ordersDao();

    WebDriverWait wait;
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/avgor46/Downloads/chromedriver");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        wait = new WebDriverWait(driver,10);
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void SignUpAndDeleteUser() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1440, 826));
        driver.findElement(By.linkText("Регистрация")).click();
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("test");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("aaa");
        driver.findElement(By.id("pnumber")).click();
        driver.findElement(By.id("pnumber")).sendKeys("8800");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("bb@aaa.com");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("login")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        WebElement ele = driver.findElement(By.id("Accept"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", ele);

        Assert.assertEquals(driver.getTitle(), "Success");
        Map<String, String> findmap = new HashMap();
        findmap.put("name", "test");
        List<clients> cl_test = dclients.search(findmap);
        Assert.assertNotEquals(0, cl_test.size());

        driver.findElement(By.linkText("Администрирование")).click();
        driver.findElement(By.linkText("Перейти")).click();

        List<WebElement> weblist = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(weblist.size(), 4);
        String table1 = driver.findElement(By.id("Table1")).getText();

        Assert.assertTrue(table1.contains("Vito Scaletto"));
        Assert.assertTrue(table1.contains("Max Payne"));
        Assert.assertTrue(table1.contains("test"));
        Assert.assertTrue(table1.contains("800"));
        Assert.assertTrue(table1.contains("8800553535"));
        Assert.assertTrue(table1.contains("900"));

        driver.findElement(By.id("clinfo3")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).sendKeys("1");
        driver.findElement(By.id("update")).click();

        findmap.put("name", "test1");
        cl_test = dclients.search(findmap);
        Assert.assertNotEquals(0, cl_test.size());

        driver.findElement(By.id("clinfo3")).click();
        driver.findElement(By.id("deleteclient")).click();

        cl_test = dclients.search(findmap);
        Assert.assertEquals(0, cl_test.size());

        driver.close();
    }

    @Test
    public void aaddNewOrderdelete() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1440, 826));
        driver.findElement(By.name("from")).click();
        driver.findElement(By.name("from")).sendKeys("Midgard");
        driver.findElement(By.name("to")).click();
        driver.findElement(By.name("to")).sendKeys("Asgard");
        driver.findElement(By.id("Search")).click();
        driver.findElement(By.id("buy1")).click();
        driver.findElement(By.id("accept")).click();

        Assert.assertEquals(driver.getTitle(), "Success");
        Assert.assertEquals(dorders.getAll().size(), 3);

        driver.findElement(By.linkText("Мои заказы")).click();
        driver.findElement(By.id("delete3")).click();

        Assert.assertEquals(driver.getTitle(), "Success");
        Assert.assertEquals(dorders.getAll().size(), 2);
        driver.close();

    }

    @Test
    public void addNewTripOrderAndDelete() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1440, 826));
        driver.findElement(By.linkText("Администрирование")).click();
        driver.findElement(By.id("addtrip")).click();
        driver.findElement(By.id("company")).click();
        driver.findElement(By.id("company")).sendKeys("Umbrella");
        driver.findElement(By.id("a_seats")).click();
        driver.findElement(By.id("a_seats")).sendKeys("2");
        driver.findElement(By.id("hstations")).click();
        driver.findElement(By.id("hstations")).sendKeys("Midgard,Asgard");
        driver.findElement(By.id("dates")).click();
        driver.findElement(By.id("dates")).sendKeys("2022-01-01 20:20,2022-01-01 21:20");
        driver.findElement(By.id("accept")).click();

        List<WebElement> weblist = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(weblist.size(), 3);
        Assert.assertEquals(dstations_on_trip.getAll().size(), 6);
        String table1 = driver.findElement(By.id("Table1")).getText();

        Assert.assertTrue(table1.contains("Umbrella"));
        Assert.assertTrue(table1.contains("2"));
        Assert.assertTrue(table1.contains("Midgard"));
        Assert.assertTrue(table1.contains("Asgard"));
        Assert.assertTrue(table1.contains("2022-01-01 20:20"));
        Assert.assertTrue(table1.contains("2022-01-01 21:20"));

        driver.findElement(By.linkText("Главная")).click();
        driver.findElement(By.name("from")).click();
        driver.findElement(By.name("from")).sendKeys("Midgard");
        driver.findElement(By.name("to")).click();
        driver.findElement(By.name("to")).sendKeys("Asgard");
        driver.findElement(By.id("Search")).click();

        weblist = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(weblist.size(), 3);
        table1 = driver.findElement(By.id("Table1")).getText();

        Assert.assertTrue(table1.contains("2"));
        Assert.assertTrue(table1.contains("Midgard"));
        Assert.assertTrue(table1.contains("Asgard"));
        Assert.assertTrue(table1.contains("2022-01-01 20:20"));

        driver.findElement(By.id("buy2")).click();
        driver.findElement(By.id("accept")).click();

        Assert.assertEquals(driver.getTitle(), "Success");
        Assert.assertEquals(dorders.getAll().size(), 3);

        driver.findElement(By.linkText("Мои заказы")).click();

        weblist = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(weblist.size(), 3);

        driver.findElement(By.id("delete3")).click();

        Assert.assertEquals(dorders.getAll().size(), 2);

        driver.findElement(By.linkText("Администрирование")).click();
        driver.findElement(By.id("trips")).click();

        weblist = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(weblist.size(), 3);

        driver.findElement(By.id("More2")).click();

        String element = driver.findElement(By.tagName("h1")).getText();
        Assert.assertTrue(element.contains("ID 2"));

        driver.findElement(By.id("DeleteTrip")).click();

        Assert.assertEquals(driver.getTitle(), "Success");

        Assert.assertEquals(dtrips.getAll().size(), 1);
        driver.close();
    }

    @Test
    public void miniErrorTest() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1440, 826));
        driver.findElement(By.name("from")).click();
        driver.findElement(By.name("from")).sendKeys("Midgard");
        driver.findElement(By.name("to")).click();
        driver.findElement(By.name("to")).click();
        driver.findElement(By.name("to")).sendKeys("Muspelheim");
        driver.findElement(By.id("Search")).click();

        Assert.assertEquals(driver.findElement(By.id("msg")).getText(), "Trips not found");
        driver.close();
    }
}
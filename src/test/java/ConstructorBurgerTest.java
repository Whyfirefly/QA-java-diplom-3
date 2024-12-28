import com.codeborne.selenide.Configuration;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pageobject.BasePage;

import java.io.IOException;

import static browser.Browser.initDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

@Epic("Burger constructor")
public class ConstructorBurgerTest {
  private BasePage basePage;

  @Before
  public void setUp() throws IOException {
    //initialization our Browser
    initDriver();
    Configuration.timeout = 4000;
    //create an instance of BasePage class
    basePage = open(BasePage.BASE_URL, BasePage.class);

  }

  @Test
  @DisplayName("Check if the bun ingredients 'Булки' current")
  public void checkIfBunIngredientIsCurrent() {
    boolean isDisplayed = basePage.checkBunIngredientIsCurrent();

    assertTrue(isDisplayed);
  }

  @Test
  @DisplayName("Check if the sauce ingredient 'Соусы' current")
  public void checkIfSauceIngredientIsCurrent() {
    boolean isDisplayed = basePage.checkSauceIngredientIsCurrent();

    assertTrue(isDisplayed);
  }

  @Test
  @DisplayName("Check if the filling ingredient 'Начинки' current")
  public void checkIfFillingIngredientIsCurrent() {
    boolean isDisplayed = basePage.checkFillingIngredientIsCurrent();

    assertTrue(isDisplayed);
  }
}

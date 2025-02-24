package pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.page;

public class BasePage {
  public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

  @FindBy(how = How.XPATH, using = ".//p[text()='Личный Кабинет']")
  private SelenideElement personalCabinetButton;
  @FindBy(how = How.XPATH, using = ".//button[text()='Войти в аккаунт']")
  private SelenideElement loginButton;
  @FindBy(how = How.CLASS_NAME, using = "BurgerIngredients_ingredients__list__2A-mT")
  private ElementsCollection menuIngredients;
  @FindBy(how = How.XPATH, using = ".//div[contains(@class,'tab_tab_type_current')]")
  private SelenideElement currentSectionOfIngredients;

  @Step("Click account button")
  public LoginPage clickPersonalCabinetButton() {
    personalCabinetButton.click();
    return page(LoginPage.class);
  }

  @Step("Click account button, go to personal cabinet page")
  public UserPersonalCabinetPage clickPersonalCabinetButtonGoAccountPage() {
    personalCabinetButton.click();
    return page(UserPersonalCabinetPage.class);
  }

  @Step("Click login button")
  public LoginPage clickLoginButton() {
    loginButton.click();
    return page(LoginPage.class);
  }

  @Step("Check the bun ingredients current")
  public boolean checkBunIngredientIsCurrent() {
    SelenideElement bun = menuIngredients.get(0).lastChild();
    bun.scrollIntoView(true);
    bun.click();
    return currentSectionOfIngredients.isDisplayed();
  }

  @Step("Check the sauce ingredients current")
  public boolean checkSauceIngredientIsCurrent() {
    SelenideElement sauce = menuIngredients.get(1).lastChild();
    sauce.scrollIntoView(true);
    sauce.click();
    return currentSectionOfIngredients.isDisplayed();
  }

  @Step("Check the filling ingredients current")
  public boolean checkFillingIngredientIsCurrent() {
    SelenideElement filling = menuIngredients.get(2).lastChild();
    filling.scrollIntoView(true);
    filling.click();
    return currentSectionOfIngredients.isDisplayed();
  }
}

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.BasePage;
import random_data.UserGeneratorData;

import java.io.IOException;

import static browser.Browser.initDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Epic("Logout user and transitions between pages")
public class UserPersonalCabinetTest {
  private User user;
  private BasePage basePage;

  @Before
  public void setUp() throws IOException {
    //initialization our Browser
    initDriver();
    Configuration.timeout = 4000;
    //create an instance of User class
    user = UserGeneratorData.getRandomUser();
    //create an instance of BasePage class
    basePage = open(BasePage.BASE_URL, BasePage.class);
    basePage.clickLoginButton()
            .clickRegisterLink()
            .fillRegisterForm(user.getName(), user.getEmail(), user.getPassword())
            .clickRegisterButton(Condition.hidden);
    basePage = null;
  }

  @After
  public void clearState() {
    user = null;
    Selenide.clearBrowserLocalStorage();
  }

  @Test
  @DisplayName("Transition user to constructor")
  public void transitionToConstructor() {
    basePage = open(BasePage.BASE_URL, BasePage.class);
    String url = basePage.clickPersonalCabinetButton()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton()
            .clickConstructor();

    assertEquals(BasePage.BASE_URL, url);
  }

  @Test
  @DisplayName("Transition user to logo burger")
  public void transitionToLogoBurger() {
    basePage = open(BasePage.BASE_URL, BasePage.class);
    String url = basePage.clickPersonalCabinetButton()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton()
            .clickLogoBurger();

    assertEquals(BasePage.BASE_URL, url);
  }

  @Test
  @DisplayName("Logout user by logout button")
  public void logoutUserByLogoutButton() {
    basePage = open(BasePage.BASE_URL, BasePage.class);
    basePage.clickPersonalCabinetButton()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton(Condition.hidden);

    boolean isDisplayed = basePage.clickPersonalCabinetButtonGoAccountPage()
            .clickLogoutButton(Condition.hidden);

    assertFalse(isDisplayed);
  }
}

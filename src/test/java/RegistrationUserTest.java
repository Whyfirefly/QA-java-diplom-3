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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Epic("User's registration")
public class RegistrationUserTest {
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
  }

  @After
  public void clearState() {
    user = null;
    Selenide.clearBrowserLocalStorage();
  }

  @Test
  @DisplayName("Register user by valid credentials")
  public void registerUserByValidCredentials() {
    boolean isDisplayed = basePage.clickLoginButton()
            .clickRegisterLink()
            .fillRegisterForm(user.getName(), user.getEmail(), user.getPassword())
            .clickRegisterButton(Condition.hidden);

    assertFalse(isDisplayed);
  }

  @Test
  @DisplayName("Register user by invalid password")
  public void registerUserByInvalidPassword() {
    boolean isDisplayed = basePage.clickLoginButton()
            .clickRegisterLink()
            .fillRegisterForm(user.getName(), user.getEmail(), "yes7")
            .clickRegisterButton(Condition.visible);

    assertTrue(isDisplayed);
  }

  @Test
  @DisplayName("Register user is displayed password error")
  public void registerUserIsDisplayedPasswordError() {
    boolean isDisplayed = basePage.clickLoginButton()
            .clickRegisterLink()
            .fillRegisterForm(user.getName(), user.getEmail(), "yes7")
            .isDisplayedPasswordError();

    assertTrue(isDisplayed);
  }
}

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

@Epic("Login user")
public class LoginUserTest {
  private User user;
  private BasePage basePage;

  @Before
  public void setUp() throws IOException {
    user = UserGeneratorData.getRandomUser();
    //initialization our Browser
    initDriver();
    Configuration.timeout = 4000;
    //create an instance of BasePage class
    basePage = open(BasePage.BASE_URL, BasePage.class);
    basePage.clickLoginButton()
            .clickRegisterLink()
            .fillRegisterForm(user.getName(), user.getEmail(), user.getPassword())
            .clickRegisterButton(Condition.hidden);
    basePage = null;
    //create an instance of BasePage class
    basePage = open(BasePage.BASE_URL, BasePage.class);
  }

  @After
  public void clearState() {
    user = null;
    Selenide.clearBrowserLocalStorage();
  }

  @Test
  @DisplayName("Login user by login button 'Войти в аккаунт'")
  public void loginUserByLoginButton() {
    boolean isDisplayed = basePage.clickLoginButton()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton(Condition.hidden);

    assertFalse(isDisplayed);
  }

  @Test
  @DisplayName("Login user by personal cabinet button 'Личный кабинет'")
  public void loginUserByAccountButton() {
    boolean isDisplayed = basePage.clickPersonalCabinetButton()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton(Condition.hidden);

    assertFalse(isDisplayed);
  }

  @Test
  @DisplayName("Login user on the registration page")
  public void loginUserByRegisterPage() {
    boolean isDisplayed = basePage.clickLoginButton()
            .clickRegisterLink()
            .clickLoginLink()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton(Condition.hidden);

    assertFalse(isDisplayed);
  }

  @Test
  @DisplayName("Login user on the password recovery page")
  public void loginUserByForgotPasswordPage() {
    boolean isDisplayed = basePage.clickLoginButton()
            .clickForgotPasswordLink()
            .clickLoginLink()
            .fillLoginForm(user.getEmail(), user.getPassword())
            .clickLoginButton(Condition.hidden);

    assertFalse(isDisplayed);
  }
}

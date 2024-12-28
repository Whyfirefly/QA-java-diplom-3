import api.UserStepsApi;
import api.UserStepsApiChecks;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.BasePage;
import randomdata.UserGeneratorData;

import java.io.IOException;

import static browser.Browser.initDriver;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Epic("User's registration")
public class RegistrationUserTest {
  private ValidatableResponse response;
  private User user;
  private BasePage basePage;
  private static String accessToken;

  @Before
  public void setUp() throws IOException {
    //initialization our Browser
    initDriver();
    Configuration.timeout = 4000;

    //creation of user
    user = UserGeneratorData.getRandomUser();
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");

    //create an instance of BasePage class
    basePage = open(BasePage.BASE_URL, BasePage.class);
  }

  @After
  public void clearState() {
    //login user
    response = UserStepsApi.loginUser(user, accessToken);
    UserStepsApiChecks.checkUserLoginByValidCredentialsSuccess(response);

    //user deletion`
    if (accessToken != null) {
      response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
      UserStepsApiChecks.checkUserDeleteByValidCredentials(response);
    }
    //clear browser
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

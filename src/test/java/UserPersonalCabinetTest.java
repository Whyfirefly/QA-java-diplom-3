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
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Epic("Logout user and transitions between pages")
public class UserPersonalCabinetTest {
  private ValidatableResponse response;
  private User user;
  private BasePage basePage;
  private static String accessToken;

  @Before
  public void setUp() throws IOException {
    //initialization our Browser
    initDriver();
    Configuration.timeout = 4000;

    //create an instance of BasePage class
    basePage = open(BasePage.BASE_URL, BasePage.class);

    //creation of user
    user = UserGeneratorData.getRandomUser();
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");

    basePage = null;
  }

  @After
  public void clearState() {
    //user deletion`
    if (accessToken != null) {
      response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
      UserStepsApiChecks.checkUserDeleteByValidCredentials(response);
    }
    //clear browser
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
    accessToken = response.extract().path("accessToken");

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

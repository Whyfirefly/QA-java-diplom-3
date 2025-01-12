package api;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import pageobject.BasePage;
import pageobject.RegistrationPage;

import static api.RestApi.getBaseSpec;
import static constants.AnswerWhenCheckFailed.USER_SUCCESSFULLY_REMOVED_MESSAGE;
import static constants.EndPoints.USER_DELETION;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static sun.nio.cs.Surrogate.is;

public class UserStepsApi {
  private WebDriver driver;
  private BasePage basePage;
  private RegistrationPage registrationPage;
  private String accessToken;

  @Step("Send POST request to /api/auth/register")
  public static ValidatableResponse createUser(User user) {
    return given()
            .spec(getBaseSpec())
            .body(user)
            .log().all()
            .post(EndPoints.USER_AUTHENTICATION_API + "register")
            .then()
            .log().all();
  }

  @Step("Send POST request to /api/auth/login")
  public static ValidatableResponse loginUser(User user, String accessToken) {
    return given()
            .spec(getBaseSpec())
            .auth().oauth2(accessToken)
            .body(user)
            .log().all()
            .post(EndPoints.USER_AUTHENTICATION_API + "login")
            .then()
            .log().all();
  }



  @Step("Send DELETE request to /api/auth/user")
  public static ValidatableResponse deleteUser(String accessToken) {
    return given()
            .spec(getBaseSpec())
            .auth().oauth2(accessToken)
            .log().all()
            .delete(EndPoints.USER_DELETION)
            .then()
            .log().all();
  }




}

package api;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;

import static constants.AnswerWhenCheckFailed.USER_SUCCESSFULLY_REMOVED_MESSAGE;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserStepsApiChecks {

  @Step("Check status code 200 and body response - success:true when user login ")
  @DisplayName("User login by valid credentials")
  public static void checkUserLoginByValidCredentialsSuccess(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("accessToken", Matchers.notNullValue());
  }

  @Step("Check status code 202 and body response - success:true when we delete user")
  public static void checkUserDeleteByValidCredentials(ValidatableResponse response) {
    response
            .statusCode(SC_ACCEPTED)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("message", CoreMatchers.equalTo(USER_SUCCESSFULLY_REMOVED_MESSAGE));
  }
}

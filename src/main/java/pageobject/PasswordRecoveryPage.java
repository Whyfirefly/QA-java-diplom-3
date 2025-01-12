package pageobject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.page;

public class PasswordRecoveryPage {
  @FindBy(how = How.XPATH, using = ".//a[text()='Войти']")
  private SelenideElement loginLink;

  @Step("Click login link")
  public LoginPage clickLoginLink() {
    loginLink.click();
    return page(LoginPage.class);
  }
}

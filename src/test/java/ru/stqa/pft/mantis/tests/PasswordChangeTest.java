package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTest extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testPasswordChange() throws MessagingException, IOException {
        String newPassword = "newPassword";
        app.manageUser().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        User user = app.db().users().stream().filter((u) -> !(u.getUserName().equals("administrator"))).iterator().next();
        app.manageUser().resetPassword(user);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String passwordChangeLink = findPasswordChangeLink(mailMessages, user.getUserMail());
        app.manageUser().resetPasswordLinkMail(passwordChangeLink, newPassword);

        assertTrue(app.newSession().login(user.getUserName(), newPassword));
    }

    private String findPasswordChangeLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
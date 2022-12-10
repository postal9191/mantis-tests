package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.User;

public class UsersHelper extends HelperBase {

    public UsersHelper(ApplicationManager app) {
        super(app);
    }

    public void ResetPasswordByUi(User user) { //первая мысль кликать по ссылкам
        click(By.xpath("//*[contains(text(),'управление')]"));
        click(By.xpath("//*[contains(text(),'Управление пользователями')]"));
        click(By.xpath(String.format("//*[contains(text(),'%s')]", user.getUserName())));
        click(By.xpath("//*[@value='Сбросить пароль']"));
    }

    public void resetPassword(User user) { // более быстрый способ
        wd.get(String.format(app.getProperty("web.baseUrl") + "/manage_user_edit_page.php?user_id=%s", user.getId()));
        click(By.xpath("//*[@value='Сбросить пароль']"));
    }

    public void resetPasswordLinkMail(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//*[@value='Изменить учетную запись']"));
    }

    public void login(String administrator, String root) {
        type(By.name("username"), administrator);
        type(By.name("password"), root);
        click(By.xpath("//*[@value='Войти']"));
    }
}

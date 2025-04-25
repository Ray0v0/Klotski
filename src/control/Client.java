package control;

import model.UserLib;
import view.LoginFrame;
import view.UserFrame;

public class Client {
    private UserLib userLib;
    private LoginFrame loginFrame;
    private UserFrame userFrame;
    private UserLib.User user;

    public Client() {
        userLib = new UserLib();
        loginFrame = new LoginFrame(this);
    }

    public void login(UserLib.User user) {
        if (userLib.has(user)) {
            loginFrame.setVisible(false);
            userFrame = new UserFrame(this, user);
            loginFrame.dispose();
        } else {
            loginFrame.show("登陆失败：用户名或密码不正确！");
        }
    }

    public void register(UserLib.User user) {
        if (userLib.addUser(user)) {
            loginFrame.setVisible(false);
            loginFrame.dispose();
        } else {
            loginFrame.show("注册失败：用户名已被注册！");
        }
    }
}

import Controller.LoginMenuController;
import Model.User;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    @org.junit.jupiter.api.Test
    public void testLoginInput() throws IOException {
        String content1 = " -u   -u   ";
        Assertions.assertTrue(LoginMenuController.login(content1).equals("username field is empty"));
        String content2 = "  -u man  -u an  ";
        Assertions.assertTrue(LoginMenuController.login(content2).equals("invalid command"));
        String content3 = "-u sdlfj -p  ";
        Assertions.assertTrue(LoginMenuController.login(content3).equals("password field is empty"));
        String content4 = "-u \"sd f l j s fj sd s d    f\" -psdlfk -p sldkfj -p sldfj";
        Assertions.assertTrue(LoginMenuController.login(content4).equals("invalid command"));
    }

    @org.junit.jupiter.api.Test
    public void testLoginExist() throws IOException {
        User.addUser(new User("sadra", "aA@sldfjlsdf", "god", "nothing", "ho@g.c", 1, "1"));
        String content1 = "-u sadraa -p aA@sldfjlsdf";
        Assertions.assertTrue(LoginMenuController.login(content1).equals("this username does not exists"));
        String content2 = "-u sadra -p aA@sl";
        Assertions.assertTrue(LoginMenuController.login(content2).equals("username and password didn't match!"));
    }

    @org.junit.jupiter.api.Test
    public void testStayLoggedIn() throws IOException {
        String content1 = " --stay-logged-in--stay-logged-in ";
        Assertions.assertTrue(LoginMenuController.login(content1).equals("username field is empty"));
        String content2 = "--stay-logged-in     --stay-logged-in    ";
        Assertions.assertTrue(LoginMenuController.login(content2).equals("username field is empty"));
    }

    @org.junit.jupiter.api.Test
    public void testRegisterInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String content1 = "-u    -p     ";
        Assertions.assertTrue(LoginMenuController.register(content1, scanner).equals("username field is empty"));
        String content2 = "-u sldkfj -u sldfj ";
        Assertions.assertTrue(LoginMenuController.register(content2, scanner).equals("invalid command"));
        String content3 = "-u sdfASD_@";
        Assertions.assertTrue(LoginMenuController.register(content3, scanner).equals("username format is not correct"));
        String content4 = "-u sdf -p  -s sdlfj -";
        Assertions.assertTrue(LoginMenuController.register(content4, scanner).equals("password field is empty"));
        String content5 = "-u sdf -p sadfs -p sdfj";
        Assertions.assertTrue(LoginMenuController.register(content5, scanner).equals("invalid command"));
        String content6 = "-u sdf -p \"skjdfh  sdf \" ";
        Assertions.assertTrue(LoginMenuController.register(content6, scanner).equals("password format is not correct"));
    }

    @org.junit.jupiter.api.Test
    public void testRegisterPasswordStrong() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String content1 = "-u sdf -p sA@2s";
        Assertions.assertTrue(LoginMenuController.register(content1, scanner).equals("password is weak : short password"));
        String content2 = "-u sdf -p A@SDF234";
        Assertions.assertTrue(LoginMenuController.register(content2, scanner).equals("password is weak : need lower case"));
        String content3 = "-u sdf -p a@sdf234";
        Assertions.assertTrue(LoginMenuController.register(content3, scanner).equals("password is weak : need upper case"));
        String content4 = "-u sdf -p A@SDFsdf";
        Assertions.assertTrue(LoginMenuController.register(content4, scanner).equals("password is weak : need number"));
        String content5 = "-u sdf -p AasdfSDF234";
        Assertions.assertTrue(LoginMenuController.register(content5, scanner).equals("password is weak : need special character"));
    }

    @org.junit.jupiter.api.Test
    public void testRegisterExist() throws IOException {
        Scanner scanner = new Scanner(System.in);
        User.addUser(new User("sadra1232", "sadra", "sadra", "sadra", "sadra", 1, "father"));
        User.addUser(new User("sadra1231", "sadra", "sadra", "sadra", "sadra", 1, "father"));
        User.addUser(new User("sadra1230", "sadra", "sadra", "sadra", "sadra", 1, "father"));
        User.addUser(new User("sadra123", "sadra", "sadra", "sadra", "sadra", 1, "father"));
        String content1 = "-u sadra123 -p khar";
        Assertions.assertTrue(LoginMenuController.register(content1, scanner).equals("this username already exists\nyou can choose \"sadra1233\" instead of sadra123"));
    }

    @org.junit.jupiter.api.Test
    public void testForgotPassword() {
        Scanner scanner = new Scanner(System.in);
        User.addUser(new User("ali", "ali", "ali", "nothing", "ali", 1, "ali"));
        String username1 = "ali1";
        Assertions.assertTrue(LoginMenuController.forgotPassword(username1, scanner).equals("no user with this username exists"));
    }
}
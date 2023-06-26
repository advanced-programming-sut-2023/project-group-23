package Controller;

import Model.User;

import java.util.Random;
import java.util.regex.Matcher;

public class Controller {
    public static String deleteWhiteSpacesOfEnd(String string) {
        int lastIndex = string.length() - 1;
        while (string.charAt(lastIndex) == ' ' || string.charAt(lastIndex) == '\t') {
            lastIndex--;
        }
        String result = "";
        for (int i = 0; i <= lastIndex; i++) {
            result += string.charAt(i);
        }
        return result;
    }

    public static Integer findCounter(Matcher matcher) {
        Integer count = 0;
        if (matcher.find(0)) count++;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public static String captchaAddress() {
        Random random = new Random();
        Integer captcha = random.nextInt(50);
        return User.captchaList.get(captcha) + ".png";
    }
}

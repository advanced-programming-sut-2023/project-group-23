package Controller;

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
}

package util;

/**
 * @author Crown
 * @ClassName CharUtil
 * @Description 字符工具类
 * @email haocan@foxmail.com
 * @date 2018/7/19
 */
public class CharUtil {

    /**
     * <h3>第一个字母大写</h3>
     *
     * @param [str]
     * @return java.lang.String
     * @author Crown
     */
    public static String stringBeginCharToUpper(String str) {
        char[] chars = str.toCharArray();
        char c = charToUpperCase(chars[0]);
        chars[0] = c;
        return String.valueOf(chars);
    }

    /**
     * <h3>第一个字母小写</h3>
     *
     * @param [str]
     * @return java.lang.String
     * @author Crown
     */
    public static String stringBeginCharToLower(String str) {
        char[] chars = str.toCharArray();
        char c = charToLowerCase(chars[0]);
        chars[0] = c;
        return String.valueOf(chars);
    }

    /**
     * <h3>转小写</h3>
     *
     * @param [ch]
     * @return char
     * @author Crown
     */
    public static char charToUpperCase(char ch) {
        if (ch <= 122 && ch >= 97) {
            ch -= 32;
        }
        return ch;
    }

    /**
     * <h3>转大写</h3>
     *
     * @param [ch]
     * @return char
     * @author Crown
     */
    public static char charToLowerCase(char ch) {
        if (ch <= 90 && ch >= 65) {
            ch += 32;
        }
        return ch;
    }

}

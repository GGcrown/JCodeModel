package test;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Crown
 * @ClassName CharTest
 * @Description TODO
 * @email haocan@foxmail.com
 * @date 2018/7/24
 */
public class CharTest {


    @Test
    public void asciiStringToUtf() {
        // String you = "你";
        // System.out.println(cnToUnicode(you));
        String str = "呵呵\u6dfb\u52a0\u6210\u529f";
        System.out.println(str);
        // String s = cnToUnicode(str);
        // System.out.println(unicodeToCn(s));
    }

    @Test
    public void readFile() throws Exception {
        // 读取文件(字节流)
        Reader in = new InputStreamReader(new FileInputStream("e:\\123.txt"), "GBK");
        // 写如相应的文件
        PrintWriter out = new PrintWriter("e:\\321.txt");
        // 循环读取数据
        byte[] bytes = new byte[1024];
        int len = -1;
        len = in.read();
        while ((len = in.read()) != -1) {
            System.out.println(len);
            out.write(len);
        }
        // 清除缓存
        out.flush();
        // 关闭流
        in.close();
        out.close();
    }

    @Test
    public void readFile2() throws Exception {
        // 读取文件
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("e:\\123.txt")));
        // 写入相应的文件
        //循环读取数据
        List<String> stringList = new ArrayList<String>();
        String str = null;
        StringBuffer sb = new StringBuffer();
        // 正则表达式
        Pattern pattern = Pattern.compile("\\\\u\\d[\\d\\D]{3}");
        int i = 0;
        while ((str = in.readLine()) != null) {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                // matcher.appendReplacement(sb, "$");// 调用此方法报错
                str = str.replace(matcher.group(), unicodeToCn(matcher.group()));
            }
            System.out.println(str);
            stringList.add(str);
        }
        in.close();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("e:123.txt")));
        // 写入文件
        for (String s : stringList) {
            out.write(s);
            out.newLine();
        }
        out.flush();
        out.close();
    }

    @Test
    public void regexStr() {
        String text = "@ApiOperation(value = \"\u6839\\u636e\\u5b66\\u751fid\\u67e5\\u8be2\\u5b66\\u751f\", httpMethod = \"POST\", response = com.sun.codemodel.JDirectClass.class)\n";
        System.out.println(text);
        String replaceText = "&";//匹配到 替换的文字
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FFF]");//正则表达式 匹配 aa或bb或bb

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matcher.appendReplacement(sb, replaceText);
            String s = matcher.group();// 得到匹配的结果
            System.out.println(s);
        }
        matcher.appendTail(sb);
        System.out.printf("====>" + sb.toString());
    }

    @Test
    public void regexStr2() {
        // 方法开始时间
        long methodStart = System.currentTimeMillis();
        String text = "@ApiOperation(value = \"\u6839\\u636e\\u5b66\\u751fid\\u67e5\\u8be2\\u5b66\\u751f\", httpMethod = \"POST\", response = com.sun.codemodel.JDirectClass.class)\n";
        Pattern pattern = Pattern.compile("\\\\u\\d[\\d\\D]{3}");
        Matcher matcher = pattern.matcher(text);
        StringBuffer sb2 = new StringBuffer();
        // while (matcher.find()) {
        //     String group = matcher.group();
        // System.out.println(unicodeToCn(group));
        // Matcher matcher1 = matcher.appendReplacement(sb2, unicodeToCn(cnToUnicode(group)));
        // System.out.println("matcher1"+matcher1);
        // }

        String s = text.replaceAll("\\\\u\\d[\\d\\D]{3}", "hehe");
        // String replace = text.replace("\\u636e", unicodeToCn("\\u636e"));
        String replace1 = "";
        while (matcher.find()) {
            text = text.replace(matcher.group(), unicodeToCn(matcher.group()));
            matcher = pattern.matcher(text);
            System.out.println(text);
        }
        System.out.println(replace1);
        // 方法结束时间
        System.out.println("方法regexStr2总共用时====>" + (System.currentTimeMillis() - methodStart) + "毫秒");
        // System.out.println(text);


        // System.out.println(replace);
        // System.out.println(s);
        // System.out.println(text);
        // System.out.println(sb2);
    }


    public static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }


    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String retunrStr = "";
        for (int i = 0; i < chars.length; i++) {
            retunrStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return retunrStr;
    }

    @Test
    public void getPropertyTest() throws IOException {
        File directory = new File("");
        System.out.println(directory.getAbsolutePath() + "\\src\\main\\java\\wang\\crown9527\\test");//获得绝对路径
    }

    /**
     * <h3>递归文件</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/25
     */
    @Test
    public void recursionFileTest() throws Exception {
        File directory = new File("");
        String s = directory.getAbsolutePath() + "\\src\\main\\java\\wang\\crown9527\\test";
        System.out.println(s);//获得绝对路径
        // File file = new File(s);
        // System.out.println(file.isDirectory());

        // File baseDirectory = new File(directory.getAbsolutePath() + "\\src\\main\\java\\wang\\crown9527\\test");
        // boolean directory1 = baseDirectory.isDirectory();
        // File[] files = baseDirectory.listFiles();
        // for (File file : files) {
        //     System.out.println(file.getName());
        // }
        // System.out.println(directory1);

        List<String> stringList = CharTest.recursionFileName(s, "", null);
        for (int i = 0; i < stringList.size(); i++) {
            System.out.println(stringList.get(i));
        }

    }


    /**
     * <h3>递归文件夹下面的路径</h3>
     *
     * @param [path, relativePath, fileNameList]
     * @return java.util.List<java.lang.String>
     * @author Crown
     * @date 2018/7/25
     */
    public static List<String> recursionFileName(String path, String relativePath, List<String> fileNameList) throws Exception {
        if (fileNameList == null) {
            fileNameList = new ArrayList<String>(20);
        }
        File baseDirectory = new File(path);
        // 获得目录下的文件
        File[] files = baseDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String tempPath = relativePath.equals("") ? "" : relativePath + File.separator;
                    recursionFileName(path + File.separator + file.getName(), tempPath + file.getName(), fileNameList);
                } else {
                    relativePath = relativePath.equals("") ? "" : relativePath + File.separator;
                    fileNameList.add(relativePath + file.getName());
                }
            }
        }
        return fileNameList;
    }


}

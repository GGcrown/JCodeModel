package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Crown
 * @ClassName FileUtil
 * @Description 文件工具类
 * @email haocan@foxmail.com
 * @date 2018/7/25
 */
public class FileUtil {


    /**
     * <h3>unicode中文文件转成中文</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/25
     */
    public static void unicodeFileToCn(String path) throws Exception {
        // 读取文件
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        // 写入相应的文件
        //循环读取数据
        List<String> stringList = new ArrayList<String>();
        String str = null;
        // 正则表达式
        Pattern pattern = Pattern.compile("\\\\u\\d[\\d\\D]{3}");
        int i = 0;
        while ((str = in.readLine()) != null) {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                // matcher.appendReplacement(sb, "$");// 调用此方法报错
                str = str.replace(matcher.group(), CharUtil.unicodeToCn(matcher.group()));
            }
            System.out.println(str);
            stringList.add(str);
        }
        in.close();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        // 写入文件
        for (String s : stringList) {
            out.write(s);
            out.newLine();
        }
        out.flush();
        out.close();
    }

    /**
     * <h3>递归文件夹下面的路径</h3>
     *
     * @param [path]
     * @return java.util.List<java.lang.String>
     * @author Crown
     * @date 2018/7/25        
     */
    public static List<String> recursionFileName(String path) throws Exception {
        return recursionFileName(path, "", null);
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
                    String tempPath = relativePath.equals("") ? "" : relativePath + File.separator;
                    // relativePath = relativePath.equals("") ? "" : relativePath + File.separator;
                    fileNameList.add(tempPath + file.getName());
                }
            }
        }
        return fileNameList;
    }

    /**
     * <h3>递归目录下的文件unicode转中文</h3>
     *
     * @param [path]
     * @return void
     * @author Crown
     * @date 2018/7/25
     */
    public static void recurisionFileToUnicode(String path) throws Exception {
        // 当前项目下的
        String str = new File("").getAbsolutePath() + File.separator + path;
        List<String> stringList = FileUtil.recursionFileName(str);
        for (String s : stringList) {
            FileUtil.unicodeFileToCn(str + File.separator + s);
        }

    }


}

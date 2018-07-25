package util.generate.ssm;

import com.sun.codemodel.JType;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import util.base.CodeModelUtil;
import util.base.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Crown
 * @ClassName GenerateSSM
 * @Description 生成SSM类结构
 * @email haocan@foxmail.com
 * @date 2018/7/25
 */
public class GenerateSSM {


    public void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        // 指定 逆向工程配置文件
        File configFile = new File("src\\main\\resources\\generatorConfig.xml");
        File file = new File("");
        System.out.println(configFile.getAbsolutePath());

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }


    /**
     * <h3>生成模块</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/25
     */
    @Test
    public void generatorModule() throws Exception {
        JType pojoType = CodeModelUtil.codeModel.parseType("DcSysUser");
        CodeModelUtil.setBasePackage("wang.crown9527.test");
        CodeModelUtil.setModelName("用户");
        // 生成Contoller层
        GenerateController generateController = GenerateController.initClass(pojoType, "No101");
        // 生成dao层
        GenerateDao generateDao = GenerateDao.initClass(pojoType);
        // 生成service层
        GenerateService.initServiceClass(pojoType);
        // 生成ServiceImpl层
        GenerateServiceImpl.initClass(pojoType);
        // 导入文件
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }

    /**
     * <h3>MyBatis逆向工程</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/25        
     */
    @Test
    public void generatorTest() throws Exception {
        try {
            GenerateSSM generatorSqlmap = new GenerateSSM();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unicodeFileToCn()throws Exception{
        FileUtil.recurisionFileToUnicode("src\\main\\java\\wang\\crown9527\\test");
        // FileUtil.unicodeFileToCn("src\\main\\java\\wang\\crown9527\\test");

    }


}

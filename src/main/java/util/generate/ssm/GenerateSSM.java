package util.generate.ssm;

import com.sun.codemodel.JDefinedClass;
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


    /**
     * <h3>生成一个模块</h3>
     * 使用MyBatis逆向工创建好po和mapper后再调用此方法
     * 默认baseDirectory=src\\main\\java
     * 默认basePackage=wang.crown9527.test
     *
     * @param aClass 待生成模块的po类 如SysUser.class
     * @param modelName 模块名称，用于注释 如用户
     * @param aopModule 模块编号 如No101
     * @return void
     * @author Crown
     * @date 2018/7/27
     */
    public static void generatorSingleModule(Class aClass, String modelName, String aopModule) throws Exception {
        generatorSingleModule(aClass, modelName, aopModule, "src\\main\\java", "wang.crown9527.test");
    }

    /**
     * <h3>生成一个模块</h3>
     * 使用MyBatis逆向工创建好po和mapper后再调用此方法
     *
     * @param aClass 待生成模块的po类 如SysUser.class
     * @param modelName 模块名称，用于注释 如用户
     * @param aopModule 模块编号 如No101
     * @param baseDiretory 项目底层文件夹 如src\\main\\java
     * @param basePackage 底层包 如wang.crown9527.test
     * @return void
     * @author Crown
     * @date 2018/7/27        
     */
    public static void generatorSingleModule(Class aClass, String modelName, String aopModule, String baseDiretory, String basePackage) throws Exception {
        CodeModelUtil.setModelName(modelName);
        CodeModelUtil.setBasePackage(basePackage);
        // 根据po层重新生成po层
        GeneratePo generatePo = GeneratePo.iniClass(aClass);
        // 获得po类
        JDefinedClass po = generatePo.getGenClass();
        // 生成Contoller层
        GenerateController generateController = GenerateController.initClass(po, aopModule);
        // 生成dao层
        GenerateDao generateDao = GenerateDao.initClass(po);
        // 生成service层
        GenerateService generateService = GenerateService.initServiceClass(po);
        // 生成ServiceImpl层
        GenerateServiceImpl generateServiceImpl = GenerateServiceImpl.initClass(po);
        // 实现类
        generateServiceImpl.getGenClass()._implements(generateService.getGenClass());
        // 开始生成
        CodeModelUtil.codeModel.build(new File(baseDiretory));
        String replaceBasePackage = basePackage.replace(".", File.separator);
        // unicde文件转中文
        FileUtil.recurisionFileToUnicode(baseDiretory + File.separator + replaceBasePackage);
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
        JType pojoType = CodeModelUtil.codeModel.parseType("SysUser");
        CodeModelUtil.setBasePackage("wang.crown9527.test");
        CodeModelUtil.setModelName("用户");
        // 生成Contoller层
        GenerateController generateController = GenerateController.initClass(pojoType, "No101");
        // 生成dao层
        GenerateDao generateDao = GenerateDao.initClass(pojoType);
        // 生成service层
        GenerateService generateService = GenerateService.initServiceClass(pojoType);
        // 生成ServiceImpl层
        GenerateServiceImpl generateServiceImpl = GenerateServiceImpl.initClass(pojoType);
        // 实现类
        generateServiceImpl.getGenClass()._implements(generateService.getGenClass());
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
            GenerateSSM.generatorMyBatis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <h3>mybatis逆向工程</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/27        
     */
    public static void generatorMyBatis() throws Exception {
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


}

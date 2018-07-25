package test;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import org.junit.Test;
import util.*;

import java.io.File;

/**
 * @author Crown
 * @ClassName CodeModelUtilTest
 * @Description CodeModelUtils测试类
 * @email haocan@foxmail.com
 * @date 2018/7/19
 */
public class CodeModelUtilTest {

    String packageName = "wang.crown9527.test.";


    /**
     * <h3>测试生成属性方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     */
    @Test
    public void createPropertiesTest() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        // 生成文件
        File deskDir = new File("src/main/java");
        JDefinedClass genClass = codeModel._class("com.crown.test.CreateProperties");
        String property = "user";
        CodeModelUtil.generateProperties(codeModel, genClass, property);
        codeModel.build(new File("src/main/java"));
    }

    @Test
    public void createServiceAddMethodTest() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        // 生成文件
        File deskDir = new File("src/main/java");
        // 生成抽象类
        JDefinedClass genClass = codeModel._class(JMod.NONE, "com.crown.test.StudentService", ClassType.INTERFACE);
        GenerateService generateService = new GenerateService();
        generateService.setGenClass(genClass);
        generateService.setPojoType(codeModel.parseType("Student"));
        generateService.generateServiceAddMethod();
        codeModel.build(new File("src/main/java"));
    }

    @Test
    public void initCreateServiceClass() throws Exception {
        GenerateService.initServiceClass("com.crown.test.StudentService", CodeModelUtil.codeModel.parseType("Student"));
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }

    /**
     * <h3>测试Service实现</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    @Test
    public void initCreateServiceImplClass() throws Exception {
        String fullName = packageName + "StudentServiceImpl";
        String moduleType = "Student";
        GenerateServiceImpl.initClass(fullName, CodeModelUtil.codeModel.parseType(moduleType));
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }

    /**
     * <h3>测试Dao</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    @Test
    public void initCreateCreateDaoClass() throws Exception {
        String fullName = packageName + "StudentDao";
        String moduleType = "Student";
        GenerateDao.initClass(fullName, CodeModelUtil.codeModel.parseType(moduleType));
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }

    @Test
    public void intCreateControllerClassTest() throws Exception {
        String fullName = packageName + "StudentController";
        String moduleType = "Student";
        GenerateController.initClass(fullName, CodeModelUtil.codeModel.parseType(moduleType), "No101"
                , "学生");
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }


}

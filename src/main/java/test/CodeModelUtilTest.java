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
     * <h3>测试创建属性方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     */
    @Test
    public void createPropertiesTest() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        // 创建文件
        File deskDir = new File("src/main/java");
        JDefinedClass genClass = codeModel._class("com.crown.test.CreateProperties");
        String property = "user";
        CodeModelUtil.createProperties(codeModel, genClass, property);
        codeModel.build(new File("src/main/java"));
    }

    @Test
    public void createServiceAddMethodTest() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        // 创建文件
        File deskDir = new File("src/main/java");
        // 创建抽象类
        JDefinedClass genClass = codeModel._class(JMod.NONE, "com.crown.test.StudentService", ClassType.INTERFACE);
        CreateService createService = new CreateService();
        createService.setGenClass(genClass);
        createService.setPojoType(codeModel.parseType("Student"));
        createService.createServiceAddMethod();
        codeModel.build(new File("src/main/java"));
    }

    @Test
    public void initCreateServiceClass() throws Exception {
        CreateService.initServiceClass("com.crown.test.StudentService", CodeModelUtil.codeModel.parseType("Student"));
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
        CreateServiceImpl.initClass(fullName, CodeModelUtil.codeModel.parseType(moduleType));
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
        CreateDao.initClass(fullName, CodeModelUtil.codeModel.parseType(moduleType));
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }

    @Test
    public void intCreateControllerClassTest()throws Exception{
        String fullName = packageName + "StudentController";
        String moduleType = "Student";
        CreateController.initClass(fullName, CodeModelUtil.codeModel.parseType(moduleType));
        CodeModelUtil.codeModel.build(new File("src/main/java"));
    }






}

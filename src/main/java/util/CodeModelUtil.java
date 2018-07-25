package util;


import com.sun.codemodel.*;

/**
 * @author Crown
 * @ClassName CodeModelUtil
 * @Description CodeModel工具包
 * @email haocan@foxmail.com
 * @date 2018/7/19
 */
public class CodeModelUtil {

    // CodeModel 模块代码
    public static JCodeModel codeModel = new JCodeModel();
    // Exception 异常
    public static JClass exception = codeModel.ref("java.lang.Exception");
    // BusinessException 业务异常
    public static JType businessException;
    // BaseModel 基础模块
    public static JType baseModel;
    // String String类型
    public static JClass string;
    // List类型
    // public static JType list;
    public static JClass list;
    // ArrayList类型
    public static JClass arrayList;
    // 基础控制类
    public static JClass baseController;


    /*
     * 注解类
     */
    // Autowired 自动注入类
    public static JClass autowired;
    // PathVariable 变量类
    public static JClass pathVariable;


    static {
        try {
            // 设置值
            businessException = codeModel.parseType("BusinessException");
            baseModel = codeModel.parseType("BaseModel");
            string = codeModel.ref("java.lang.String");
            list = codeModel.ref("java.lang.List");
            arrayList = codeModel.ref("jvaa.lang.ArrayList");
            autowired = codeModel.ref("org.springframework.beans.factory.annotation.Autowire");
            pathVariable = codeModel.ref("org.springframework.web.bind.annotation.PathVariable");
            baseController = codeModel.ref("org.zsc.common.controller.BaseController");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * <h3>生成私有属性 类型为String 包含GetSet方法 </h3>
     *
     * @param [codeModel, genClass, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateProperties(JCodeModel codeModel, JDefinedClass genClass, String propertyName) throws ClassNotFoundException {
        generateProperties(codeModel, genClass, codeModel.parseType("String"), propertyName);
    }

    /**
     * <h3>生成私有属性 包含GetSet方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateProperties(JCodeModel codeModel, JDefinedClass genClass, JType jType, String propertyName) {
        generateProperties(codeModel, genClass, JMod.PRIVATE, jType, propertyName);
    }

    /**
     * <h3>生成属性方法 包含GetSet方法</h3>
     *
     * @param [codeModel, genClass, mods, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateProperties(JCodeModel codeModel, JDefinedClass genClass, int mods, JType jType, String propertyName) {
        // 生成属性
        JFieldVar field = genClass.field(mods, jType, propertyName);
        generateGetSetMethod(codeModel, genClass, jType, propertyName);
    }

    /**
     * <h3>生成GetSet方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateGetSetMethod(JCodeModel codeModel, JDefinedClass genClass, JType jType, String propertyName) {
        generateGetMethod(codeModel, genClass, jType, propertyName);
        generateSetMethod(codeModel, genClass, propertyName);
    }

    /**
     * <h3>生成get方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateGetMethod(JCodeModel codeModel, JDefinedClass genClass, JType jType, String propertyName) {
        // 生成方法
        JMethod method = genClass.method(JMod.PUBLIC, jType, "get" + CharUtil.stringBeginCharToUpper(propertyName));
        // 方法体
        JBlock methodBlock = method.body();
        methodBlock.decl(codeModel.INT, "te");
        methodBlock._return(JExpr.refthis(propertyName));
    }

    /**
     * <h3>生成set方法</h3>
     *
     * @param [codeModel, genClass, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateSetMethod(JCodeModel codeModel, JDefinedClass genClass, String propertyName) {
        try {
            // 生成方法
            JMethod method = genClass.method(JMod.PUBLIC, codeModel.VOID, "set" + CharUtil.stringBeginCharToUpper(propertyName));
            JVar param = method.param(codeModel.parseType("String"), propertyName);
            // 方法体
            JBlock methodBlock = method.body();
            methodBlock.assign(JExpr.refthis(propertyName), JExpr.ref(propertyName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

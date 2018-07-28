package util.base;


import com.sun.codemodel.*;
import com.sun.org.apache.bcel.internal.util.Objects;

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
    // 模块名称(中文)
    private static String modelName;
    // 底层包路径
    public static String basePackage = "";
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




    static {
        try {
            // 设置值
            businessException = codeModel.parseType("BusinessException");
            baseModel = codeModel.parseType("BaseModel");
            string = codeModel.ref("java.lang.String");
            list = codeModel.ref("java.lang.List");
            arrayList = codeModel.ref("jvaa.lang.ArrayList");
            baseController = CodeModelUtil.codeModel.ref("org.zsc.common.controller.BaseController");

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
    public static void generateProperties(JDefinedClass genClass, String propertyName) throws ClassNotFoundException {
        generateProperties(genClass, codeModel.parseType("String"), propertyName);
    }

    /**
     * <h3>生成私有属性 包含GetSet方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateProperties(JDefinedClass genClass, JType jType, String propertyName) {
        generateProperties(genClass, JMod.PRIVATE, jType, propertyName);
    }

    /**
     * <h3>生成属性方法 包含GetSet方法</h3>
     *
     * @param [genClass, mods, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateProperties(JDefinedClass genClass, int mods, JType jType, String propertyName) {
        // 生成属性
        JFieldVar field = genClass.field(mods, jType, propertyName);
        generateGetSetMethod(genClass, jType, propertyName);
    }

    /**
     * <h3>生成GetSet方法</h3>
     *
     * @param [genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateGetSetMethod(JDefinedClass genClass, JType jType, String propertyName) {
        generateGetMethod(genClass, jType, propertyName);
        generateSetMethod(genClass, jType, propertyName);
    }

    /**
     * <h3>生成get方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateGetMethod(JDefinedClass genClass, JType jType, String propertyName) {
        // 生成方法
        JMethod method = genClass.method(JMod.PUBLIC, jType, "get" + CharUtil.stringBeginCharToUpper(propertyName));
        // 方法体
        JBlock methodBlock = method.body();
        methodBlock._return(JExpr.refthis(propertyName));
    }

    /**
     * <h3>生成set方法</h3>
     *
     * @param [codeModel, genClass, propertyName]
     * @return void
     * @author Crown
     */
    public static void generateSetMethod(JDefinedClass genClass, JType jType, String propertyName) {
        // 生成方法
        JMethod method = genClass.method(JMod.PUBLIC, codeModel.VOID, "set" + CharUtil.stringBeginCharToUpper(propertyName));
        JVar param = method.param(jType, propertyName);
        // 方法体
        JBlock methodBlock = method.body();
        methodBlock.assign(JExpr.refthis(propertyName), JExpr.ref(propertyName));
    }

    public static String getBasePackage() {
        return basePackage;
    }

    public static void setBasePackage(String basePackage) {
        CodeModelUtil.basePackage = basePackage;
    }

    public static String getModelName() {
        return Objects.equals(modelName, "") ? CodeModelConstants.TODO : modelName;
    }

    public static void setModelName(String modelName) {
        CodeModelUtil.modelName = modelName;
    }
}

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
    public static JType string;
    // List类型
    public static JType list;
    // ArrayList类型
    public static JType arrayList;

    static {
        try {
            // 设置值
            businessException = codeModel.parseType("BusinessException");
            baseModel = codeModel.parseType("BaseModel");
            string = codeModel.parseType("String");
            list = codeModel.parseType("java.util.List");
            arrayList = codeModel.parseType("java.util.ArrayList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * <h3>创建私有属性 类型为String 包含GetSet方法 </h3>
     *
     * @param [codeModel, genClass, propertyName]
     * @return void
     * @author Crown
     */
    public static void createProperties(JCodeModel codeModel, JDefinedClass genClass, String propertyName) throws ClassNotFoundException {
        createProperties(codeModel, genClass, codeModel.parseType("String"), propertyName);
    }

    /**
     * <h3>创建私有属性 包含GetSet方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void createProperties(JCodeModel codeModel, JDefinedClass genClass, JType jType, String propertyName) {
        createProperties(codeModel, genClass, JMod.PRIVATE, jType, propertyName);
    }

    /**
     * <h3>创建属性方法 包含GetSet方法</h3>
     *
     * @param [codeModel, genClass, mods, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void createProperties(JCodeModel codeModel, JDefinedClass genClass, int mods, JType jType, String propertyName) {
        // 创建属性
        JFieldVar field = genClass.field(mods, jType, propertyName);
        createGetSetMethod(codeModel, genClass, jType, propertyName);
    }

    /**
     * <h3>创建GetSet方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void createGetSetMethod(JCodeModel codeModel, JDefinedClass genClass, JType jType, String propertyName) {
        createGetMethod(codeModel, genClass, jType, propertyName);
        createSetMethod(codeModel, genClass, propertyName);
    }

    /**
     * <h3>创建get方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public static void createGetMethod(JCodeModel codeModel, JDefinedClass genClass, JType jType, String propertyName) {
        // 创建方法
        JMethod method = genClass.method(JMod.PUBLIC, jType, "get" + CharUtil.stringBeginCharToUpper(propertyName));
        // 方法体
        JBlock methodBlock = method.body();
        methodBlock.decl(codeModel.INT, "te");
        methodBlock._return(JExpr.refthis(propertyName));
    }

    /**
     * <h3>创建set方法</h3>
     *
     * @param [codeModel, genClass, propertyName]
     * @return void
     * @author Crown
     */
    public static void createSetMethod(JCodeModel codeModel, JDefinedClass genClass, String propertyName) {
        try {
            // 创建方法
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

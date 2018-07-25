package util.base;

import com.sun.codemodel.*;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Crown
 * @ClassName BaseClass
 * @Description 基础类类
 * @email haocan@foxmail.com
 * @date 2018/7/20
 */
public class BaseClass<T> {

    // 代码模块
    protected JCodeModel codeModel = CodeModelUtil.codeModel;
    // 模块Service类
    protected JDefinedClass genClass;
    // 模块类型
    protected JType pojoType;
    // 模块名称 (中文)
    protected String moduleName;

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public BaseClass() {
    }

    /**
     * <h3>两参构造函数</h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public BaseClass(JDefinedClass genClass, JType pojoType) {
        this.genClass = genClass;
        this.pojoType = pojoType;
    }

    /**
     * <h3></h3>
     *
     * @param [genClass, pojoType, moduleName]
     * @return
     * @author Crown
     * @date
     */
    public BaseClass(JDefinedClass genClass, JType pojoType, String moduleName) {
        this.genClass = genClass;
        this.pojoType = pojoType;
        this.moduleName = moduleName;
    }

    /**
     * <h3>生成类注释</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    protected void generateClassJavaDoc() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        JDocComment javadoc = genClass.javadoc();
        javadoc.addXdoclet("ClassName " + genClass.name());
        javadoc.addXdoclet("author Crown");
        javadoc.addXdoclet("Description " + CodeModelConstants.TODO);
        javadoc.addXdoclet("email haocan@foxmail.com");
        javadoc.addXdoclet("date " + sdf.format(date));
    }

    /**
     * <h3>生成方法注释 在方法参数设置好后再调用此方法</h3>
     *
     * @param [method]
     * @return void
     * @author Crown
     * @date 2018/7/24
     */
    protected void generateMehotdJavaDoc(JMethod method) {
        generateMehotdJavaDoc(method, CodeModelConstants.TODO, "");
    }

    /**
     * <h3>生成方法注释 在方法参数设置好后再调用此方法</h3>
     *
     * @param [method, annotation]
     * @return void
     * @author Crown
     * @date 2018/7/24
     */
    protected void generateMehotdJavaDoc(JMethod method, String annotation, String returnType) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        List<JVar> params = method.params();
        StringBuilder str = new StringBuilder();
        for (JVar param : params) {
            str.append("," + param.name());
        }
        String paramStr = str.substring(1, str.length());
        JDocComment javadoc = method.javadoc();
        javadoc.add("<h3>" + annotation + "</h3>\n\n");
        javadoc.add("@param [" + paramStr + "] \n");
        javadoc.add("@return " + returnType + "\n");
        javadoc.add("@author Crown\n");
        javadoc.add("@date " + sdf.format(date));

    }

    /**
     * <h3>用于实例化对象，目前没什么使用价值</h3>
     *
     * @param [fullName, jType, aClass]
     * @return A
     * @author Crown
     * @date 2018/7/20
     * @deprecated
     */
    public static <A> A initClass(String fullName, JType jType, Class<A> aClass) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC, fullName, ClassType.INTERFACE);
        Constructor<A> constructor = aClass.getConstructor(JDefinedClass.class, JType.class);
        return constructor.newInstance(genClass, jType);
    }


    /**
     * <h3>生成抽象方法</h3>
     *
     * @param [methodName 方法名称,默认第二个参数类型为当前模块类型,默认参数名称为模块类型的小写形式]
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    protected void generateAbstractMethod(String methodName) throws Exception {
        generateAbstractMethod(methodName, this.pojoType);
    }

    /**
     * <h3>生成抽象方法</h3>
     *
     * @param [methodName 方法名称, jType 第二个参数类型,默认参数名称为参数类型的小写形式]
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    protected void generateAbstractMethod(String methodName, JType jType) throws Exception {
        generateAbstractMethod(methodName, jType, jType.name());
    }

    /**
     * <h3>生成抽象方法</h3>
     *
     * @param [methodName 方法名称, jType 第二个参数类型, paramName 第二个参数名称]
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateAbstractMethod(String methodName, JType jType, String paramName) throws Exception {
        JMethod addDataMethod = genClass.method(JMod.NONE, codeModel.VOID, methodName);
        // 第一个参数 baseModel
        addDataMethod.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        // 第二个参数
        addDataMethod.param(jType, CharUtil.stringBeginCharToLower(paramName));
        // 抛出Exception异常
        addDataMethod._throws(CodeModelUtil.exception);
    }

    public String getFindPageDataMethodName() {
        return "findPageData" + this.genClass.name() + "Vo";
    }

    public String getCountPageDataMethodName() {
        return "findPageData" + this.genClass.name() + "Vo";
    }

    public JCodeModel getCodeModel() {
        return codeModel;
    }

    public JDefinedClass getGenClass() {
        return genClass;
    }

    public JType getPojoType() {
        return pojoType;
    }

    public void setGenClass(JDefinedClass genClass) {
        this.genClass = genClass;
    }

    public void setPojoType(JType pojoType) {
        this.pojoType = pojoType;
    }

}

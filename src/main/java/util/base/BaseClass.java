package util.base;

import com.sun.codemodel.*;
import com.sun.org.apache.bcel.internal.util.Objects;

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
    protected String moduleName = CodeModelUtil.getModelName();

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

    protected void generateClassJavaDoc() {
        generateClassJavaDoc(CodeModelConstants.TODO);
    }

    /**
     * <h3>生成类注释</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    protected void generateClassJavaDoc(String description) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        JDocComment javadoc = genClass.javadoc();
        javadoc.add("author Crown\n");
        javadoc.add("className " + this.genClass.name() + "\n");
        javadoc.add("description " + description + "\n");
        javadoc.add("email haocao@foxmail.com" + "\n");
        javadoc.add("date " + sdf.format(date));
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
    protected JMethod generateAbstractMethod(String methodName) throws Exception {
        return generateAbstractMethod(methodName, this.pojoType);
    }

    /**
     * <h3>生成抽象方法</h3>
     *
     * @param [methodName 方法名称, jType 第二个参数类型,默认参数名称为参数类型的小写形式]
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    protected JMethod generateAbstractMethod(String methodName, JType jType) throws Exception {
        return generateAbstractMethod(methodName, jType, jType.name());
    }

    /**
     * <h3>生成抽象方法</h3>
     *
     * @param [methodName 方法名称, jType 第二个参数类型, paramName 第二个参数名称]
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public JMethod generateAbstractMethod(String methodName, JType jType, String paramName) throws Exception {
        JMethod method = genClass.method(JMod.NONE, codeModel.VOID, methodName);
        // 第一个参数 baseModel
        method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        // 第二个参数
        method.param(jType, CharUtil.stringBeginCharToLower(paramName));
        // 抛出Exception异常
        method._throws(CodeModelUtil.exception);
        return method;
    }

    /**
     * <h3>生成私有属性 类型为String 包含GetSet方法 </h3>
     *
     * @param [codeModel, genClass, propertyName]
     * @return void
     * @author Crown
     */
    public JFieldVar generateProperties(String propertyName) throws ClassNotFoundException {
        return generateProperties(codeModel.parseType("String"), propertyName);
    }

    /**
     * <h3>生成私有属性 包含GetSet方法</h3>
     *
     * @param [codeModel, genClass, jType, propertyName]
     * @return void
     * @author Crown
     */
    public JFieldVar generateProperties(JType jType, String propertyName) {
        return generateProperties(JMod.PRIVATE, jType, propertyName);
    }

    /**
     * <h3>生成属性方法 包含GetSet方法</h3>
     *
     * @param [mods, jType, propertyName]
     * @return void
     * @author Crown
     */
    public JFieldVar generateProperties(int mods, JType jType, String propertyName) {
        // 生成属性
        JFieldVar field = genClass.field(mods, jType, propertyName);
        generateGetSetMethod(jType, propertyName);
        return field;
    }

    /**
     * <h3>生成GetSet方法</h3>
     *
     * @param [jType, propertyName]
     * @return void
     * @author Crown
     */
    public void generateGetSetMethod(JType jType, String propertyName) {
        generateGetMethod(jType, propertyName);
        generateSetMethod(jType, propertyName);
    }

    /**
     * <h3>生成get方法</h3>
     *
     * @param [jType, propertyName]
     * @return void
     * @author Crown
     */
    public void generateGetMethod(JType jType, String propertyName) {
        // 生成方法
        JMethod method = genClass.method(JMod.PUBLIC, jType, "get" + CharUtil.stringBeginCharToUpper(propertyName));
        // 方法体
        JBlock methodBlock = method.body();
        methodBlock._return(JExpr.refthis(propertyName));
    }

    /**
     * <h3>生成set方法</h3>
     *
     * @param [jType, propertyName]
     * @return void
     * @author Crown
     */
    public void generateSetMethod(JType jType, String propertyName) {
        // 生成方法
        JMethod method = genClass.method(JMod.PUBLIC, codeModel.VOID, "set" + CharUtil.stringBeginCharToUpper(propertyName));
        JVar param = method.param(jType, propertyName);
        // 方法体
        JBlock methodBlock = method.body();
        methodBlock.assign(JExpr.refthis(propertyName), JExpr.ref(propertyName));
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

    public String getModuleName() {
        return Objects.equals(moduleName, "") ? CodeModelConstants.TODO : moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}

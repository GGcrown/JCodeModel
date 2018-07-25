package learn;

import com.sun.codemodel.*;
import org.junit.Test;

import java.io.File;


/**
 * @author Crown
 * @ClassName HelloCodeModel
 * @Description TODO
 * @email haocan@foxmail.com
 * @date 2018/7/17
 */
public class HelloCodeModel {

    /**
     * <h3>使用中文会被转成Ascci码</h3>
     *
     */
    @Test
    public void codeModelTest1() throws Exception {
        // 实例化CodeModel
        JCodeModel cm = new JCodeModel();
        // 生成TestMain类
        JDefinedClass dc = cm._class("com.example.test.TestMain");
        // 指明引用类型
        JType type = cm.parseType("String");
        JType type1 = cm.parseType("Student");

        // 定义方法
        JMethod exampleMethod = dc.method(JMod.PUBLIC + JMod.STATIC, cm.VOID, "exampleMethod");
        // 方法参数
        exampleMethod.param(type, "str1");
        exampleMethod.param(type, "str2");
        JBlock exampleMethodBlk = exampleMethod.body();
        // 成员变量声明
        JFieldVar sFiledVar = dc.field(JMod.PRIVATE + JMod.STATIC, cm.INT, "userAge", JExpr.lit(10));
        // 局部变量声明
        JVar var = exampleMethodBlk.decl(type, "fieldVar");
        JVar var1 = exampleMethodBlk.decl(type, "fieldVar1", JExpr.lit("5"));
        JVar student = exampleMethodBlk.decl(type1, "student", JExpr._null());
        // new出一个typ1对象 new Student()赋值给student局部对象
        exampleMethodBlk.assign(student, JExpr._new(type1));
        // 数组变量生成与赋值
        // 定义为数组类型
        JType arrType = cm.parseType("int []");
        // 生成整形的数组成员变量
        JArray initIntArray = JExpr.newArray(cm.INT);
        dc.field(JMod.PUBLIC, arrType, "arr", initIntArray);
        initIntArray.add(JExpr.lit(0));
        initIntArray.add(JExpr.lit(1));
        // 生成整型数组的局部变量
        exampleMethodBlk.decl(arrType, "partArray", initIntArray);
        // list数据类型
        JType listType = cm.parseType("List");
        JType arrayListType = cm.parseType("ArrayList");
        JVar list = exampleMethodBlk.decl(listType, "list", JExpr._new(arrayListType));
        exampleMethodBlk.invoke(list, "add").arg("aa");
        // 变量类型强制转换
        JVar longValue = exampleMethodBlk.decl(cm.LONG, "longValue", JExpr.lit(99L));
        JVar intValu = exampleMethodBlk.decl(cm.INT, "intValue", JExpr.cast(cm.INT, JExpr.ref("longValue")));
        cm.build(new File("src/main/java"));
    }

    /**
     * <h3>从清单9开始</h3>
     *
     * @param []
     * @return void
     * @author Crown
     */
    @Test
    public void codeModelTest2() throws Exception {
        JCodeModel cm = new JCodeModel();
        // 生成类
        // cm._class("com.example.test.TestMainNine");

        JDefinedClass exampleClass = cm._class("com.example.test.TestMainNine");
        JMethod methodA = exampleClass.method(JMod.PUBLIC, cm.VOID, "methodA");
        // 抽象类不能直接使用，必须使用子类去实现抽象类,然后再使用其子类的实例
        // 生成抽象类
        JDefinedClass abstractClass = cm._class(JMod.PUBLIC + JMod.ABSTRACT
                , "com.example.test.AbstractClass", ClassType.CLASS);
        // 抽象方法
        abstractClass.method(JMod.PUBLIC, cm.VOID, "methodB");
        // 子类实现抽象类
        JDefinedClass sonClass = cm._class(JMod.PUBLIC, "com.example.test.SonClass", ClassType.CLASS);
        sonClass._extends(abstractClass);
        JBlock methodB = sonClass.method(JMod.PUBLIC, cm.VOID, "methodB").body();
        // 生成接口
        JDefinedClass exampleInterface = cm._class(JMod.PUBLIC, "com.example.test.Runner", ClassType.INTERFACE);
        exampleClass.field(JMod.NONE, cm.INT, "ID", JExpr.lit(1));
        exampleInterface.method(JMod.PUBLIC, cm.VOID, "start");
        exampleInterface.method(JMod.PUBLIC, cm.VOID, "run");
        exampleInterface.method(JMod.PUBLIC, cm.VOID, "stop");
        // 通过类来实现接口
        JDefinedClass impClass = cm._class(JMod.PUBLIC, "com.example.test.Person", ClassType.CLASS);
        impClass._implements(exampleClass);
        impClass.method(JMod.PUBLIC, cm.VOID, "start").body().invoke("actions");
        impClass.method(JMod.PUBLIC, cm.VOID, "run").body().invoke("actions");
        impClass.method(JMod.PUBLIC, cm.VOID, "stop").body().invoke("actions");
        cm.build(new File("src/main/java"));
    }


    /**
     * <h3>13开始</h3>
     *
     * @param []
     * @return void
     * @author Crown
     */
    @Test
    public void codeModelTest3() throws Exception {
        JCodeModel cm = new JCodeModel();
        JDefinedClass dc = cm._class("com.example.test.SimpleClass");
        JMethod runMethod = dc.method(JMod.PUBLIC, cm.VOID, "run");
        // 基本类的调用
        JBlock runMethodBody = runMethod.body();
        JClass sys = cm.ref("java.lang.System");
        runMethodBody.invoke(sys.staticRef("out"), "println").arg("Hello CodeModel");
        // 自定义类的调用
        JDefinedClass customClass = cm._class("com.example.test.CustomClass");
        customClass.field(JMod.NONE, cm.INT, "day");
        customClass.field(JMod.NONE, cm.INT, "month");
        customClass.field(JMod.NONE, cm.INT, "year");

        JType myDateType = cm.parseType("MyDate");
        JMethod newMyDate = customClass.method(JMod.PUBLIC, cm.VOID, "newMyDate");
        JBlock blk = newMyDate.body();
        JVar today = blk.decl(myDateType, "today");
        blk.assign(today, JExpr._new(myDateType));
        blk.invoke(JExpr.ref("xixi"), "methodCCC").arg("服了呀");


        cm.build(new File("src/main/java"));
    }


    /**
     * <h3>控制语句开始</h3>
     *
     * @param []
     * @return void
     * @author Crown
     */
    @Test
    public void codeModelTest4() throws Exception {
        JCodeModel cm = new JCodeModel();
        JDefinedClass controllerStatementClass = cm._class("com.example.test.ControllerStatementClass");
        // if 控制语句
        JMethod statementTest = controllerStatementClass.method(JMod.PUBLIC, cm.VOID, "statementTest");
        JBlock statemtnTestBody = statementTest.body();
        statemtnTestBody.decl(cm.INT, "a1", JExpr.lit(1));
        statemtnTestBody.decl(cm.INT, "a2", JExpr.lit(2));

        // a1 <a2
        JConditional outerIf = statemtnTestBody._if(JExpr.ref("a1").lt(JExpr.ref("a2")));
        JBlock outerThen = outerIf._then();
        JBlock outherElse = outerIf._else();
        // switch 控制语句
        statemtnTestBody.decl(cm.INT, "colorNum", JExpr.lit(0));
        JSwitch exampleSwitch = statemtnTestBody._switch(JExpr.ref("colorNum"));
        JCase caseA = exampleSwitch._case(JExpr.lit(0));
        caseA.body().invoke("caseA");
        JCase caseB = exampleSwitch._case(JExpr.lit(1));
        JBlock caseBBody = caseB.body();
        caseBBody.invoke("caseB");
        caseBBody._break();
        JCase caseC = exampleSwitch._case(JExpr.lit(2));
        caseC.body()._break();
        JCase caseDefault = exampleSwitch._default();
        JBlock defaultBody = caseDefault.body();
        defaultBody._break();
        cm.build(new File("src/main/java"));
    }


    /**
     * <h3>循环语句</h3>
     *
     * @param []
     * @return void
     * @author Crown
     */
    @Test
    public void codeModelTest5() throws Exception {
        JCodeModel cm = new JCodeModel();
        JDefinedClass loopStatementClass = cm._class("com.example.test.LoopStatementClass");
        JMethod forLoopTest = loopStatementClass.method(JMod.PUBLIC, cm.VOID, "forLoopTest");
        JBlock forLoopBody = forLoopTest.body();
        JForLoop forLoop = forLoopBody._for();
        forLoop.init(cm.INT,"i", JExpr.lit(0));
        forLoop.test(JExpr.ref("i").lt(JExpr.lit(10)));
        forLoop.update(JExpr.ref("i").incr());
        // forLoop.update(JExpr.ref("i").assignPlus(JExpr.lit(1)));
        JBlock forBody = forLoop.body();
        forBody.invoke("xixixi");


        cm.build(new File("src/main/java"));
    }

    @Test
    public void tttTest()throws Exception{
        JCodeModel cm = new JCodeModel();
        JDefinedClass loopStatementClass = cm._class("com.example.test.LoopStatementClass");


        cm.build(new File("src/main/java"));
    }



}

package util.generate.ssm;

import com.sun.codemodel.*;
import util.base.BaseAnnotation;
import util.base.BaseClass;
import util.base.CharUtil;
import util.base.CodeModelUtil;

/**
 * @author Crown
 * @ClassName GenerateServiceImpl
 * @Description 生成ServiceImpl层
 * @email haocan@foxmail.com
 * @date 2018/7/20
 */
public class GenerateServiceImpl extends BaseClass {

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public GenerateServiceImpl() {
    }

    /**
     * <h3></h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public GenerateServiceImpl(JDefinedClass genClass, JType pojoType) {
        super(genClass, pojoType);
    }

    /**
     * <h3>初始化类</h3>
     *
     * @param [fullName, jType]
     * @return test.util.generate.ssm.GenerateServiceImpl
     * @author Crown
     * @date 2018/7/21
     */
    public static GenerateServiceImpl initClass(JType jType) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC,
            CodeModelUtil.getBasePackage() + ".service.impl." + jType.name() + "ServiceImpl"
            , ClassType.CLASS);
        // 初始化实例
        GenerateServiceImpl generateServiceImpl = new GenerateServiceImpl(genClass, jType);
        // 生成类注释
        generateServiceImpl.generateClassJavaDoc(generateServiceImpl.getModuleName() + "服务层");
        // 生成类注解
        generateServiceImpl.getGenClass().annotate(BaseAnnotation.service);
        // 生成基本方法
        generateServiceImpl.generateBaseMethod();
        return generateServiceImpl;
    }


    /**
     * <h3>生成基础方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void generateBaseMethod() throws Exception {
        generateProperty();
        generateServiceImplAddMethod();
        generateServiceImplDeleteMethod();
        generateServiceImplUpdateMethod();
        generateServiceImplFindPageDataMethod();
        generateServiceImplFindByPkMethod();
    }

    /**
     * <h3>生成属性</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/28
     */
    public void generateProperty() throws Exception {
        JFieldVar field = this.genClass.field(JMod.NONE, this.codeModel.parseType(getPropertyDaoName())
            , CharUtil.stringBeginCharToLower(getPropertyDaoName()));
        field.annotate(BaseAnnotation.autowired);
    }

    /**
     * <h3>生成serviceImpl add方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void generateServiceImplAddMethod() {
        // 生成addData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, this.codeModel.VOID, "addData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(typePram, "insert").arg(typePram);
        // 生成注释
        this.generateMehotdJavaDoc(method, "新增" + this.moduleName, "");
        // 生成注解
        method.annotate(BaseAnnotation.override);

        // 生成方法如下:
        // @Override
        // public void addData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     salesLoan.insert(salesLoan);
        // }
    }

    /**
     * <h3>生成serviceImpl delete方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void generateServiceImplDeleteMethod() throws ClassNotFoundException {
        // 生成deleteData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, this.codeModel.VOID, "deleteData");
        // 生成参数 BaseModel、delIds
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(CodeModelUtil.string, "delIds");
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        // 判断如果为空
        JConditional ifCondition = methodBody._if(typePram.eq(JExpr.ref("null")).cor(JExpr.lit("")
            .invoke("equals").arg(typePram)));
        ifCondition._then()._throw(JExpr._new(CodeModelUtil.businessException).arg("请选择删除的记录"));
        methodBody.invoke(JExpr._new(pojoType), "deleteInPkValue_isdeleted").arg(typePram);
        // 生成注释
        this.generateMehotdJavaDoc(method, "删除" + this.moduleName, "");
        // 生成注解
        method.annotate(BaseAnnotation.override);

        // 生成方法如下：
        // @Override
        // public void deleteData(BaseModel baseModel, String delIds) throws Exception {
        //     if (delIds == null || "".equals(delIds)) {
        //         throw new BusinessException("请选择删除的记录");
        //     }
        //     new SalesLoan().deleteInPkValue_isdeleted(delIds);
        // }
    }


    /**
     * <h3>生成serviceImpl update方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void generateServiceImplUpdateMethod() throws Exception {
        // 生成deleteData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, this.codeModel.VOID, "updateData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(typePram, "update").arg(typePram);
        // 生成注释
        this.generateMehotdJavaDoc(method, "修改" + this.moduleName, "");
        // 生成注解
        method.annotate(BaseAnnotation.override);

        // @Override
        // public void updateData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     salesLoan.update(salesLoan);
        // }
    }

    /**
     * <h3>生成serviceImpl findPageData方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void generateServiceImplFindPageDataMethod() throws Exception {
        // 生成findPageData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, this.codeModel.VOID, "findPageData");
        // 生成参数 BaseModel、delIdSSs
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        // methodBody.invoke(baseModelParam, "countSave").arg(typePram.invoke(getCountPageDataMethodName()));
        methodBody.invoke(baseModelParam, "countSave")
            .arg(JExpr.ref(CharUtil.stringBeginCharToLower(getPropertyDaoName()))
                .invoke(CharUtil.stringBeginCharToLower("count" + this.pojoType.name() + "Vo")));
        // 声明变量
        JVar list = methodBody.decl(CodeModelUtil.list, "list");
        list.init(JExpr.ref(CharUtil.stringBeginCharToLower(getPropertyDaoName()))
            .invoke(CharUtil.stringBeginCharToLower("findPage" + this.pojoType.name() + "Vo")).arg(baseModelParam.invoke("getQueryParams")));
        // methodBody.assign(list, methodBody.invoke(JExpr.ref(CharUtil.stringBeginCharToLower(getPropertyDaoName()))
        //         , getFindPageDataMethodName()).arg(baseModelParam.invoke("getQueryParams")));
        methodBody.invoke(baseModelParam, "setData").arg(list);
        // 生成注释
        this.generateMehotdJavaDoc(method, "分页查询" + this.moduleName, "");
        // 生成注解
        method.annotate(BaseAnnotation.override);

        // @Override
        // public void findPageData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     baseModel.countSave(salesLoanDao.countSalesLoanVo(baseModel.getQueryParams()));
        //     List<SalesLoanVo> list = salesLoanDao.findPageSalesLoanVo(baseModel.getQueryParams());
        //     baseModel.setData(list);
        // }
    }

    /**
     * <h3>生成serviceImpl findByPk方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void generateServiceImplFindByPkMethod() throws Exception {
        // 生成findDataByPk方法
        JMethod method = this.genClass.method(JMod.PUBLIC, this.codeModel.VOID, "findDataByPk");
        // 生成参数 BaseModel、delIds
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(CodeModelUtil.string, "pk");
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        // 判断如果为空
        JConditional ifCondition = methodBody._if(typePram.eq(JExpr.ref("null")).cor(JExpr.lit("")
            .invoke("equals").arg(typePram)));
        ifCondition._then()._throw(JExpr._new(CodeModelUtil.businessException).arg("查询记录主键id不能为空"));
        methodBody.invoke(JExpr._new(pojoType), "deleteInPkValue_isdeleted").arg(typePram);
        // 生成注释
        this.generateMehotdJavaDoc(method, "通过id查询" + this.moduleName, "");
        // 生成注解
        method.annotate(BaseAnnotation.override);

        // @Override
        // public void findDataByPk(BaseModel baseModel, String pk) throws Exception {
        //     if (pk == null || "".equals(pk)) {
        //         throw new BusinessException("查询记录主键id不能为空");
        //     }
        //     baseModel.setData(new SalesLoan().findByPk(pk));
        // }
    }

    public String getFindPageDataMethodName() {
        return "findPageData" + this.genClass.name() + "Vo";
    }

    public String getCountPageDataMethodName() {
        return "findPageData" + this.genClass.name() + "Vo";
    }

    public String getPropertyDaoName() {
        return this.pojoType.name() + "Dao";
    }
}

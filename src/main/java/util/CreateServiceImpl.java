package util;

import com.sun.codemodel.*;

/**
 * @author Crown
 * @ClassName CreateServiceImpl
 * @Description 创建ServiceImpl层
 * @email haocan@foxmail.com
 * @date 2018/7/20
 */
public class CreateServiceImpl extends BaseClass {

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public CreateServiceImpl() {
    }

    /**
     * <h3></h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public CreateServiceImpl(JDefinedClass genClass, JType pojoType) {
        super(genClass, pojoType);
    }

    /**
     * <h3>初始化类</h3>
     *
     * @param [fullName, jType]
     * @return test.util.CreateServiceImpl
     * @author Crown
     * @date 2018/7/21        
     */
    public static CreateServiceImpl initClass(String fullName, JType jType) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC, fullName, ClassType.CLASS);
        // 初始化实例
        CreateServiceImpl createServiceImpl = new CreateServiceImpl(genClass, jType);
        // 生成类注释
        createServiceImpl.createClassJavaDoc();
        // 生成基本方法
        createServiceImpl.createBaseMethod();
        return createServiceImpl;
    }


    /**
     * <h3>生成基础方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20        
     */
    public void createBaseMethod() throws Exception {
        createServiceImplAddMethod();
        createServiceImplDeleteMethod();
        createServiceImplUpdateMethod();
        createServiceImplFindPageDataMethod();
        createServiceImplFindByPkMethod();
    }


    /**
     * <h3>创建serviceImpl add方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void createServiceImplAddMethod() {
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

        // 生成方法如下:
        // @Override
        // public void addData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     salesLoan.insert(salesLoan);
        // }
    }

    /**
     * <h3>创建serviceImpl delete方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void createServiceImplDeleteMethod() throws ClassNotFoundException {
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
     * <h3>创建serviceImpl update方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void createServiceImplUpdateMethod() throws Exception {
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

        // @Override
        // public void updateData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     salesLoan.update(salesLoan);
        // }
    }

    /**
     * <h3>创建serviceImpl findPageData方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void createServiceImplFindPageDataMethod() throws Exception {
        // 生成findPageData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, this.codeModel.VOID, "findPageData");
        // 生成参数 BaseModel、delIdSSs
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(baseModelParam, "countSave").arg(typePram.invoke(getCountPageDataMethodName()));
        // 声明变量
        JVar list = methodBody.decl(CodeModelUtil.list, "list");
        methodBody.assign(list, methodBody.invoke(typePram, getFindPageDataMethodName()).arg(baseModelParam.invoke("getQueryParams")));
        methodBody.invoke(baseModelParam, "setData").arg(list);

        // @Override
        // public void findPageData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     baseModel.countSave(salesLoanDao.countSalesLoanVo(baseModel.getQueryParams()));
        //     List<SalesLoanVo> list = salesLoanDao.findPageSalesLoanVo(baseModel.getQueryParams());
        //     baseModel.setData(list);
        // }
    }

    /**
     * <h3>创建serviceImpl findByPk方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/20
     */
    public void createServiceImplFindByPkMethod() throws Exception {
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

        // @Override
        // public void findDataByPk(BaseModel baseModel, String pk) throws Exception {
        //     if (pk == null || "".equals(pk)) {
        //         throw new BusinessException("查询记录主键id不能为空");
        //     }
        //     baseModel.setData(new SalesLoan().findByPk(pk));
        // }
    }

}

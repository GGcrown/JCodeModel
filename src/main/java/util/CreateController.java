package util;

import com.sun.codemodel.*;

/**
 * @author Crown
 * @ClassName CreateController
 * @Description TODO
 * @email haocan@foxmail.com
 * @date 2018/7/21
 */
public class CreateController extends BaseClass {

    // service 属性
    JFieldVar serviceField;

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public CreateController() {
    }

    /**
     * <h3></h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public CreateController(JDefinedClass genClass, JType pojoType) {
        super(genClass, pojoType);
    }

    /**
     * <h3>初始化类</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21
     */
    public static CreateController initClass(String fullName, JType jType) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC, fullName, ClassType.CLASS);
        // 初始化实例
        CreateController createController = new CreateController(genClass, jType);
        // 生成类注释
        createController.createClassJavaDoc();
        // 生成基本方法
        createController.createBaseMethod();
        return createController;
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
        createProperty();
        createControllerAddMethod();
        createControllerDeleteMethod();
        createControllerUpdateMethod();
        createControllerFindPageDataMethod();
        createControllerFindByPkMethod();
    }

    /**
     * <h3>创建属性</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21
     */
    public void createProperty() throws Exception {
        serviceField = this.genClass.field(JMod.NONE, this.codeModel.parseType(this.genClass.name() + "Service")
                , CharUtil.stringBeginCharToUpper(this.genClass.name() + "Service"));
        serviceField.annotate(CodeModelUtil.autowired);

        // @Autowired
        // SalesLoanService salesLoanService;
    }

    /**
     * <h3>创建add方法</h3></h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void createControllerAddMethod() throws Exception {
        // 生成addData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "addData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "addData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMeesage").arg("添加成功");
        methodBody._return(baseModelParam);

        // @AopLog(module = EnumOperationModule.No601, type = ConstantsCommon.FUN_ADD)
        // @ApiOperation(value = "新增贷款办理情况成功", httpMethod = "POST", response = SalesLoan.class)
        // @RequestMapping(value = "addSalesLoan")
        // public BaseModel addData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        // todo
        //     salesLoan.setCreator_id(findSessionUserInfo().getUser_id());
        //     salesLoan.setCreator(findSessionUserInfo().getStaff_name());
        //     salesLoan.setCreated_time(new Date());
        // todo
        //     salesLoanService.addData(baseModel, salesLoan);
        //     baseModel.setMessage("添加贷款办理情况成功");
        //     return baseModel;
        // }
    }

    /**
     * <h3>创建删除方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void createControllerDeleteMethod() throws Exception {
        // 生成addData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "deleteData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(CodeModelUtil.string, CharUtil.stringBeginCharToLower("delIds"));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "deleteData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMeesage").arg("删除成功");
        methodBody._return(baseModelParam);

        // @AopLog(module = EnumOperationModule.No601, type = ConstantsCommon.FUN_DELETE)
        // @ApiOperation(value = "删除贷款办理情况成功", httpMethod = "GET")
        // @RequestMapping("deleteSalesLoan/{delIds}")
        // public BaseModel deleteData(BaseModel baseModel, @PathVariable("delIds") String delIds) throws Exception {
        //     salesLoanService.deleteData(baseModel, delIds);
        //     baseModel.setMessage("删除贷款办理情况成功");
        //     return baseModel;
        // }
    }

    /**
     * <h3>创建更新方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void createControllerUpdateMethod() throws Exception {
        // 生成update方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "addData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "updateData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMeesage").arg("修改成功");
        methodBody._return(baseModelParam);

        // @AopLog(module = EnumOperationModule.No601, type = ConstantsCommon.FUN_SEARCH)
        // @ApiOperation(value = "修改贷款办理情况成功", httpMethod = "POST", response = SalesLoan.class)
        // @RequestMapping(value = "updateSalesLoan")
        // public BaseModel updateData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        // todo
        //     salesLoan.setModifier(findSessionUserInfo().getStaff_name());
        //     salesLoan.setModified_time(new Date());
        // todo
        //     salesLoanService.updateData(baseModel, salesLoan);
        //     baseModel.setMessage("修改贷款办理情况成功");
        //     return baseModel;
        // }
    }

    /**
     * <h3>创建分页查询方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void createControllerFindPageDataMethod() throws Exception {
        // 生成findPageData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "findPageData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "findPageData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMeesage").arg("查询成功");
        methodBody._return(baseModelParam);

        // @AopLog(module = EnumOperationModule.No601, type = ConstantsCommon.FUN_SEARCH, saveLog = false)
        // @ApiOperation(value = "分页查询贷款办理情况成功", httpMethod = "POST", response = SalesLoan.class)
        // @RequestMapping(value = "findPageSalesLoan")
        // public BaseModel findPageData(BaseModel baseModel, SalesLoan salesLoan) throws Exception {
        //     baseModel.setData(findSessionUserInfo());
        //     salesLoanService.findPageData(baseModel, salesLoan);
        //     return baseModel;
        // }
    }

    /**
     * <h3>创建通过主键id查找的方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21
     */
    public void createControllerFindByPkMethod() throws Exception {
        // 生成finByPk方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "findDataByPk");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.pojoType, CharUtil.stringBeginCharToLower(this.pojoType.name()));
        typePram.annotate(CodeModelUtil.pathVariable).param("value","pk");
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "findDataByPk").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMeesage").arg("查询成功");
        methodBody._return(baseModelParam);

        // @AopLog(module = EnumOperationModule.No601, type = ConstantsCommon.FUN_SEARCH, saveLog = false)
        // @ApiOperation(value = "根据主键id查询记录", httpMethod = "GET")
        // @RequestMapping("findSalesLoanByPk/{pk}")
        // public BaseModel findDataByPk(BaseModel baseModel, @PathVariable("pk") String pk) throws Exception {
        //     salesLoanService.findDataByPk(baseModel, pk);
        //     return baseModel;
        // }
    }

}

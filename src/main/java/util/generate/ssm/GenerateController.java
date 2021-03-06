package util.generate.ssm;

import com.sun.codemodel.*;
import util.base.*;

/**
 * @author Crown
 * @ClassName GenerateController
 * @Description 生成Controller层
 * @email haocan@foxmail.com
 * @date 2018/7/21
 */
public class GenerateController extends BaseClass {

    // service 属性
    private JFieldVar serviceField;
    // 组件编号
    private String aopModule;

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date 2018/7/21
     */
    public GenerateController() {
    }

    /**
     * <h3></h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date 2018/7/21
     */
    public GenerateController(JDefinedClass genClass, JType pojoType) {
        super(genClass, pojoType);
    }

    /**
     * <h3>初始化类</h3>
     *
     * @param [jType, aopModule]
     * @return util.generate.ssm.GenerateController
     * @author Crown
     * @date 2018/7/26
     */
    public static GenerateController initClass(JType jType, String aopModule) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC,
                CodeModelUtil.getBasePackage() + ".controller." + jType.name() + "Controller", ClassType.CLASS);
        // 初始化实例
        GenerateController generateController = new GenerateController(genClass, jType);
        generateController.setAopModule(aopModule);
        // 继承BaseController
        generateController.getGenClass()._extends(CodeModelUtil.baseController);
        // 生成类注释
        generateController.generateClassJavaDoc(generateController.getModuleName() + "控制层");
        // 生成类注解
        BaseAnnotation baseAnnotation = new BaseAnnotation();
        baseAnnotation.generateControllerAnnotation(generateController.getGenClass(), generateController.pojoType.name());


        // 生成基本方法
        generateController.generateBaseMethod();
        return generateController;
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
        generateControllerAddMethod();
        generateControllerDeleteMethod();
        generateControllerUpdateMethod();
        generateControllerFindPageDataMethod();
        generateControllerFindByPkMethod();
    }

    /**
     * <h3>生成属性</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21
     */
    public void generateProperty() throws Exception {
        serviceField = this.genClass.field(JMod.NONE, this.codeModel.parseType(this.pojoType.name() + "Service")
                , CharUtil.stringBeginCharToLower(this.pojoType.name() + "Service"));
        serviceField.annotate(BaseAnnotation.autowired);

        // @Autowired
        // SalesLoanService salesLoanService;
    }

    /**
     * <h3>生成add方法</h3></h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void generateControllerAddMethod() throws Exception {
        // 生成addData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "addData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.codeModel.parseType(this.pojoType.name()), CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "addData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMessage").arg("添加成功");
        methodBody._return(baseModelParam);
        // 生成注解
        BaseAnnotation baseAnnotation = new BaseAnnotation(method, pojoType);
        baseAnnotation.generateBaseAnnottation(this.aopModule, CodeModelConstants.FUN_ADD, false
                , "添加" + this.moduleName, "POST"
                , "add" + this.pojoType.name());
        // 生成注释
        this.generateMehotdJavaDoc(method, "添加" + this.moduleName, baseModelParam.name());

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
     * <h3>生成删除方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void generateControllerDeleteMethod() throws Exception {
        // 生成deleteData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "deleteData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(CodeModelUtil.string, CharUtil.stringBeginCharToLower("delIds"));
        typePram.annotate(BaseAnnotation.pathVariable).param("value", "delIds");
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "deleteData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMessage").arg("删除成功");
        methodBody._return(baseModelParam);
        // 生成注解
        BaseAnnotation baseAnnotation = new BaseAnnotation(method, pojoType);
        baseAnnotation.generateBaseAnnottation(this.aopModule, CodeModelConstants.FUN_DELETE, false
                , "删除" + this.moduleName + "信息", "POST"
                , "delete" + this.pojoType.name() + "/{delIds}");
        // 生成注释
        this.generateMehotdJavaDoc(method, "删除" + this.moduleName + "成功", baseModelParam.name());

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
     * <h3>生成更新方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void generateControllerUpdateMethod() throws Exception {
        // 生成update方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "updateData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.codeModel.parseType(this.pojoType.name()), CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "updateData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMessage").arg("修改成功");
        methodBody._return(baseModelParam);
        // 生成注解
        BaseAnnotation baseAnnotation = new BaseAnnotation(method, pojoType);
        baseAnnotation.generateBaseAnnottation(this.aopModule, CodeModelConstants.FUN_UPDATE, false
                , "更新" + this.moduleName, "POST"
                , "update" + this.pojoType.name());
        // 生成注释
        this.generateMehotdJavaDoc(method, "更新" + this.moduleName, baseModelParam.name());

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
     * <h3>生成分页查询方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21        
     */
    public void generateControllerFindPageDataMethod() throws Exception {
        // 生成findPageData方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "findPageData");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(this.codeModel.parseType(this.pojoType.name()), CharUtil.stringBeginCharToLower(this.pojoType.name()));
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "findPageData").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMessage").arg("查询成功");
        methodBody._return(baseModelParam);
        // 生成注解
        BaseAnnotation baseAnnotation = new BaseAnnotation(method, pojoType);
        baseAnnotation.generateBaseAnnottation(this.aopModule, CodeModelConstants.FUN_SEARCH, false
                , "分页查询" + this.moduleName, "POST"
                , "findPage" + this.pojoType.name());
        // 生成注释
        this.generateMehotdJavaDoc(method, "分页查询" + this.moduleName, baseModelParam.name());

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
     * <h3>生成通过主键id查找的方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/21
     */
    public void generateControllerFindByPkMethod() throws Exception {
        // 生成finByPk方法
        JMethod method = this.genClass.method(JMod.PUBLIC, CodeModelUtil.baseModel, "findDataByPk");
        // 生成参数 BaseModel、模块对象
        JVar baseModelParam = method.param(CodeModelUtil.baseModel, CharUtil.stringBeginCharToLower(CodeModelUtil.baseModel.name()));
        JVar typePram = method.param(CodeModelUtil.string, "pk");
        typePram.annotate(BaseAnnotation.pathVariable).param("value", "pk");
        // 抛出异常
        method._throws(CodeModelUtil.exception);
        // 方法体
        JBlock methodBody = method.body();
        methodBody.invoke(this.serviceField, "findDataByPk").arg(baseModelParam).arg(typePram);
        methodBody.invoke(baseModelParam, "setMessage").arg("查询成功");
        methodBody._return(baseModelParam);
        // 生成注解
        BaseAnnotation baseAnnotation = new BaseAnnotation(method, pojoType);
        baseAnnotation.generateBaseAnnottation(this.aopModule, CodeModelConstants.FUN_SEARCH, false
                , "根据" + this.moduleName + "id查询" + this.moduleName, "POST"
                , "find" + this.pojoType.name() + "ByPk/{pk}");
        // 生成注释
        this.generateMehotdJavaDoc(method, "根据" + this.moduleName + "id查询" + this.moduleName, baseModelParam.name());

        // @AopLog(module = EnumOperationModule.No601, type = ConstantsCommon.FUN_SEARCH, saveLog = false)
        // @ApiOperation(value = "根据主键id查询记录", httpMethod = "GET")
        // @RequestMapping("findSalesLoanByPk/{pk}")
        // public BaseModel findDataByPk(BaseModel baseModel, @PathVariable("pk") String pk) throws Exception {
        //     salesLoanService.findDataByPk(baseModel, pk);
        //     return baseModel;
        // }
    }

    public JFieldVar getServiceField() {
        return serviceField;
    }

    public void setServiceField(JFieldVar serviceField) {
        this.serviceField = serviceField;
    }

    public String getAopModule() {
        return aopModule;
    }

    public void setAopModule(String aopModule) {
        this.aopModule = aopModule;
    }
}

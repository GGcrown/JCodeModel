package util.base;

import com.sun.codemodel.*;
import com.sun.org.apache.bcel.internal.util.Objects;


/**
 * @author Crown
 * @ClassName BaseAnnotation
 * @Description 基础注解类
 * @email haocan@foxmail.com
 * @date 2018/7/23
 */
public class BaseAnnotation {

    // 方法
    public static JMethod method;
    // Aoplog 切面日志类
    public static JClass aopLog;
    // ApiOperation 接口文档注释类
    public static JClass apiOperation;
    // RequestMapping 映射请求路径
    public static JClass requestMapping;
    // EnumOperationModule 模块枚举类
    public static JClass enumOperationModule;
    // ConstantsCommon 基本常量类
    public static JClass constantsCommon;
    // 模块类型
    private JType pojoType;
    // Controoler注解类
    public static JClass controller;
    // 返回体
    public static JClass responseBody;
    // Overrride 重写注解
    public static JClass override;
    // ModelDao<> po继承
    public static JClass modelDao;
    // ApiModel 文档模型
    public static JClass apiModel;
    // ModelBind 模块绑定
    public static JClass modelBind;
    // ApiModelProperty swagger属性文档
    public static JClass apiModelProperty;
    // Service 服务层注解
    public static JClass service;


    static {
        aopLog = CodeModelUtil.codeModel.ref("AopLog");
        apiOperation = CodeModelUtil.codeModel.ref("ApiOperation");
        requestMapping = CodeModelUtil.codeModel.ref("RequestMapping");
        enumOperationModule = CodeModelUtil.codeModel.ref("EnumOperationModule");
        constantsCommon = CodeModelUtil.codeModel.ref("ConstantsCommon");
        controller = CodeModelUtil.codeModel.ref("org.springframework.stereotype.Controller");
        responseBody = CodeModelUtil.codeModel.ref("org.springframework.web.bind.annotation.ResponseBody");
        override = CodeModelUtil.codeModel.ref("java.lang.annotation.Override");
        modelDao = CodeModelUtil.codeModel.ref("org.zsc.base.annotation.ModelDao");
        apiModel = CodeModelUtil.codeModel.ref("com.wordnik.swagger.annotations.ApiModel");
        modelBind = CodeModelUtil.codeModel.ref("org.zsc.base.annotation.ModelBind");
        apiModelProperty = CodeModelUtil.codeModel.ref("com.wordnik.swagger.annotations.ApiModelProperty");
        service = CodeModelUtil.codeModel.ref("org.springframework.stereotype.Service");
    }

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public BaseAnnotation() {
        // 初始化类
/*        aopLog = CodeModelUtil.codeModel.ref("AopLog");
        apiOperation = CodeModelUtil.codeModel.ref("ApiOperation");
        requestMapping = CodeModelUtil.codeModel.ref("RequestMapping");
        enumOperationModule = CodeModelUtil.codeModel.ref("EnumOperationModule");
        constantsCommon = CodeModelUtil.codeModel.ref("ConstantsCommon");
        controller = CodeModelUtil.codeModel.ref("org.zsc.common.controller.BaseController");*/
    }

    /**
     * <h3></h3>
     *
     * @param [method, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public BaseAnnotation(JMethod method, JType pojoType) {
        this();
        BaseAnnotation.method = method;
        this.pojoType = pojoType;
    }

    /**
     * <h3>生成基础方法</h3>
     *
     * @param aopLogModule Aop日志模块编号
     * @param aopLogType Aop日志类型
     * @param aopLogSaveLog 是否保存日志
     * @param apiOperationValue swagger手册操作内容
     * @param apiOperationHttpMethod 请求类型
     * @param requestMappingValue 方法映射路径
     * @return void
     * @author Crown
     * @date 2018/7/24        
     */
    public void generateBaseAnnottation(String aopLogModule, String aopLogType, boolean aopLogSaveLog
            , String apiOperationValue, String apiOperationHttpMethod, String requestMappingValue) {
        generateAopLog(aopLogModule, aopLogType, aopLogSaveLog);
        generateApiOperation(apiOperationValue, apiOperationHttpMethod);
        generateRequestMapping(requestMappingValue);
    }

    /**
     * <h3>生成AopLog注解</h3>
     *
     * @param [aopLogModule Aop日志模块编号, aopLogType Aop日志类型, aopLogSaveLog 是否保存日志]
     * @return void
     * @author Crown
     * @date 2018/7/23
     */
    public void generateAopLog(String aopLogModule, String aopLogType, boolean aopLogSaveLog) {
        if (aopLogModule == null || Objects.equals(aopLogModule, "")) {
            aopLogModule = CodeModelConstants.TODO;
        }
        if (aopLogType == null || Objects.equals(aopLogType, "")) {
            aopLogType = CodeModelConstants.TODO;
        }
        // 生成注解
        JAnnotationUse annotation = method.annotate(aopLog);
        annotation.param("module", enumOperationModule.staticRef(aopLogModule));
        annotation.param("type", constantsCommon.staticRef(aopLogType));
        annotation.param("saveLog", aopLogSaveLog);
    }


    /**
     * <h3>生成ApiOperatioin注解</h3>
     *
     * @param [apiOperationValue swagger手册操作内容, apiOperationHttpMethod 请求类型]
     * @return void
     * @author Crown
     * @date 2018/7/24        
     */
    public void generateApiOperation(String apiOperationValue, String apiOperationHttpMethod) {
        if (apiOperationValue == null || Objects.equals(apiOperationValue, "")) {
            apiOperationValue = CodeModelConstants.TODO;
        }
        if (apiOperationHttpMethod == null || Objects.equals(apiOperationHttpMethod, "")) {
            apiOperationHttpMethod = "POST";
        }

        // 生成注解
        JAnnotationUse annotation = method.annotate(apiOperation);
        annotation.param("value", apiOperationValue);
        annotation.param("httpMethod", apiOperationHttpMethod);
        annotation.param("response", this.pojoType.getClass());
    }


    /**
     * <h3>生成RequestMapping注解</h3>
     *
     * @param [requestMappingValue 方法映射路径]
     * @return void
     * @author Crown
     * @date 2018/7/24        
     */
    public void generateRequestMapping(String requestMappingValue) {
        if (requestMappingValue == null || Objects.equals(requestMappingValue, "")) {
            requestMappingValue = CodeModelConstants.TODO;
        }
        JAnnotationUse annotation = method.annotate(requestMapping);
        annotation.param("value", requestMappingValue);
    }

    /**
     * <h3>生成控制层注解</h3>
     * 生成三个注解 @Controller、@ResponseBody、@RequestMapping
     *
     * @param [genClass, mapping]
     * @return void
     * @author Crown
     * @date 2018/7/26        
     */
    public void generateControllerAnnotation(JDefinedClass genClass, String mapping) {
        if (mapping == null || Objects.equals(mapping, "")) {
            mapping = CodeModelConstants.TODO;
        }
        genClass.annotate(controller);
        genClass.annotate(responseBody);
        genClass.annotate(requestMapping).param("value", mapping);
    }


}

package util;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
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
    private JMethod method;
    // Aoplog 切面日志类
    private JClass aopLog;
    // ApiOperation 接口文档注释类
    private JClass apiOperation;
    // RequestMapping 映射请求路径
    private JClass requestMapping;
    // EnumOperationModule 模块枚举类
    private JClass enumOperationModule;
    // ConstantsCommon 基本常量类
    private JClass constantsCommon;
    // 模块类型
    private JType pojoType;

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
        // aopLog = CodeModelUtil.codeModel.ref("org.zsc.common.aop.AopLog");
        // apiOperation = CodeModelUtil.codeModel.ref("com.wordnik.swagger.annotations.ApiOperation");
        // requestMapping = CodeModelUtil.codeModel.ref("org.springframework.web.bind.annotation.RequestMapping");
        // enumOperationModule = CodeModelUtil.codeModel.ref("org.zsc.common.em.EnumOperationModule");
        // constantsCommon = CodeModelUtil.codeModel.ref("org.zsc.common.constants.ConstantsCommon");
        aopLog = CodeModelUtil.codeModel.ref("AopLog");
        apiOperation = CodeModelUtil.codeModel.ref("ApiOperation");
        requestMapping = CodeModelUtil.codeModel.ref("RequestMapping");
        enumOperationModule = CodeModelUtil.codeModel.ref("EnumOperationModule");
        constantsCommon = CodeModelUtil.codeModel.ref("ConstantsCommon");
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
        this.method = method;
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
        // 创建注解
        JAnnotationUse annotation = this.method.annotate(this.aopLog);
        annotation.param("module", this.enumOperationModule.staticRef(aopLogModule));
        annotation.param("type", this.constantsCommon.staticRef(aopLogType));
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

        // 创建注解
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
        JAnnotationUse annotation = this.method.annotate(requestMapping);
        annotation.param("value", requestMappingValue);
    }

}

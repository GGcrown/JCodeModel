package util.generate.ssm;

import com.sun.codemodel.*;
import util.base.BaseAnnotation;
import util.base.BaseClass;
import util.base.CodeModelConstants;
import util.base.CodeModelUtil;

import java.lang.reflect.Field;

/**
 * @author Crown
 * @className GeneratePo
 * @description 生成实体对象
 * @email haocan@foxmail.com
 * @date 2018/7/26
 */
public class GeneratePo extends BaseClass {
    // 需要重构的类
    Class aClass;

    public GeneratePo() {
    }

    /**
     * <h3>无参构造函数</h3>
     *
     * @param [aClass]
     * @return
     * @author Crown
     * @date
     */
    public GeneratePo(Class aClass) {
        this.aClass = aClass;
    }

    /**
     * <h3></h3>
     *
     * @param [genClass, pojoType, aClass]
     * @return
     * @author Crown
     * @date
     */
    public GeneratePo(JDefinedClass genClass, JType pojoType, Class aClass) {
        super(genClass, pojoType);
        this.aClass = aClass;
    }

    /**
     * <h3>构造函数</h3>
     *
     * @param [genClass, pojoType, aClass]
     * @return
     * @author Crown
     * @date
     */
    public GeneratePo(JDefinedClass genClass, JType pojoType, String moduleName, Class aClass) {
        super(genClass, pojoType, moduleName);
        this.aClass = aClass;
    }

    public static GeneratePo iniClass(Class aClass) throws Exception {
        // 生成类
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC,
                CodeModelUtil.getBasePackage() + ".po." + aClass.getSimpleName(), ClassType.CLASS);
        GeneratePo generatePo = new GeneratePo(genClass, CodeModelUtil.codeModel.ref(""), aClass);
        // 生成类注释
        generatePo.generateClassJavaDoc(generatePo.getModuleName() + "表");
        // 生成注解
        genClass.annotate(BaseAnnotation.apiModel).param("value", genClass.name());
        // 此注解的参数为todo 需要手动重写
        genClass.annotate(BaseAnnotation.modelBind).param("table", CodeModelConstants.TODO);
        // 继承
        genClass._extends(CodeModelUtil.codeModel.ref("ModelDao<" + genClass.name() + ">"));
        // 生成基础方法
        generatePo.generateBaseMethod();
        return generatePo;
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
    }

    /**
     * <h3>生成属性</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/26        
     */
    public void generateProperty() throws Exception {
        // 通过反射获得属性
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            // 生成属性和Get Set方法
            JFieldVar genField = generateProperties(CodeModelUtil.codeModel.ref(fields[i].getType().getSimpleName()), fields[i].getName());
            String apiModelPropertyValue = i == 0 ? CodeModelConstants.TODO + " stay add ApiModelProperty Annotation" : CodeModelConstants.TODO;
            genField.annotate(BaseAnnotation.apiModelProperty).param("value", apiModelPropertyValue);
        }


    }
}

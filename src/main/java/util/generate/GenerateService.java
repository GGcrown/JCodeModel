package util.generate;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import util.base.BaseClass;
import util.base.CodeModelUtil;

/**
 * @author Crown
 * @ClassName GenerateService
 * @Description 生成service层
 * @email haocan@foxmail.com
 * @date 2018/7/19
 */
public class GenerateService extends BaseClass<GenerateService> {

    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public GenerateService() {
    }

    /**
     * <h3>两3参构造函数</h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     */
    public GenerateService(JDefinedClass genClass, JType pojoType) {
        super(genClass, pojoType);
    }

    /**
     * <h3>初始化Service类</h3>
     *
     * @param [fullName 类的全路径名称, jType 类的类型]
     * @return test.util.generate.GenerateService
     * @author Crown
     * @date 2018/7/19
     */
    public static GenerateService initServiceClass(String fullName, JType jType) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC, fullName, ClassType.INTERFACE);
        // 初始化实例
        GenerateService generateService = new GenerateService(genClass, jType);
        // 生成类注释
        generateService.generateClassJavaDoc();
        // 生成基本方法
        generateService.generateBaseMethod();
        return generateService;
    }

    /**
     * <h3>生成基本的五个方法</h3>
     * 包括：addData,deleteData,updateData,findPageData,findDataByPk
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateBaseMethod() throws Exception {
        generateServiceAddMethod();
        generateServiceDeleteMethod();
        generateServiceUpdateMethod();
        generateServiceFindPageMethod();
        generateServiceFindByPkMethod();
    }

    /**
     * <h3>新增方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateServiceAddMethod() throws Exception {
        generateAbstractMethod("addData");
    }

    /**
     * <h3>生成删除方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateServiceDeleteMethod() throws Exception {
        generateAbstractMethod("deleteData", CodeModelUtil.string, "delIds");
    }

    /**
     * <h3>生成修改方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateServiceUpdateMethod() throws Exception {
        generateAbstractMethod("updateData");
    }

    /**
     * <h3>生成分页查询方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateServiceFindPageMethod() throws Exception {
        generateAbstractMethod("findPageData");
    }

    /**
     * <h3>生成通过主键查询的方法</h3>
     *
     * @param []
     * @return void
     * @author Crown
     * @date 2018/7/19
     */
    public void generateServiceFindByPkMethod() throws Exception {
        generateAbstractMethod("findDataByPk", CodeModelUtil.string, "pk");
    }


}

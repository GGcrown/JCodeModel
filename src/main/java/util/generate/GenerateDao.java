package util.generate;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import util.base.BaseClass;
import util.base.CodeModelUtil;

/**
 * @author Crown
 * @ClassName GenerateDao
 * @Description
 * @email haocan@foxmail.com
 * @date 2018/7/21
 */
public class GenerateDao extends BaseClass {


    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public GenerateDao() {
    }

    /**
     * <h3>两参构造函数</h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public GenerateDao(JDefinedClass genClass, JType pojoType) {
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
    public static GenerateDao initClass(String fullName, JType jType) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC, fullName, ClassType.INTERFACE);
        // 初始化实例
        GenerateDao generateDao = new GenerateDao(genClass,jType);
        // 生成类注释
        generateDao.generateClassJavaDoc();
        return generateDao;
    }

}

package util;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

/**
 * @author Crown
 * @ClassName CreateDao
 * @Description
 * @email haocan@foxmail.com
 * @date 2018/7/21
 */
public class CreateDao extends BaseClass {


    /**
     * <h3>无参构造函数</h3>
     *
     * @param []
     * @return
     * @author Crown
     * @date
     */
    public CreateDao() {
    }

    /**
     * <h3>两参构造函数</h3>
     *
     * @param [genClass, pojoType]
     * @return
     * @author Crown
     * @date
     */
    public CreateDao(JDefinedClass genClass, JType pojoType) {
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
    public static CreateDao initClass(String fullName, JType jType) throws Exception {
        JDefinedClass genClass = CodeModelUtil.codeModel._class(JMod.PUBLIC, fullName, ClassType.INTERFACE);
        // 初始化实例
        CreateDao createDao = new CreateDao(genClass,jType);
        // 生成类注释
        createDao.generateClassJavaDoc();
        return createDao;
    }




















}

package test;

import org.junit.Test;
import util.generate.ssm.GenerateSSM;
import wang.crown9527.test.po.SysUser;

/**
 * @author Crown
 * @className SSMTest
 * @description SSM测试类
 * @email haocan@foxmail.com
 * @date 2018/7/27
 */
public class SSMTest {


    @Test
    public void generatorMybatis() throws Exception {
        // 先确认generatorConfig.xml表配置是否正确，然后再运行此方法
        // mybatis逆向工程 生成po和mapper
        GenerateSSM.generatorMyBatis();
    }

    @Test
    public void generatorSingleModuleTest() throws Exception {
        GenerateSSM.generatorSingleModule(SysUser.class, "用户", "No909");
    }
}

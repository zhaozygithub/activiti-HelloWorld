import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class CreateTable {
    private static final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void TestDoTask() {
        processEngine.getTaskService().complete("7504");//完成任务
    }

    @Test
    public void TestQuery() {
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("lisi").list();//查询某个人的任务列表
        for (Task task : taskList) {
            System.out.println(task);//Task[id=7504, name=UserTask1]
        }
    }


    @Test
    public void TestStart() {
        ProcessInstance process1 = processEngine.getRuntimeService()
                .startProcessInstanceByKey("myProcess_1");//启动某一个实例
        System.out.println(process1.getId());
        System.out.println(process1.getName());
        System.out.println(process1.getProcessDefinitionId());
    }


    @Test
    public void TestDeploy() {
        processEngine.getRepositoryService()//创建流程定义
                .createDeployment()
                .addClasspathResource("HelloWorld.bpmn")
                .deploy();
    }


    @Test
    public void TestCreateTable2() {
        // 引擎配置
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        // 获取流程引擎对象
        ProcessEngine processEngine = pec.buildProcessEngine();
    }

    @Test
    public void TestCreateTable() {
        // 引擎配置
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        pec.setJdbcDriver("com.mysql.jdbc.Driver");
        pec.setJdbcUrl("jdbc:mysql://localhost:3306/db_activiti?useSSL=false&characterEncoding=utf8");
        pec.setJdbcUsername("root");
        pec.setJdbcPassword("zhao");

        /**
         * DB_SCHEMA_UPDATE_FALSE 不能自动创建表，需要表存在
         * create-drop 先删除表再创建表
         * DB_SCHEMA_UPDATE_TRUE 如何表不存在，自动创建和更新表
         */
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 获取流程引擎对象
        ProcessEngine processEngine = pec.buildProcessEngine();
    }
}

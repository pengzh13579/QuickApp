package cn.pzh.system.web.project.common.conf.schedule;

import org.quartz.*;

public class GetInformationByPython implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("开始运行：" + jobExecutionContext.getTrigger().getJobKey().getName());
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobMessage = jobDataMap.getString("parameter");
        System.out.println("执行内容：" + jobMessage);
    }
}

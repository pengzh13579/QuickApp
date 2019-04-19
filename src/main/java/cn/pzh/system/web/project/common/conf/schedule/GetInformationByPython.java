package cn.pzh.system.web.project.common.conf.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class GetInformationByPython implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("开始运行" + jobExecutionContext.getTrigger().getJobKey().getName());
    }
}

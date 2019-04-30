package cn.pzh.system.web.project.common.conf.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class DynamicScheduleTask {

    /**
     * 添加定时任务
     * @param jobName
     * @param cronExpression
     * @param parameter
     */
    public static void schedulerAdd(String jobName, String cronExpression, String parameter)
            throws SchedulerException {

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // 启动调度器
            scheduler.start();

            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(GetInformationByPython.class).withIdentity(jobName).usingJobData("parameter", parameter).build();

            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 删除定时任务
     * @param jobClassName
     */
    public static void schedulerDelete(String jobClassName) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName));
    }
}
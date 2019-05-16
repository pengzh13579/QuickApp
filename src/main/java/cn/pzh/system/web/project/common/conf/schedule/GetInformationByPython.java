package cn.pzh.system.web.project.common.conf.schedule;

import org.quartz.*;
import java.io.*;

public class GetInformationByPython implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("开始运行：" + jobExecutionContext.getTrigger().getJobKey().getName());
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String parameter = jobDataMap.getString("parameter");
        try {
            Process proc = Runtime.getRuntime().exec(parameter);// 执行py文件
            proc.waitFor();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行内容：" + parameter);
    }

    public static void main(String args[]) {
        try {
            Process proc = Runtime.getRuntime().exec("cmd /c start e:xxx.bat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

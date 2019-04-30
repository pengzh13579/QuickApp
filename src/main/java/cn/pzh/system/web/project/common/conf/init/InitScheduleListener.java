package cn.pzh.system.web.project.common.conf.init;

import cn.pzh.system.web.project.business.sys.service.SystemScheduleService;
import cn.pzh.system.web.project.common.conf.schedule.DynamicScheduleTask;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemScheduleEntity;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;

@Service
public class InitScheduleListener implements InitializingBean, ServletContextAware {

    @Resource
    private SystemScheduleService scheduleService;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        SystemScheduleEntity entity = new SystemScheduleEntity();
        entity.setDisFlag(0);
        List<SystemScheduleEntity> scheduleList = scheduleService.listSchedules(entity);
        for (SystemScheduleEntity info : scheduleList) {
            try {
                DynamicScheduleTask.schedulerAdd(info.getScheduleName(), info.getScheduleCron(), info.getScheduleParam());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}

//package vip.xiaonuo.main.job;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @Description: job测试
// * @author 邾茂星
// * @date 2022/3/1 13:46
// */
//@Component
//@Slf4j
//@Transactional(rollbackFor = Exception.class)
//public class TestJob {
//
//    @XxlJob("demoJob")
//    public void demoJob() {
//        log.info("=======job======");
//        XxlJobHelper.log("=======job======");
//    }
//}

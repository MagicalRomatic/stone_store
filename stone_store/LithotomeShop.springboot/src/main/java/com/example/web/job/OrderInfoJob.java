package com.example.web.job;

import com.example.web.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderInfoJob {
    @Autowired()
    private OrderInfoService OrderService;
    /**
     * 每隔5s查询支付状态
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void AutoCheckAliPayCallBack() {
        long nowDateTime = System.currentTimeMillis();
        System.out.println("固定定时任务执行:--->"+nowDateTime+"，任务名称:每隔5s查询支付状态");
        OrderService.CheckAliPayCallBack();
    }
    /**
     * 每隔5s查询是否订单逾期了
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void CheckExpire() {
        long nowDateTime = System.currentTimeMillis();
        System.out.println("固定定时任务执行:--->"+nowDateTime+"，任务名称:每隔5s查询是否订单逾期了");
        OrderService.CheckExpire();
    }

}

package com.sahajamit.k8s.schedules;

import com.sahajamit.k8s.service.GridConsoleService;
import com.sahajamit.k8s.service.PodScalingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class K8sAutoScaleSchedule {
    private static final Logger logger = LoggerFactory.getLogger(K8sAutoScaleSchedule.class);
    @Autowired
    private GridConsoleService service;

    @Autowired
    private PodScalingService podScalingService;

    @Scheduled(fixedDelayString = "${grid_scale_up_check_frequency_in_sec:10}000", initialDelay = 5000)
    public synchronized void checkAndAutoScaleUp() {
        try {
            podScalingService.adjustScaleUp(service.getStatus());
        } catch (Exception e) {
            logger.error("Error in running checkAndAutoScaleUp scheduler: {}", e);
        }
    }

    @Scheduled(fixedDelayString = "${grid_scale_down_check_frequency_in_sec:10}000", initialDelay = 5000)
    public synchronized void checkAndAutoScaleDown() {
        try {
            podScalingService.adjustScaleDown(service.getStatus());
        } catch (Exception e) {
            logger.error("Error in running checkAndAutoScaleDown scheduler: {}", e);
        }

    @Scheduled(cron = "${grid_daily_cleanup_cron}")
    public synchronized void restartAllNodes() {
        try {
            logger.info("This is daily cleanup....");
            podScalingService.cleanUp();
        } catch (Exception e) {
            logger.error("Error in running restartAllNodes scheduler: {}", e);
        }
    }
}

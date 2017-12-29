package com.gr.market.scheduled;

import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTasks {
	@Scheduled(cron = "0 0/1 * * * ?")
	public void executeFileDownLoadTask() {
		System.out.println(new Date());
	}
}

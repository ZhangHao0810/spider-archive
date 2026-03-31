package com.zhanghao.searchboot.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhanghao.searchboot.service.SearchService;

@Component // 想让Spring 去装载的话，如果 不是 Controller ， Dao ， Service 这三种 那么就用 @Component来告诉Spring框架装载这个类。
public class NewsJob {
	private static final Logger LOG = LoggerFactory.getLogger(NewsJob.class);
	@Autowired
	SearchService searchService;

	@Scheduled(cron = "0/3 * * * * ?")
	public void run() {
		LOG.info("===开始爬取内容定时任务===");
		searchService.importNews();
		LOG.info("===结束爬取内容定时任务===");
	}
}

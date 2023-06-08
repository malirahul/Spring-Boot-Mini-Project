package com.example.aop.springaop.concurrectClassess;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingThreadFactory implements ThreadFactory {

	private static final Logger logger = LoggerFactory.getLogger(LoggingThreadFactory.class);
	private final ThreadFactory defaultThreadFactory;

	public LoggingThreadFactory() {
		defaultThreadFactory = Executors.defaultThreadFactory();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = defaultThreadFactory.newThread(r);
		thread.setUncaughtExceptionHandler((t, e) -> {
			logger.error("Uncaught exception in thread: " + t.getName(), e);
		});
		return thread;
	}
}
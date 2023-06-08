package com.example.aop.springaop;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.example.aop.springaop.concurrectClassess.LoggingThreadFactory;


@SpringBootApplication
@EnableCaching
public class SpringAopApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringAopApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringAopApplication.class, args);
		

		// logging level
		// ALL > TRACE > DEBUGG > INFO > WARN > ERROR > FATAL > OF
		log.trace("Trace Log");
		log.debug("Debugging log");
		log.info("Information log");
		log.warn("Warning log");
		log.error("Error Log");

		// Logging Using Thread Factory-----------------------------------

		ThreadFactory threadFactory = new LoggingThreadFactory();
		ExecutorService executor = Executors.newSingleThreadExecutor(threadFactory);

		executor.submit(() -> {

			try {
				// Your task code here
				log.info("ThreadFactory Class ......");
				log.info("Executing task...");
				int result = 1 + 2;
				log.info("Task result: " + result);
			} catch (Exception e) {
				log.error("Error executing task", e);
			}
		});

		// Shutdown the executor when you're done
//		executor.shutdown();

		// Future class
		ExecutorService fexecutor = Executors.newFixedThreadPool(2);

		Callable<Integer> task1 = () -> {
			log.info("Future Class ......");
			log.info("Task 1: Start");
			Thread.sleep(2000);
			log.info("Task 1: End");
			return 1;
		};

		Callable<Integer> task2 = () -> {
			log.info("Task 2: Start");
			Thread.sleep(3000);
			log.info("Task 2: End");
			return 2;
		};

		Future<Integer> future1 = fexecutor.submit(task1);
		Future<Integer> future2 = fexecutor.submit(task2);

		// Perform other operations in the main thread
		log.info("Performing other operations...");

		try {
			// Wait for task1 to complete and retrieve the result
			int result1 = future1.get();
			log.info("Task 1 result: " + result1);

			// Wait for task2 to complete and retrieve the result
			int result2 = future2.get();
			log.info("Task 2 result: " + result2);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		// Shutdown the executor when you're done
		fexecutor.shutdown();
	

	}
}

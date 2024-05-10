//package com.example.creatingcontainer.Service.Impl;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//@EnableScheduling
//@Configuration
//@EnableAsync(proxyTargetClass = true)
//public class AsyncConfiguration {
//
//
//
//        @Bean(name = "asyncTaskExecutor")
//        public ThreadPoolTaskExecutor asyncTaskExecutor() {
//            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//            executor.setCorePoolSize(5); // Set the initial number of threads in the pool
//            executor.setMaxPoolSize(10); // Set the maximum number of threads in the pool
//            executor.setQueueCapacity(100); // Set the queue capacity for holding pending tasks
//            executor.setThreadNamePrefix("AsyncTask-"); // Set a prefix for thread names
//            executor.initialize();
//            return executor;
//
//    }
//}

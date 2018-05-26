package com.serialport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool
{
    
    private ThreadPool()
    {
        
    }
    
    private static ExecutorService executor;
    
    public static ExecutorService getThreadPoolService()
    {
        if (null == executor)
        {
            executor = Executors.newFixedThreadPool(5);
        }
        return executor;
    }
    
    public static void submit(Runnable runner)
    {
        getThreadPoolService().execute(runner);
    }
    
    public static void stop()
    {
    	if(null != executor) 
    	{
    		executor.shutdown();
    	}
    }
    
}

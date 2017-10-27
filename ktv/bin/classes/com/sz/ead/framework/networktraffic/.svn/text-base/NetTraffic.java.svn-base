package com.sz.ead.framework.networktraffic;

public class NetTraffic 
{
	static 
	{
		System.loadLibrary("networktraffic");
	}
	
	public static native void startMonitor();
	public static native void stopMonitor();
	public static native String getSpeed();
	
	public void start() 
	{
		new SpeedThread().start();
	}
	
	public void stop() 
	{
		NetTraffic.stopMonitor();
	}
	
	public class SpeedThread extends Thread 
	{
		public void run() 
		{
			NetTraffic.startMonitor();
		}
	}
}

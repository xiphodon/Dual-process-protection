package com.service.demo;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
/**
 * 服务2
 */
public class Service2 extends Service {
	private String TAG = getClass().getName();
	private String ServiceName = "com.service.demo.Service1";
	private String Process_Name = "com.example.servicetest2:service1";

	private Service_2 service_2 = new Service_2.Stub() {

		@Override
		public void stopService() throws RemoteException {
			Intent i = new Intent(Service2.this, Service1.class);
			Service2.this.stopService(i);
		}

		@Override
		public void startService() throws RemoteException {
			Intent i = new Intent(Service2.this, Service1.class);
			Service2.this.startService(i);
			
		}
	};


	public void onCreate() {
		new Thread() {
			public void run() {
				while (true) {
					//boolean isRun = isServiceRunning(Service2.this,"com.service.demo.Service1");
					boolean isRun = isProessRunning(Service2.this, Process_Name);
					if(isRun==false){
						try {
							Log.i(TAG, "重新启动服务1: "+service_2);
							service_2.startService();
						} catch (RemoteException e) {
							e.printStackTrace();
						}	
					}
				}
			};
		}.start();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return (IBinder) service_2;
	}

	//服务是否运行
	public static boolean isServiceRunning(Context context, String serviceName) {
		
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> lists = am.getRunningServices(30);
		
		for (RunningServiceInfo info : lists) {//判断服务
				if(info.service.getClassName().equals(serviceName)){
				Log.i("Service1进程", ""+info.service.getClassName());
				isRunning = true;
			}
		}
		
		
		return isRunning;
	}
	
	//进程是否运行
	public static boolean isProessRunning(Context context, String proessName) {
		
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
		for(RunningAppProcessInfo info : lists){
			if(info.processName.equals(proessName)){
				//Log.i("Service2进程", ""+info.processName);
				isRunning = true;
			}
		}
		
		return isRunning;
	}

}

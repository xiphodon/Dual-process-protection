package com.service.demo;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
/**
 * 服务1
 */
public class Service1 extends Service {

	private String TAG = getClass().getName();
	//用于判断服务是否运行
	private String ServiceName = "com.service.demo.Service2";
	//用于判断进程是否运行
	private String Process_Name = "com.example.servicetest2:service2";

	private Service_1 service_1 = new Service_1.Stub() {

		@Override
		public void stopService() throws RemoteException {
			Intent i = new Intent(Service1.this, Service2.class);
			Service1.this.stopService(i);
		}

		@Override
		public void startService() throws RemoteException {
			Intent i = new Intent(Service1.this, Service2.class);
			Service1.this.startService(i);
		}
	};

	public void onCreate() {
		new Thread() {
			public void run() {
				while (true) {
					//boolean isRun = isServiceRunning(Service1.this, ServiceName);
					boolean isRun = isProessRunning(Service1.this, Process_Name);
					if (isRun==false) {
						try {
							Log.i(TAG, "重新启动服务2: "+service_1);
							service_1.startService();
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
		return (IBinder) service_1;
	}

	//服务是否运行
	public boolean isServiceRunning(Context context, String serviceName) {
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> lists = am.getRunningServices(30);

		for (RunningServiceInfo info : lists) {// 获取运行服务再启动
			if (info.service.getClassName().equals(serviceName)) {
				Log.i("Service1进程", "" + info.service.getClassName());
				isRunning = true;
			}
		}
		return isRunning;

	}

	// 进程是否运行
	public static boolean isProessRunning(Context context, String proessName) {

		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : lists) {
			if (info.processName.equals(proessName)) {
				//Log.i("Service1进程", "" + info.processName);
				isRunning = true;
			}
		}

		return isRunning;
	}

}

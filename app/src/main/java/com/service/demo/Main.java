package com.service.demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.servicetest2.R;
/**
 * Service双进程守护
 * @author 掌缘生灭
 *
 */
public class Main extends Activity implements OnClickListener {

	private Button btn1,btn2;
	//AIDL,此处用于bindService
	private Service_1 service_1;
	private Service_2 service_2;
	private String TAG = getClass().getName();
	
	//用于bindService
	/*private ServiceConnection conn1 = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(TAG, "onServiceDisconnected");
			service_1=null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "onServiceConnected");
			service_1 = Service_1.Stub.asInterface(service);
		}
	};
	
	private ServiceConnection conn2 = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(TAG, "onServiceDisconnected");
			service_2=null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "onServiceConnected");
			service_2 = Service_2.Stub.asInterface(service);
		}
	};*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	
	}
	
	@Override
	protected void onDestroy() {
		//unbindService(conn1);
		//unbindService(conn2);
		super.onDestroy();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.button1:
			Intent i1 = new Intent(Main.this,Service1.class);
			startService(i1);
			//bindService(i1, conn1, Context.BIND_AUTO_CREATE);
			
			Intent i2 = new Intent(Main.this,Service2.class);
			startService(i2);
			//bindService(i2, conn2, Context.BIND_AUTO_CREATE);
			break;

		case R.id.button2:
			//关闭Activity
			this.finish();
			break;
		}
	}

}

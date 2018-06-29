package com.example.tcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		btn_send = (Button) findViewById(R.id.btn_send);
		
		tv_text = (TextView) findViewById(R.id.tv_text);
			 new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						extracted(tv_text);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			

		
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			tv_text.setText(msg.obj.toString());
		};
	};


	private TextView tv_text;	
	private static int i =0;


	private Button btn_send;
	private void extracted(TextView tv_text) throws UnknownHostException, IOException {
		Socket so=new Socket("192.168.1.137",10002);  
		// ��ȡsocket���е������
				 OutputStream out = so.getOutputStream();
//				btn_send.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						try {
//							out.write(("����"+i++).getBytes());
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}						
//					}
//				});

//				 ʹ���������ָ��������д��ȥ
//				while(true){
//					SystemClock.sleep(500);
//					i++;
//				out.write(("����"+i).getBytes());
//				if(i%2 == 0){
//					out.write(("�෢�Ͳ���").getBytes());
//				}
//				}
				// ��ȡ����˷��ص����ݣ�ʹ��socket��ȡ��
				 InputStream in = so.getInputStream();
				while(true){
				byte[] buf = new byte[1024];
				int len = in.read(buf);
				String text = new String(buf, 0, len);

				Message msg = new Message().obtain();
				msg.obj = text;
				handler.sendMessage(msg);
				}
//				so.close();
	}

}

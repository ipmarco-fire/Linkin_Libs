package com.linkin.window;



import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public abstract class BaseWindow {
	protected static final int MESSAGE_HIDE= 1;  //hide epg list box
	protected static final int DELAY_MILLIS= 5*1000;  //hide epg list box
	protected static final int DELAY_PLAY= 10*1000;  //hide epg list box
	protected Context mContext;
	int width,height;
	protected View parent;
	protected PopupWindow popupWindow;
	protected OnClickListener onClickListener;
	
	protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == MESSAGE_HIDE){
				hide();
			}
		}
	};
	
	public BaseWindow(Context context){
		this(context,0,0,0);
	}
	
	public BaseWindow(Context context,int layoutId){
		this(context,layoutId,0,0);
	}
	
	public BaseWindow(Context context,int layoutId,int width,int height){
		mContext = context;
		if(width == 0 || height == 0){
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager windowManager = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE);
			windowManager.getDefaultDisplay().getMetrics(dm);
			if(width == 0){
				width = dm.widthPixels;
			}
			if(height == 0){
				height = dm.heightPixels;
			}
		}
		this.width = width;
		this.height = height;
		if(layoutId!=0){
			LayoutInflater mInflater =  (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			parent = mInflater.inflate(layoutId, null);
			popupWindow = new PopupWindow(parent,width,height);
			popupWindow.setBackgroundDrawable(new BitmapDrawable()); // �����Ϊ�˵��������Back������ó��
			// Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
			popupWindow.setFocusable(true);// �������setFocusableΪtrue��popupwindow�����ǻ�ȡ��������ģ���ô���popupwindow�����������ȵĻ����޷����롣
			popupWindow.setOutsideTouchable(true); // ����������������ʧ
		//	popupWindow.setOnDismissListener(onDismissListener);
		}
	}
	
	public void onExecute(String type){
		
	}
	
	public void dismiss(){
		if(popupWindow!=null){
			popupWindow.dismiss();
		}
	}
	
	public abstract void setData(Object data);
	public  void showAtLocation(View parent,int gravity,int x,int y){
		popupWindow.showAtLocation(parent, gravity, x, y); 
	}
	public void hide(){
		popupWindow.dismiss();
	}
	public boolean isShowing(){
		return popupWindow.isShowing();
	}
	
	public void setOnClickListener(OnClickListener onClickListener){
		this.onClickListener = onClickListener;
	}
	
	public interface OnClickListener{
		public void onClickListener(Object data);
	};
	
	public void setOnDismissListener(OnDismissListener onDismissListener){
		if(popupWindow!=null){
			popupWindow.setOnDismissListener(onDismissListener);
		}
	}
}

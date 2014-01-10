package com.linkin.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class ImageUtils {

	/**
	 * 获取view的倒影
	 * 
	 * @param v
	 *            被倒影的view
	 * @param scaleX
	 *            倒影的X轴缩放比例
	 * @param scaleY
	 *            倒影的Y轴缩放比例
	 * @param w
	 *            倒影的宽度
	 * @param h
	 *            倒影的高度
	 * @param startColor
	 *            倒影开始渐变的颜色
	 * @param endColor
	 *            倒影结束渐变的颜色
	 * @return
	 */
	public static Bitmap getReflectView(View v, float scaleX, float scaleY,
			int w, int h, int startColor, int endColor) {
		if (v == null)
			return null;

		v.setDrawingCacheEnabled(true);
		v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
		v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

		Bitmap originalImage = v.getDrawingCache();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		matrix.postScale(scaleX, scaleY);
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, 0,
				originalImage.getWidth(), originalImage.getHeight(), matrix,
				false);
		v.setDrawingCacheEnabled(false);

		if (w <= 0) {
			w = reflectionImage.getWidth();
		}
		if (h <= 0) {
			h = reflectionImage.getHeight();
		}
		ViewGroup.LayoutParams params = v.getLayoutParams();
		params.width = w;
		v.setLayoutParams(params);

		Bitmap finalImg = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		Canvas canvas = new Canvas(finalImg);
		canvas.drawBitmap(reflectionImage, 0, 0, null);
		reflectionImage.recycle();

		Paint shaderPaint = new Paint();
		LinearGradient shader = new LinearGradient(0, 0, 0,
				finalImg.getHeight() + 7, startColor < 0 ? 0 : startColor,
				endColor < 0 ? 0 : endColor, TileMode.MIRROR);
		shaderPaint.setShader(shader);
		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		canvas.drawRect(0, 0, finalImg.getWidth(), finalImg.getHeight(),
				shaderPaint);

		return finalImg;
	}

}

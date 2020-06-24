package com.asia.paint.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.network.bean.ImageRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CacheUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author by chenhong14 on 2019-11-30.
 */
public class FileUtils {

	public static CallbackDate<ImageRsp> upload(String type, String fileUrl) {
		CallbackDate<ImageRsp> mUploadResult = new CallbackDate<>();
		File file = new File(fileUrl);
		RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
		MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
		RequestBody typeBody = RequestBody.create(okhttp3.MultipartBody.FORM, type);
		//  RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, CacheUtils.getTk());
		NetworkFactory.createService(FileService.class)
				.uploadFile(typeBody, body)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ImageRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						//   addDisposable(d);
					}

					@Override
					public void onResponse(ImageRsp response) {
						mUploadResult.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mUploadResult;
	}

	public static CallbackDate<ImageRsp> uploadFile(String type, String fileUrl) {
		CallbackDate<ImageRsp> mUploadResult = new CallbackDate<>();
		File file = new File(fileUrl);
		RequestBody requestFile = RequestBody.create(MultipartBody.FORM, file);
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("type", type)
				.addFormDataPart("file", file.getName(), requestFile)
				.build();
		NetworkFactory.createService(FileService.class)
				.uploadFile(requestBody)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ImageRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						//   addDisposable(d);
					}

					@Override
					public void onResponse(ImageRsp response) {
						mUploadResult.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mUploadResult;
	}

	/**
	 * 目前只支持单个文件上传，只能组合为多个
	 */
	public static CallbackDate<List<String>> uploadMultiFile(String type, List<String> fileUrls) {
		CallbackDate<List<String>> callbackDate = new CallbackDate<>();
		List<Observable<ImageRsp>> result = new ArrayList<>();
		for (String url : fileUrls) {
			if (!TextUtils.isEmpty(url)) {
				File file = new File(url);
				RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
				MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
				RequestBody typeBody = RequestBody.create(okhttp3.MultipartBody.FORM, type);
				//  RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, CacheUtils.getTk());
				Observable<ImageRsp> uploadTask = NetworkFactory.createService(FileService.class)
						.uploadFile(typeBody, body)
						.compose(new NetworkObservableTransformer<>())
						.flatMap((Function<BaseRsp<ImageRsp>, ObservableSource<ImageRsp>>) imageRspBaseRsp -> {
							ImageRsp imageRsp;
							if (imageRspBaseRsp != null && imageRspBaseRsp.getData() != null) {
								imageRsp = imageRspBaseRsp.getData();
							} else {
								imageRsp = new ImageRsp();
							}
							return Observable.just(imageRsp);
						});

				result.add(uploadTask);
			}
		}
		if (result.isEmpty()) {
			callbackDate.setData(new ArrayList<>());
		} else {
			Disposable subscribe = Observable.zipIterable(result, objects -> {
				List<String> urls = new ArrayList<>();
				if (objects != null) {
					for (Object obj : objects) {
						if (obj instanceof ImageRsp && ((ImageRsp) obj).url != null) {
							urls.add(((ImageRsp) obj).url);
						}
					}
				}
				return urls;
			}, true, 9)
					.subscribe(callbackDate::setData);


		}
		return callbackDate;
	}

	/**
	 * 通用方法
	 */
	public static CallbackDate<ImageRsp> uploadMulti(String type, List<String> fileUrls) {
		CallbackDate<ImageRsp> mUploadResult = new CallbackDate<>();
		HashMap<String, RequestBody> mapBody = new HashMap<>();
		for (String url : fileUrls) {
			if (!TextUtils.isEmpty(url)) {
				File file = new File(url);
				RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
				mapBody.put("file\"; filename=\"" + file.getName(), requestFile);
			}
		}
		RequestBody typeBody = RequestBody.create(okhttp3.MultipartBody.FORM, type);
		RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, CacheUtils.getTk());
		NetworkFactory.createService(FileService.class)
				.uploadMultiFile(tokenBody, typeBody, mapBody)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ImageRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						//   addDisposable(d);
					}

					@Override
					public void onResponse(ImageRsp response) {
						mUploadResult.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mUploadResult;
	}

	private void compress(Bitmap bmp, File file) {

		int quality = 20;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 把压缩后的数据存放到baos中
		bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * bitmap转字节数组
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		//Bitmap转换成byte[]
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] datas = baos.toByteArray();
		return datas;
	}

	/**
	 * 如果需要截图的View并没有添加到界面上，可能是通过java代码创建的或者inflate创建的，此时调用DrawingCache方法是获取不到位图的。
	 * 因为View在添加到容器中之前并没有得到实际的大小（如果LayoutWidth是MatchParent，它还没有Parent…），所以首先需要指定View的大小
	 *
	 * @param v
	 * @param width
	 * @param height
	 */
	public static Bitmap layoutViewAndGetBitmap(View v, int width, int height) {
		// validate view.width and view.height
		v.layout(0, 0, width, height);
		int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
		int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
		// validate view.measurewidth and view.measureheight
		v.measure(measuredWidth, measuredHeight);
		v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

		//然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。
		//接着就可以创建位图并在上面绘制了
		int viewWidth = v.getMeasuredWidth();
		int viewHeight = v.getMeasuredHeight();
		Bitmap b = null;
		if (viewWidth > 0 && viewHeight > 0) {
			b = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
			Canvas cvs = new Canvas(b);
			v.draw(cvs);
		}
		return b;
	}

	/**
	 * 返回app文件目录
	 *
	 * @param context
	 * @return
	 */
	public static String getAppFilePath(Context context) {
		String appPath = getFileRoot(context) + File.separator + "YaShiQiApp";
		File file = new File(appPath);
		if (!file.exists()) {
			file.mkdirs();//创建文件夹
		}
		return appPath;
	}

	/**
	 * 文件存储根目录
	 */
	public static String getFileRoot(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File external = context.getExternalFilesDir(null);
			if (external != null) {
				return external.getAbsolutePath();
			}
		}
		return context.getFilesDir().getAbsolutePath();
	}

	public static Bitmap getBitmap(Context context, int vectorDrawableId) {
		Bitmap bitmap = null;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
			bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
					vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			vectorDrawable.draw(canvas);
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
		}
		return bitmap;
	}
}

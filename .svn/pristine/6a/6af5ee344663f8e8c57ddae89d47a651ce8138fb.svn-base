package com.asia.paint.network;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Connection;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public abstract class NetworkParamsInterceptor implements Interceptor {

	private static final Charset UTF8 = Charset.forName("UTF-8");
	private Logger logger = Logger.getLogger("YouOnHttpTK");
	private Map<String, String> mParams;
	private Map<String, String> mQueryParams;
	private Map<String, String> mFormParams;
	private Map<String, String> mMultipartParams;
	private List<String> mSkipUrls;

	public NetworkParamsInterceptor() {
		mParams = new HashMap<>();
		mQueryParams = new HashMap<>();
		mFormParams = new HashMap<>();
		mMultipartParams = new ArrayMap<>();
		mSkipUrls = new ArrayList<>();

		addParams(mParams);
		addQueryParams(mQueryParams);
		addFormParams(mFormParams);
		addMultipartParams(mMultipartParams);

		skipUrls(mSkipUrls);
	}

	@Override
	public Response intercept(Chain chain) {
		addDynamicParams();
		Request request = chain.request();
		if (!skip(request)) {
			request = addParams(request);
		}

//		//请求日志拦截
//		logForRequest(request, chain.connection());
//		//执行请求，计算请求时间
		long startNs = System.nanoTime();

		Response response = null;
		try {
			response = chain.proceed(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		//返回日志拦截
//		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
//		logForResponse(response, tookMs);

		return response;
	}

	private Request addParams(Request request) {
		String method = request.method();
		if (TextUtils.equals(method, "GET")) {
			request = addQueryParams(request);
		} else if (TextUtils.equals(method, "POST")) {
			RequestBody body = request.body();
			if (body instanceof MultipartBody) {
				request = addMultipartParams(request);
			} else {//剩余都走form
				request = addFormParams(request);
			}
		}
		return request;
	}

	private Request addQueryParams(Request request) {
		HttpUrl url = request.url();
		HttpUrl.Builder builder = url.newBuilder();
		for (Map.Entry<String, String> entry : mQueryParams.entrySet()) {
			builder.addQueryParameter(entry.getKey(), entry.getValue());
		}
		request = request.newBuilder().url(builder.build()).build();
		return request;
	}

	private Request addFormParams(Request request) {
		FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
		RequestBody body = request.body();
		if (body instanceof FormBody) {
			FormBody oldFormBody = (FormBody) body;
			for (int i = 0; i < oldFormBody.size(); i++) {
				//oldFormBody.encodedName获取的已经是encode的值了，标准化，比如将\或者中文转义成带%的字符
				// newFormBodyBuilder.addEncoded(oldFormBody.name(i), oldFormBody.value(i));
				// newFormBodyBuilder.add(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
				newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
			}
		}
		for (Map.Entry<String, String> entry : mFormParams.entrySet()) {
			newFormBodyBuilder.add(entry.getKey(), entry.getValue());
		}
		FormBody newFormBody = newFormBodyBuilder.build();
		request = request.newBuilder().method(request.method(), newFormBody).build();
		return request;
	}

	private Request addMultipartParams(Request request) {

		RequestBody body = request.body();
		if (body instanceof MultipartBody) {
			MultipartBody.Builder newMultipartBuilder = new MultipartBody.Builder();
			MultipartBody oldMultipartBody = (MultipartBody) body;

			for (int i = 0; i < oldMultipartBody.size(); i++) {
				newMultipartBuilder.addPart(oldMultipartBody.part(i));
			}
			for (Map.Entry<String, String> entry : mMultipartParams.entrySet()) {
				newMultipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
			}
			MultipartBody newMultipartBody = newMultipartBuilder.build();
			request = request.newBuilder().method(request.method(), newMultipartBody).build();
		}
		return request;
	}

	private boolean skip(Request request) {
		String url = request.url().toString();
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}
		return mSkipUrls.contains(url);
	}

	private void addDynamicParams() {
		Map<String, String> dynamicParams = getDynamicParams();
		if (dynamicParams != null && !dynamicParams.isEmpty()) {
			mQueryParams.putAll(dynamicParams);
			mFormParams.putAll(dynamicParams);
			mMultipartParams.putAll(dynamicParams);
		}
	}

	protected abstract void addParams(Map<String, String> params);

	protected abstract Map<String, String> getDynamicParams();

	protected void addQueryParams(Map<String, String> queryParams) {
		queryParams.putAll(mParams);
	}

	protected void addFormParams(Map<String, String> formParams) {
		formParams.putAll(mParams);
	}

	protected void addMultipartParams(Map<String, String> multipartParams) {
		multipartParams.putAll(mParams);
	}

	protected abstract void skipUrls(List<String> skipUrls);

	private void logForRequest(Request request, Connection connection) {
		RequestBody requestBody = request.body();
		boolean hasRequestBody = requestBody != null;
		Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

		try {
			String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
			log(requestStartMessage);
			if (hasRequestBody) {
				if (isPlaintext(requestBody.contentType())) {
					bodyToString(request);
				} else {
					log("\tbody: maybe [binary body], omitted!");
				}
			}
		} catch (Exception e) {
			Log.e("", e.toString());
		} finally {
			log("--> END " + request.method());
		}
	}

	private Response logForResponse(Response response, long tookMs) {
		Response.Builder builder = response.newBuilder();
		Response clone = builder.build();
		ResponseBody responseBody = clone.body();
		try {
			log("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
			if (HttpHeaders.hasBody(clone)) {
				if (responseBody == null)
					return response;
				if (isPlaintext(responseBody.contentType())) {
					byte[] bytes = toByteArray(responseBody.byteStream());
					MediaType contentType = responseBody.contentType();
					String body = new String(bytes, getCharset(contentType));
					log("\tbody:" + body);
					responseBody = ResponseBody.create(responseBody.contentType(), bytes);
					return response.newBuilder().body(responseBody).build();
				} else {
					log("\tbody: maybe [binary body], omitted!");
				}
			}
		} catch (Exception e) {
			Log.e("", e.toString());
		} finally {
			log("<-- END HTTP");
		}
		return response;
	}

	private void log(String message) {
		logger.log(Level.ALL, message);
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		write(input, output);
		output.close();
		return output.toByteArray();
	}

	public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
		int len;
		byte[] buffer = new byte[4096];
		while ((len = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, len);
	}

	private static Charset getCharset(MediaType contentType) {
		Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
		if (charset == null) charset = UTF8;
		return charset;
	}

	/**
	 * Returns true if the body in question probably contains human readable text. Uses a small sample
	 * of code points to detect unicode control characters commonly used in binary file signatures.
	 */
	private static boolean isPlaintext(MediaType mediaType) {
		if (mediaType == null) return false;
		if (mediaType.type() != null && mediaType.type().equals("text")) {
			return true;
		}
		String subtype = mediaType.subtype();
		if (subtype != null) {
			subtype = subtype.toLowerCase();
			if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
				return true;
		}
		return false;
	}

	private void bodyToString(Request request) {
		try {
			Request copy = request.newBuilder().build();
			RequestBody body = copy.body();
			if (body == null) return;
			Buffer buffer = new Buffer();
			body.writeTo(buffer);
			Charset charset = getCharset(body.contentType());
			log("\tbody:" + buffer.readString(charset));
		} catch (Exception e) {
			Log.e("", e.toString());
		}
	}
}

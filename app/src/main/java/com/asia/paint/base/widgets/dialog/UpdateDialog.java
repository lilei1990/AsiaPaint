package com.asia.paint.base.widgets.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.base.network.bean.UpdateRsp;
import com.asia.paint.databinding.DialogUpdateBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

/**
 * Created by Administrator on 2020/6/25.
 */

public class UpdateDialog extends BaseDialogFragment<DialogUpdateBinding> {
    private static final String KEY_ASIA_CODE = "KEY_ASIA_CODE";
    private UpdateRsp bean;
    private OnChangeCallback<String> mChangeCallback;
    //  文件保存路径
    private String mSavePath;
    //  版本名称
    private String mVersion_name = "1.0";
    //  判断是否停止
    private boolean mIsCancel = false;
    //  进度
    private int mProgress;

    public static UpdateDialog createInstance(UpdateRsp bean) {
        UpdateDialog dialog = new UpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ASIA_CODE, bean);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        bean = bundle.getParcelable(KEY_ASIA_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_update;
    }

    @Override
    protected void initView() {
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.content))
                mBinding.tvMessage.setText(bean.content);
            if (!TextUtils.isEmpty(bean.name))
                mBinding.tvName.setText(String.format("版本号：%s", bean.name));
        }
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mBinding.btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.llBtn.setVisibility(View.GONE);
                mBinding.progressbar.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(bean.url))
                    downloadAPK();
            }
        });
    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(288);
    }

    public void setChangeCallback(OnChangeCallback<String> changeCallback) {
        mChangeCallback = changeCallback;
    }

    /*
     * 开启新线程下载apk文件
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "asiaticdownload";

                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(bean.url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, bean.name);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接收消息
     */
    private Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置进度条
                    mBinding.progressbar.setProgress(mProgress);
                    break;
                case 2:
                    dismiss();
                    // 安装 APK 文件
                    installAPK();
            }
        }

        ;
    };


    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() {
        File apkfile = new File(mSavePath, bean.name);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileProvider", apkfile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        getContext().startActivity(intent);
    }


}

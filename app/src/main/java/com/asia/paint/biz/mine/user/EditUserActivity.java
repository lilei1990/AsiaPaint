package com.asia.paint.biz.mine.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityEditUserBinding;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.network.bean.UserDetail;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.base.widgets.selectimage.MatisseActivity;
import com.asia.paint.biz.mine.index.MineViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-29.
 */
public class EditUserActivity extends BaseActivity<ActivityEditUserBinding> {

    private MineViewModel mMineViewModel;
    private String mAvatarUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_user;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMineViewModel = getViewModel(MineViewModel.class);
        mBinding.ivBack.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        mBinding.tvSave.setText("保存");
        mBinding.tvSave.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                editUserInfo(mAvatarUrl);
            }
        });
        mBinding.ivAvatar.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                MatisseActivity.start(EditUserActivity.this, result -> {
                    if (result.size() > 0) {
                        FileUtils.uploadFile(FileService.HEAD, result.get(0)).setCallback(result1 -> {
                            UserDetail user = new UserDetail();
                            user.avatar = result1.img;
                            setAvatar(user);
                            editUserInfo(result1.url);
                        });
                    }
                });
            }
        });
        mBinding.cbMale.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                setSex(isChecked);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbMale.isChecked();
            }
        });
        mBinding.cbFemale.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                setSex(!isChecked);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbFemale.isChecked();
            }
        });
        mBinding.layoutBirthday.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                setBirthday();
            }
        });
        setAvatar(null);
        mMineViewModel.loadUserInfoDetail().setCallback(this::updateUserInfo);
    }

    private void updateUserInfo(UserDetail user) {
        setAvatar(user);
        mBinding.layoutAsia.setVisibility(user.isSeller() ? View.VISIBLE : View.GONE);
        mBinding.tvAsiaNo.setText(String.valueOf(user.ysh));
        mBinding.etNickname.setText(user.nickname);
        mBinding.tvBirthday.setText(user.birthday);
        setSex(user.isMale());
    }

    private void setAvatar(UserDetail user) {
        mAvatarUrl = user == null ? null : user.avatar;
        ImageUtils.loadCircleImage(mBinding.ivAvatar, user == null ? R.mipmap.ic_default :
                TextUtils.isEmpty(user.avatar) ? R.mipmap.ic_default : user.avatar);
    }

    private void setSex(boolean isMale) {
        mBinding.cbMale.setChecked(isMale);
        mBinding.cbFemale.setChecked(!isMale);
    }

    private void setBirthday() {

        String date = mBinding.tvBirthday.getText().toString();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, (date1, v) -> {//选中事件回调
            mBinding.tvBirthday.setText(DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_1));
        })
                .setCancelColor(AppUtils.getColor(R.color.color_333333))
                .setSubmitColor(AppUtils.getColor(R.color.color_333333))
                .setContentTextSize(18)
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_1));
        pvTime.setDate(calendar);
        pvTime.show();
    }

    private void editUserInfo(String avatarUrl) {
        String nickname = mBinding.etNickname.getText().toString().trim();
        int sex = mBinding.cbMale.isChecked() ? 1 : 0;
        String birthday = mBinding.tvBirthday.getText().toString().trim();
        mMineViewModel.editUserInfo(nickname, sex, birthday, avatarUrl);
    }
}

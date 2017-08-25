package com.jd.yyc.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.R;
import com.jd.yyc.home.HomeActivity;

import jd.wjlogin_sdk.common.WJLoginHelper;
import jd.wjlogin_sdk.common.listener.OnCommonCallback;
import jd.wjlogin_sdk.model.FailResult;

public class PortalActivity extends Activity {
    public static final String NEED_LOGIN_URL = "need_login_url";


	private WJLoginHelper helper;
    private String needLoginUrl;
	public int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        needLoginUrl = getIntent().getStringExtra(NEED_LOGIN_URL);
		type = getIntent().getIntExtra("type", 0);
		setContentView(R.layout.portal_activity);
		UserUtil.refreshA2();
		login();


	}


	public static void launch(Context context, int requestcode) {
		Intent intent = new Intent(context, PortalActivity.class);
		intent.putExtra("type", requestcode);
		context.startActivity(intent);
	}

	private void login() {
		//helper 已改成单例模式。
		helper = UserUtil.getWJLoginHelper();

		// 本地无用户
		if (!helper.isExistsUserInfo()) {
			toLoginActivity();
			return;
		}

		// A2不存在或已过期
		if (!helper.isExistsA2() || helper.checkA2TimeOut()) {
			if (helper.isNeedPwdInput()) {
				toLoginActivity();

			}

		}

		// 校验A2是否需要刷新
		if (helper.checkA2IsNeedRefresh()) {
			// 刷新A2
			// byte[] byteA2 = ByteUtil.parseHexStr2Byte(helper.getA2());
			helper.refreshA2(new OnCommonCallback() {

				@Override
				public void onSuccess() {
					// TODO 自动生成的方法存根
					toMainActivity();
				}

				@Override
				public void onFail(FailResult failResult) {
					// TODO 自动生成的方法存根
					toMainActivity();
				}

				@Override
				public void onError(String error) {
					// TODO 自动生成的方法存根
					Toast.makeText(PortalActivity.this, error,
							Toast.LENGTH_SHORT).show();
					toLoginActivity();
				}
			});
			return;
		}

		toMainActivity();
	}

	private void toMainActivity() {

		String a2=helper.getA2();
		String pin=helper.getPin();
		StringBuilder sb=new StringBuilder();
		sb.append("pin=");
		sb.append(pin);
		sb.append(";wskey=");
		sb.append(a2);
		Logger.e("Cookie",sb.toString());
		Network.setCookie(sb.toString());

		Intent intent = new Intent(PortalActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private void toLoginActivity() {
		Intent intent = new Intent(PortalActivity.this, LoginActivity.class);
        intent.putExtra(NEED_LOGIN_URL, needLoginUrl);
		intent.putExtra("type", type);
		startActivity(intent);
		finish();
	}

}

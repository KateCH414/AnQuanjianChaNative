package com.jd.yyc.login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.R;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.event.EventLoginMessage;
import com.jd.yyc.event.EventLoginSuccess;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.net.URLEncoder;
import java.util.HashMap;

import de.greenrobot.event.EventBus;
import jd.wjlogin_sdk.common.WJLoginHelper;
import jd.wjlogin_sdk.common.listener.OnLoginCallback;
import jd.wjlogin_sdk.common.listener.OnRefreshCheckCodeCallback;
import jd.wjlogin_sdk.model.FailResult;
import jd.wjlogin_sdk.model.JumpResult;
import jd.wjlogin_sdk.model.PicDataInfo;
import jd.wjlogin_sdk.util.MD5;
import jd.wjlogin_sdk.util.ReplyCode;


public class LoginActivity extends NoActionBarActivity implements View.OnClickListener {
    public static final String NEED_LOGIN_URL = "need_login_url";

    private ImageView iv_account_delete;
    private ImageView iv_password_state;
    private ImageView imageViewAutoCode;
    private ImageView back;
    private EditText et_login_Account;
    private EditText et_login_Password;
    private ImageView iv_password_delete;
    private Button bt_login;
    private WJLoginHelper helper;
    private PicDataInfo mPicDataInfo;
    private RelativeLayout autoLayout;
    private int times = 0;
    boolean eyeOpen = false;
    ProgressBar loginpbar;
    RelativeLayout autoCodeLayout;

    private EditText autoCodetext;

    private String needLoginUrl;
    public int type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        needLoginUrl = getIntent().getStringExtra(NEED_LOGIN_URL);
        type = getIntent().getIntExtra("type", 0);
        setContentView(R.layout.login);
        init();
        try {
            helper = UserUtil.getWJLoginHelper();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        pwdChange();

    }
    private void pwdChange() {
        et_login_Account.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_login_Password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private boolean checkInput() {

        String account = et_login_Account.getText().toString().trim();

        if (account == null || account.equals("")) {
            Toast.makeText(this, "请输入您的账号", Toast.LENGTH_SHORT).show();
            et_login_Account.setFocusable(true);
            return false;
        }

        String pwd = et_login_Password.getText().toString().trim();

        if (pwd == null || pwd.equals("")) {
            Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
            et_login_Password.setFocusable(true);
            return false;
        }
        String auto = autoCodetext.getText().toString().trim();
        if (mPicDataInfo != null && (auto == null || auto.equals(""))) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            autoCodetext.setFocusable(true);
            return false;
        }

        return true;
    }
    private void init() {
        et_login_Account = (EditText) findViewById(R.id.et_login_Account);
        et_login_Password = (EditText) findViewById(R.id.et_login_Password);
        iv_account_delete = (ImageView) findViewById(R.id.iv_account_delete);
        iv_password_state = (ImageView) findViewById(R.id.iv_password_state);
        back = (ImageView) findViewById(R.id.back);
        imageViewAutoCode = (ImageView) findViewById(R.id.imageViewAutoCode);
        autoLayout = (RelativeLayout) findViewById(R.id.autoCodeLayout);
        iv_password_delete = (ImageView) findViewById(R.id.iv_password_delete);
        autoCodetext = (EditText) findViewById(R.id.autoCode);
        loginpbar = (ProgressBar) findViewById(R.id.loginpbar);
        imageViewAutoCode.setOnClickListener(this);
        back.setOnClickListener(this);
        bt_login = (Button) findViewById(R.id.bt_login);
        iv_account_delete.setOnClickListener(this);
        iv_password_delete.setOnClickListener(this);
        iv_password_state.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        et_login_Account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (et_login_Account.length() > 0) {
                    iv_account_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_account_delete.setVisibility(View.INVISIBLE);
                }
            }
        });
       /* et_login_Account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    iv_account_delete.setVisibility(View.VISIBLE);

                }
                else{
                    iv_account_delete.setVisibility(View.INVISIBLE);
                }
            }
        });*/
        et_login_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (et_login_Password.length() > 0) {
                    iv_password_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_password_delete.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    public void refreshImageCode() {
        {
            try {
                // TODO Auto-generated method stub
                if (mPicDataInfo != null) {
                    showBar(true);
                    mPicDataInfo.setAuthCode("0");
                    helper.refreshImageCode(mPicDataInfo,
                            new OnRefreshCheckCodeCallback() {
                                @Override
                                public void onSuccess(
                                        PicDataInfo picDataInfo) {
                                    showBar(false);
                                    mPicDataInfo = picDataInfo;
                                    if (picDataInfo != null) {
                                        autoLayout.setVisibility(View.VISIBLE);
                                        byte[] b = mPicDataInfo
                                                .getsPicData();
                                        Bitmap bmp = BitmapFactory
                                                .decodeByteArray(b, 0,
                                                        b.length);
                                        imageViewAutoCode
                                                .setImageBitmap(bmp);
                                    }
                                }
                                @Override
                                public void onFail(FailResult failResult) {
                                    showBar(false);
                                    // 刷验证码时 验证码失效，刷新页面
                                    if (failResult.getReplyCode() == ReplyCode.reply0x11) {
                                        mPicDataInfo = null;
                                        autoLayout.setVisibility(View.GONE);
                                    }
                                    // 验证码刷新次数过多 刷新页面
                                    if (failResult.getReplyCode() == ReplyCode.reply0x12) {
                                        mPicDataInfo = null;
                                        autoLayout.setVisibility(View.GONE);
                                    }
                                    Toast.makeText(LoginActivity.this,
                                            failResult.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onError(String error) {
                                    showBar(false);
                                    Toast.makeText(LoginActivity.this,
                                            error, Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                }
            } catch (Exception e) {
                // TODO: handle exception
                showBar(false);
            }
        }
    }
    @Override
    public void onClick(View v) {
        HashMap<String, String> hashMap = new HashMap<>();
        switch (v.getId()) {
            case R.id.bt_login: {
                try {
                    ClickInterfaceParam params = new ClickInterfaceParam();
                    params.event_id = "yjc_android_201706262|1";
                    params.page_name = "登录";
                    JDMaUtil.sendClickData(params);
                    if (!checkInput()) {
                        return;
                    }
                    showBar(true);
                    if (mPicDataInfo != null) {

                        String autoCode = autoCodetext.getText().toString().trim();

                        mPicDataInfo.setAuthCode(autoCode);
                    }
                    String userAccount = et_login_Account.getText().toString().trim();
                    String pswd = MD5.encrypt32(et_login_Password.getText().toString()
                            .trim());
                    hashMap.put("user_log_acct", userAccount);
                    PvInterfaceParam param = new PvInterfaceParam();
                    param.page_id = "0001";
                    param.page_name = "登录";
                    param.map = hashMap;
                    JDMaUtil.sendPVData(param);
                    SharePreferenceUtil.setLoginAccount(mContext, userAccount);
                    helper.JDLoginWithPassword(userAccount, pswd, mPicDataInfo,
                            true, onLoginCallback);
                } catch (Exception e) {
                    showBar(false);
                    e.printStackTrace();
                }
                break;
            }
            case R.id.iv_account_delete: {
                et_login_Account.setText("");
                break;
            }
            case R.id.back: {
                finish();
                break;
            }
            case R.id.imageViewAutoCode: {
                refreshImageCode();
                break;
            }
            case R.id.iv_password_delete: {
                et_login_Password.setText("");
                break;
            }
            case R.id.iv_password_state: {
                if (eyeOpen) {
                    et_login_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeOpen = false;
                    iv_password_state.setImageResource(R.drawable.loginpasswordinvisible);

                } else {
                    et_login_Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_password_state.setImageResource(R.drawable.loginpasswordvisible);
                    eyeOpen = true;
                }
                break;
            }
        }
    }
    OnLoginCallback onLoginCallback = new OnLoginCallback() {
        @Override
        public void onSuccess() {
            showBar(false);
            // 主界面
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                    .show();
            String a2 = helper.getA2();
            String pin = helper.getPin();
            StringBuilder sb = new StringBuilder();
            sb.append("pin=");
            sb.append(URLEncoder.encode(pin));
            sb.append(";wskey=");
            sb.append(a2);
            Logger.e("Cookie", sb.toString());
            Network.setCookie(sb.toString());
            EventBus.getDefault().post(CheckTool.isEmpty(needLoginUrl) ? new EventLoginSuccess() : new EventLoginSuccess(needLoginUrl));
            EventBus.getDefault().post(new EventLoginMessage(type));
            finish();
        }
        @Override
        public void onError(String error) {
            showBar(false);
            String[] ad = error.split(":");
            Toast.makeText(LoginActivity.this, ad[2].substring(1, ad[2].length() - 2), Toast.LENGTH_SHORT)
                    .show();
        }
        @Override
        public void onFail(FailResult failResult, PicDataInfo picDataInfo) {
            showBar(false);
            try {
                String message = failResult.getMessage();
                mPicDataInfo = picDataInfo;
                if (picDataInfo != null) {
                    autoLayout.setVisibility(View.VISIBLE);
                    byte[] b = mPicDataInfo.getsPicData();
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    imageViewAutoCode.setImageBitmap(bmp);
                }
                // 登录频繁，稍后再试
                if (failResult.getReplyCode() == ReplyCode.reply0x8) {
                    // 是否需要禁止用户操作，由客户端决定
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT)
                            .show();
                }
                //帐号不存在
                else if (failResult.getReplyCode() == ReplyCode.reply0x7) {
                    if (times < 2) {
                        times++;
                        Toast.makeText(LoginActivity.this, "您的账号不存在", Toast.LENGTH_SHORT)
                                .show();

                    } else {
                        times = 0;
                        Toast.makeText(LoginActivity.this, "需要注册一个新账号", Toast.LENGTH_SHORT)
                                .show();
                        //    showDialog(LoginActivity.this, "确定","需要注册一个新账号吗？","toRegist");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT)
                            .show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //2015-3-2 add  //todo:考虑是否需要在回调中增加这个函数.
        public void onFail(FailResult failResult, JumpResult jumpResult, PicDataInfo picDataInfo) {
            showBar(false);
            try {
                String message = failResult.getMessage();
                mPicDataInfo = picDataInfo;
                if (picDataInfo != null) {
                    autoLayout.setVisibility(View.VISIBLE);
                    byte[] b = mPicDataInfo.getsPicData();
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    imageViewAutoCode.setImageBitmap(bmp);
                }
                Log.e("msg", "msg:" + message);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT)
                        .show();
                String jumpToken = jumpResult.getToken();
                String url = jumpResult.getUrl();
                if (failResult.getReplyCode() >= ReplyCode.reply0x80 &&
                        failResult.getReplyCode() <= ReplyCode.reply0x8f) {
                    //风险用户登录，上下行短信验证
                    //  String fullBindUrl = jumpFengkongM(url, jumpToken);
                    //    toWebActivity(fullBindUrl,"sms");
                } else if (failResult.getReplyCode() >= ReplyCode.reply0x77 && failResult.getReplyCode() <= ReplyCode.reply0x7a) {
                    // 是否需要禁止用户操作，由客户端决定
                    Toast.makeText(LoginActivity.this, failResult.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                } else if (failResult.getReplyCode() >= ReplyCode.reply0x7b && failResult.getReplyCode() <= ReplyCode.reply0x7e) {
                    Toast.makeText(LoginActivity.this, failResult.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    //方法中已封装post
                    Toast.makeText(LoginActivity.this, failResult.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private void showBar(boolean bshow) {
        if (bshow) {
            bt_login.setEnabled(false);
            imageViewAutoCode.setEnabled(false);
            loginpbar.setVisibility(View.VISIBLE);
        } else {
            loginpbar.setVisibility(View.GONE);
            bt_login.setEnabled(true);
            imageViewAutoCode.setEnabled(true);
        }
    }

}

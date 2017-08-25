package com.jd.yyc.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.RequestBuilder;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

//import com.lifeweeker.nuts.util.ChatHelper;

/**
 *
 */
public class NetRequest {

    public static final int REQUEST_OK = 200;
    public static final int NET_ERROR = -1;
    public static final int ERROR_DATA = -2;

    private static boolean DEBUG_MODE = true;

    public interface RequestMethod {
        public int GET = 1;
        public int POST = 2;
        public int DELETE = 3;
    }

    private static NetRequest netRequest;

    private NetRequest() {
    }

    public static synchronized NetRequest getInstance() {
        if (netRequest == null) {
            netRequest = new NetRequest();
        }
        return netRequest;
    }

    public <T> void post(final Context activity, final String url, final Map<String, String> parameters, final RequestCallback<T> requestCallback) {
        post(activity, url, parameters, false, requestCallback);
    }

    public <T> void post(final Context activity, final String url, final Map<String, String> parameters, boolean showLoadingDialog, final RequestCallback<T> requestCallback) {
        request(activity, RequestMethod.POST, url, parameters, null, showLoadingDialog, requestCallback);
    }


    public <T> void get(final Context activity, final String url, final Map<String, String> parameters, final RequestCallback<T> requestCallback) {
        request(activity, RequestMethod.POST, url, parameters, null, false, requestCallback);
    }

    public <T> void delete(final Context context, final String url, final Map<String, String> parameters, final RequestCallback<T> requestCallback) {
        request(context, RequestMethod.POST, url, parameters, null, false, requestCallback);
    }

    private <T> void request(final Context activity, final int type, final String url, final Map<String, String> parameters, final Map<String, String> header, final boolean showDialogProgress, final RequestCallback<T> requestCallback) {
        logUrl(url, type, parameters, null, null);
        if (showDialogProgress)
            LoadingDialogUtil.show(activity);
        RequestBuilder builder = Network.getRequestBuilder(url)
                .params(parameters)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        LoadingDialogUtil.close();
//                        log(url, false, model);
                        requestCallBack(url, parameters, model, true, requestCallback);
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        LoadingDialogUtil.close();
//                        log(url, false, errorMessage);
                        requestCallback.requestCallBack(false, null, errorMessage);
                    }
                });
        if (type == RequestMethod.GET) {
            builder.get();

        } else if (type == RequestMethod.POST) {
            builder.post();
        }
    }

    public String getUrlKey(String url, Map map) {
        return url.hashCode() + (map == null ? "" : map.toString()) + "-data";
    }


    private Gson gson = new Gson();
    private JsonParser jsonParser = new JsonParser();
    private Handler maniHandler = new Handler(Looper.getMainLooper());

    private <T> void requestCallBack(String url, final Map<String, String> parameters, final String body, boolean async, final RequestCallback<T> requestCallback) {
        if (requestCallback == null) {
            return;
        }
        T t = null;
        Type type = requestCallback.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type argument = parameterizedType.getActualTypeArguments()[0];

            try {
                if (argument == JSONObject.class) {
                    t = (T) new JSONObject(body);
                } else if (argument == JSONArray.class) {
                    t = (T) new JSONArray(body);
                } else if (argument == JsonObject.class) {
                    t = (T) jsonParser.parse(body).getAsJsonObject();
                } else if (argument == JsonArray.class) {
                    t = (T) jsonParser.parse(body).getAsJsonArray();
                } else if (argument == String.class) {
                    t = (T) body;
                } else {
                    t = gson.fromJson(body, argument);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                maniHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show("数据解析失败");
                        requestCallback.requestCallBack(false, null, "数据解析失败");
                    }
                });
                return;
            }
        } else {
            t = (T) body;
        }

        final T back = t;
        if (async) {
            maniHandler.post(new Runnable() {
                @Override
                public void run() {
                    requestCallback.requestCallBack(true, back, null);
                }
            });
        } else {
            requestCallback.requestCallBack(true, back, null);
        }
    }

    public static void setDebugMode(boolean debug) {
        DEBUG_MODE = debug;
    }

    private void log(String url, boolean local, String result) {
        if (!DEBUG_MODE) {
            return;
        }
//        logUrl(url, type, parameters, object, map);
        Log.d(">>> NetHttp <<<", "url: " + url + (local ? " local:" : " net:") + result);
    }

    private void logUrl(String url, final int type, Map<String, String> parameters, JsonObject object, Map<String, String> map) {
        if (!DEBUG_MODE) {
            return;
        }
        Log.d(">>> NetHttp <<<", "url -> " + url);
        if (type == RequestMethod.GET) {
            Log.d(">>> NetHttp <<<", "get -> " + (CheckTool.isEmpty(parameters) ? "" : parameters.toString()));
        } else if (type == RequestMethod.POST) {
            Log.d(">>> NetHttp <<<", "post -> " + (CheckTool.isEmpty(parameters) ? "" : parameters.toString()));
        } else {
            Log.d(">>> NetHttp <<<", "header -> " + (CheckTool.isEmpty(parameters) ? "" : parameters.toString()));
        }
    }

}


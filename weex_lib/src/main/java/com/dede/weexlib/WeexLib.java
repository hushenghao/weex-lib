package com.dede.weexlib;

import android.app.Application;
import android.support.annotation.Keep;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXException;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hsh on 2019/1/9 11:03 AM
 */
@Keep
public final class WeexLib {

    public static final String EXTRA_WEEX_URL = "extra_weex_url";
    public static final String EXTRA_PAGE_NAME = "extra_page_name";

    private static HashMap<String, Class<? extends WXModule>> modules = new HashMap<>();

    static void registerModule() {
        if (modules.isEmpty()) return;
        try {
            Set<Map.Entry<String, Class<? extends WXModule>>> entrySet = modules.entrySet();
            for (Map.Entry<String, Class<? extends WXModule>> entry : entrySet) {
                WXSDKEngine.registerModule(entry.getKey(), entry.getValue());
            }
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    static boolean debug = false;

    private WeexLib() {
    }

    public static Builder with(Application application) {
        return new Builder(application);
    }

    @Keep
    public final static class Builder {
        private InitConfig.Builder mBuilder;
        private Application mApplication;


        public InitConfig.Builder getInitBuilder() {
            return mBuilder;
        }

        private Builder(Application application) {
            this.mApplication = application;
            mBuilder = new InitConfig.Builder();
        }

        public Builder setImageAdapter(IWXImgLoaderAdapter iwxImgLoaderAdapter) {
            mBuilder.setImgAdapter(iwxImgLoaderAdapter);
            return this;
        }

        public Builder debug(boolean debug) {
            WeexLib.debug = debug;
            return this;
        }

        public Builder addModule(String name, Class<? extends WXModule> moduleClass) {
            modules.put(name, moduleClass);
            return this;
        }

        // todo 添加其他方法

        public void init() {
            InitConfig config = mBuilder.build();
            WXSDKEngine.initialize(mApplication, config);
        }
    }
}

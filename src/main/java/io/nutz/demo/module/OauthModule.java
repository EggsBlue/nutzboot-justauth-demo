package io.nutz.demo.module;


import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.*;
import org.nutz.mvc.annotation.*;

@At("/oauth")
@IocBean(create="init", depose="depose")
public class OauthModule {
    
    @Inject
    protected PropertiesProxy conf;

    /**
     * 获取授权的url让用户在浏览器访问,其中会拼接一个redirectUrl(配置文件配置),授权方会带着相关参数回调请求我们的服务器{@link OauthModule#callback(String, AuthCallback)},然后获取Access_token和用户信息
     * @param oauthType
     * @return
     */
    @At("/login/?")
    @Ok("re")
    public String login(String oauthType) {
        AuthRequest authRequest = getAuthRequest(oauthType);
        return "redirect:"+authRequest.authorize(AuthStateUtils.createState());
    }


    private AuthRequest getAuthRequest(String oauthType) {
        AuthSource authSource = AuthSource.valueOf(oauthType.toUpperCase());
        switch (authSource) {
            case QQ:
                return getQqAuthRequest();
            case GITHUB:
                return getGithubAuthRequest();
            case MI:
                return getMiAuthRequest();
            case GITEE:
                return getGitEEAuthRequest();
            default:
                throw new RuntimeException("暂不支持的第三方登录");
        }
    }

    /**
     * 授权方调用,授权信息自动
     * @param oauthType
     * @param callback
     * @return
     */
    @At("/callback/?")
    @Ok("json:full")
    public Object callback(String oauthType, @Param("..") AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest(oauthType);
        AuthResponse response = authRequest.login(callback);
        return response;
    }

    private AuthRequest getQqAuthRequest() {
        AuthConfig authConfig = AuthConfig.builder().clientId(conf.get("qq.clientId")).clientSecret(conf.get("qq.clientSecret")).redirectUri(conf.get("qq.redirectUri")).build();
        return new AuthQqRequest(authConfig);
    }

    private AuthRequest getGithubAuthRequest() {
        AuthConfig authConfig = AuthConfig.builder().clientId(conf.get("github.clientId")).clientSecret(conf.get("github.clientSecret")).redirectUri(conf.get("github.redirectUri")).build();
        return new AuthGithubRequest(authConfig);
    }

    private AuthRequest getMiAuthRequest() {
        AuthConfig authConfig = AuthConfig.builder().clientId(conf.get("mi.clientId")).clientSecret(conf.get("mi.clientSecret")).redirectUri(conf.get("mi.redirectUri")).build();
        return new AuthMiRequest(authConfig);
    }

    private AuthRequest getGitEEAuthRequest() {
        AuthConfig authConfig = AuthConfig.builder().clientId(conf.get("gitee.clientId")).clientSecret(conf.get("gitee.clientSecret")).redirectUri(conf.get("gitee.redirectUri")).build();
        return new AuthGiteeRequest(authConfig);
    }


    public void init() {}
    public void depose() {}

}

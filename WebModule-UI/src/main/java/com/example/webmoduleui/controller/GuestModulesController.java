package com.example.webmoduleui.controller;

import com.example.webmoduleui.Utils.RSA;
import com.example.webmoduleui.Utils.RSAUtils;
import com.example.webmoduleui.dto.NavigationTree;
import com.example.webmoduleui.service.UIService;
import com.example.webmoduleui.service.UserModulesService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.task.TaskDecorator;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.interfaces.RSAPrivateKey;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/Guest")
//@Secured({ "ROLE_DBA", "ROLE_ADMIN" })  //spring-security 类权限注解
@CrossOrigin
public class GuestModulesController {
    RSA rsa = new RSA();
    @Resource(name = "userModulesService")
    private UserModulesService userModulesService;
    @Resource(name = "UIService")
    private UIService UIService;

    @GetMapping("/")
    public String defaultIndex() {
        return rsa.publicKey;
    }

    @GetMapping("/verifyCode")
    public String getVerifyCode(HttpServletResponse response) throws JSONException {
        List<String> imageCode = UIService.getVerifyCode();
        if (ObjectUtils.isEmpty(imageCode)) {
            return null;
        } else {
            JSONObject obj = new JSONObject();
            response.setHeader("kaptchaOwner",imageCode.get(0));
            obj.put("imageCode", imageCode.get(1));
            return obj.toString();
        }
    }
    @GetMapping("/navigation")
    public List<NavigationTree> getNavigation(HttpServletResponse response) throws JSONException {
       return UIService.getNavigationTree();
    }

    @ResponseBody
//    @PreAuthorize("hasAuthority('read')") //spring-security 方法权限注解 "hasRole('ROLE_ADMIN') AND hasRole('ROLE_DBA')" 或者 "hasAuthority('admin')" 或者 "自定义@[类名].[方法名]"
//    @PostAuthorize("returnObject.type == authentication.name") //spring-security 方法权限注解
    @RequestMapping("/userLogin")
    public String userLogin(@RequestParam("userId") String userId,
                            @RequestParam("password") String encrypt_Pwd,
                            @RequestParam("verifyCode") String verifyCode,
                            @RequestParam("kaptchaOwner") String kaptchaOwner) throws Exception {
        if(UIService.verifyCodeCheck(verifyCode,kaptchaOwner).equals("success")){
            String privateKey = rsa.privateKey;
            RSAPrivateKey rsaPrivateKey = RSAUtils.getPrivateKey(privateKey);
            //如果数据库存储的是加密后的密文时使用
//        String Decrypt_database = RSAUtils.privateDecrypt(select_Password, rsaPrivateKey);
            String Decrypt_web = RSAUtils.privateDecrypt(encrypt_Pwd, rsaPrivateKey);
            log.info("{} login success", userId);
            return userModulesService.login(userId, Decrypt_web);
        }
        log.info("{} login fail", userId);
        return "verifyCode error";
    }
}
//spring-security 添加权限  SecurityContextHolder.getContext().setAuthentication(authentication);
//1————————
//if (userOptional.isPresent()) {
//// token有效，则设置登录信息
//// 设置用户角色
//List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//    for (Role role: userOptional.get().getRoles()) {
//        authorities.add(new SimpleGrantedAuthority(role.getValue()));
//        }
//PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
//        new UserServiceImpl.UserDetail(userOptional.get(), new ArrayList<>()), null, authorities);
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//}
//2————————
//UserDetails userDetails = this.authService.loadUserByUsername(authentication.getName());
//        SecurityContextHolder.getContext().setAuthentication(
//                new SuperPasswordAuthenticationToken(
//        userDetails,
//                authentication.getCredentials(),
//                        userDetails.getAuthorities()
//                ));
package com.example.demoui.controller;

import com.example.demoui.Utils.RSA;
import com.example.demoui.Utils.RSAUtils;
import com.example.demoui.dto.NavigationTree;
import com.example.demoui.service.UIService;
import com.example.demoui.service.UserModulesService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPrivateKey;
import java.util.List;

@RestController
@RequestMapping(value = "/Guest")
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
            return userModulesService.login(userId, Decrypt_web);
        }
        return "verifyCode error";
    }
}

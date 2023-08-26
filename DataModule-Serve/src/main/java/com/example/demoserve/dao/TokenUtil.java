package com.example.demoserve.dao;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoserve.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TokenUtil {
    private static final long EXPIRE_TIME= 30*60*1000;   //三十分钟
    private static final String TOKEN_SECRET="Zcl27272369250?";  //密钥盐

    /**
     * 签名生成
     * @param user
     * @return
     */
    public static String sign(User user){
        String token = null;
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);  //过期时间
        try {
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userName", user.getUserName())
                    .withClaim("userId", user.getUserId())
//                    .withAudience(employee.getEmployeeName())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.err.println("认证通过：");
            System.err.println("userId: " + jwt.getClaim("userId").asString());
            System.err.println("userName: " + jwt.getClaim("userName").asString());
            System.err.println("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            System.out.println("token错误");
            return false;
        }
    }

    public static String refresh(String token){
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userName", jwt.getClaim("userName").asString())
                    .withClaim("userId", jwt.getClaim("userId").asString())
//                    .withAudience(employee.getEmployeeName())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static String getUserId(String token){
        return JWT.decode(token).getClaim("userId").asString();
    }
}
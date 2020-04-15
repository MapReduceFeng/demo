package com.example.demo.system.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTUtil {
    static final Algorithm algorithm = Algorithm.HMAC256("secret");
    static final Map<String, Object> header = new HashMap<>();
    static final JWTVerifier verifier = JWT.require(JWTUtil.algorithm).withIssuer("SERVICE").build();

    static {
        header.put("alg", "HS256");
        header.put("typ", "JWT");
    }

    public static String createTokenWithClaim(Subject mySubject) {
        try {
            Date nowDate = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(nowDate);
            calendar.add(Calendar.SECOND, 1000);
            String token = JWT.create()//设置头部信息 Header
                    .withHeader(header)//设置 载荷 Payload
                    .withClaim("loginName", mySubject.getClaim())//用户名
                    .withIssuer(mySubject.getIssuer())//签名是有谁生成 例如 服务器
                    .withSubject(mySubject.getSubject())//签名的主题
                    .withAudience(mySubject.getAudience())//签名的观众 也可以理解谁接受签名的
                    .withIssuedAt(nowDate) //生成签名的时间
                    .withExpiresAt(calendar.getTime())//签名过期的时间
                    .withArrayClaim("roles", mySubject.getArrayClaim().toArray(new String[]{}))//签名 角色 权限
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Subject verifyTokenTo(String token) {
        Subject subject = new Subject();
        try {
            DecodedJWT jwt = verifier.verify(token);
            subject.setSubject(jwt.getSubject());
            subject.setClaim(jwt.getClaims().get("loginName").asString());
            List<String> audience = jwt.getAudience();
            subject.setAudience(audience.get(0));
            Claim role = jwt.getClaim("roles");
            subject.setArrayClaim(role.asList(String.class));
            subject.setIssuer(jwt.getIssuer());
            jwt.getExpiresAt().getTime();
            return subject;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Subject();
        }

    }

    public static boolean verifyToken(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String continuationToken(String token) {
        Subject mySubject = JWTUtil.verifyTokenTo(token);
        return JWTUtil.createTokenWithClaim(mySubject);
    }
}

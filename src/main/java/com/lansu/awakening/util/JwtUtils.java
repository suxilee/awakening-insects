package com.lansu.awakening.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author sulan
 * @date 2023/08/03
 */
public class JwtUtils {
    private static final String PRIVATE_KEY_FILENAME = "key/private.key";
    private static final String PUBLIC_KEY_FILENAME = "key/public.key";


    private static RSAKey rsaKey;
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    private static String KEY_ID="16dsNW4KFez5rHNdasjik120kp4AwJ39tMe";


    static {
        try {
            // 载入RSA密钥
            InputStream privateKeyInputStream = JwtUtils.class.getClassLoader().getResourceAsStream(PRIVATE_KEY_FILENAME);
            byte[] privateKeyBytes = readAllBytes(privateKeyInputStream);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(privateKeySpec);

            InputStream publicKeyInputStream = JwtUtils.class.getClassLoader().getResourceAsStream(PUBLIC_KEY_FILENAME);
            byte[] publicKeyBytes = readAllBytes(publicKeyInputStream);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            publicKey = keyFactory.generatePublic(publicKeySpec);

            // 创建RSAKEY
            rsaKey = new RSAKey.Builder((RSAPublicKey) publicKey)
                    .privateKey(privateKey)
                    .keyUse(KeyUse.SIGNATURE)
                    .keyID(KEY_ID)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return Base64.getDecoder().decode(buffer.readLine());
        }
    }

    /**
     * 生成令牌
     *
     * @param subject        主题
     * @param expirationTime 过期时间
     * @return {@link String}
     * @throws JOSEException joseexception
     */
    public static String generateToken(String subject, long expirationTime) throws JOSEException {
        // 创建jwt 算法头 rs256
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(rsaKey.getKeyID())
                .build();
        // 创建载体
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        // 创建JWS对象
        JWSObject jwsObject = new JWSObject(header, payload);
        // 创建签名
        RSASSASigner signer = new RSASSASigner(privateKey);
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return boolean
     * @throws JOSEException  joseexception
     * @throws ParseException 解析异常
     */
    public static boolean validateToken(String token) throws JOSEException, ParseException {
        // 解析令牌
        JWSObject jwsObject = JWSObject.parse(token);
        // 公钥验签
        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        return jwsObject.verify(verifier);
    }

    /**
     * 获取令牌主题
     *
     * @param token 令牌
     * @return {@link String}
     * @throws ParseException 解析异常
     */
    public static String getSubject(String token) throws ParseException {
        SignedJWT signedJwt = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJwt.getJWTClaimsSet();
        return claimsSet.getSubject();
    }

    /**
     * 令牌是否过期
     *
     * @param token 令牌
     * @return boolean
     * @throws ParseException 解析异常
     */
    public static boolean isTokenExpired(String token) throws ParseException {
        SignedJWT signedJwt = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJwt.getJWTClaimsSet();
        return claimsSet.getExpirationTime().before(new Date());
    }
}

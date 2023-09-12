package com.polog.polog.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AES128Config {
    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;
    private static final String INSTANCE_TYPE = "AES/CBC/PKCS5Padding";

    @Value("${aes.secret-key}")
    private String secretKey;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    @PostConstruct // @PostConstruct를 사용하면 의존성 주입이 끝나고 실행됨이 보장
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];   // 16bytes = 128bits
        secureRandom.nextBytes(iv);
        ivParameterSpec = new IvParameterSpec(iv);
        secretKeySpec = new SecretKeySpec(secretKey.getBytes(ENCODING_TYPE), "AES");
        cipher = Cipher.getInstance(INSTANCE_TYPE);
    }
    // SecureRandom 인스턴스를 사용해서 16바이트 크기의 난수 바이트 배열을 생성하고 바이트 배열을 사용해서 IvParameterSpec 객체를 생성한다.
    // SecurityRnadom를 사용한 이유는 SecretKeySpec과 IvParameterSpec에 동일한 키를 사용하게 되면 보안에 취약하기 때문이다.
    // 이 때 보안을 강화하는 방법으로 SecureRandom과 같은 난수 생성기를 사용하는 것이다.

    // ENCODING_TYPE(UTF-8)을 사용하여 Secret Key 문자열을 바이트로 변환한 키 값과 문자열 “AES”를 사용해서 SecretKeySpec을 생성한다.
    // 알고리즘 타입을 지정하여 Cipher를 생성한다. 나는 AES/CBC/PKCS5Padding 를 사용했다.


    // AES 암호화
    public String encryptAes(String plaintext) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(ENCODING_TYPE));
            return new String(Base64.getEncoder().encode(encrypted), ENCODING_TYPE);
        } catch (Exception e) {
            throw new RuntimeException("ENCRYPTION_FAILED");
        }
    }
    // secretKeySpec과 ivParameterSpec를 사용하여 암호화 모드(ENCRYPT_MODE)에서 Cipher를 초기화하고
    // doFinal() 메서드로 데이터를 암호화한다.
    // 암호화한 데이터는 편의를 위해 문자열로 인코딩하여 반환했다.

    // AES 복호화
    public String decryptAes(String plaintext) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decoded = Base64.getDecoder().decode(plaintext.getBytes(ENCODING_TYPE));
            return new String(cipher.doFinal(decoded), ENCODING_TYPE);
        } catch (Exception e) {
            throw new RuntimeException("DECRYPTION_FAILED");
        }
    }
    // 암호화와는 반대로 복호화 모드(DECRYPT_MODE)에서 secretKeySpec과 ivParameterSpec를 사용하여 Cipher를 초기화하고
    // doFinal() 메서드로 데이터를 복호화 한다.
    // 복호화한 데이터도 문자열로 인코딩하여 반환했다.

}
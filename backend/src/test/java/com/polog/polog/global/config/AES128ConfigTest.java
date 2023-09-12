package com.polog.polog.global.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AES128ConfigTest {

    @Autowired
    private AES128Config aes128Config;

    @Autowired
    private AesUtil aesUtil;



    @Test
    @DisplayName("Aes128 암호화가 잘 이루어지는지 테스트")
    void aes128Test() throws Exception {
        String text = "this is test";
        String enc = aes128Config.encryptAes(text);
        String dec = aes128Config.decryptAes(enc);
        log.info("enc = {}", enc);
        log.info("dec = {}", dec);

        Assertions.assertEquals(dec,text);
    }
}
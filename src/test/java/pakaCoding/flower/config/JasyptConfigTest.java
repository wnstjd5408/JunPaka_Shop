package pakaCoding.flower.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pakaCoding.flower.JasyptTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class JasyptConfigTest extends JasyptConfig{

    @Test
    public void jasypt_encrypt_decrypt_test() {
        String url = "jdbc:mariadb://localhost:3306/flower_site";
        String username = "boardAdmin";
        String password = "1234";

        String encryptUrl  =jasyptEncrypt(url);
        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);

        System.out.println("encryptUrl = " + encryptUrl);
        System.out.println("encryptUsername = " + encryptUsername);
        System.out.println("encryptPassword = " + encryptPassword);

        assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
        assertThat(username).isEqualTo(jasyptDecryt(encryptUsername));
        assertThat(password).isEqualTo(jasyptDecryt(encryptPassword));
    }

    private String jasyptEncrypt(String input){
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);

    }


    private String jasyptDecryt(String input){
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}
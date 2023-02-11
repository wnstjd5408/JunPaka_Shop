package pakaCoding.flower.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  서버 정보 암호화 하기위한 ConfigClass
 * 아래의 URL에서 encrypt decrypt 가능
 * https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
 *
 * application-dev.yml 안에 있는 암호화된 DB 정보를 decrypt 하기 위해선 환경변수에서 값을 직접 입력받아야 합니다.
 */

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {
    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(){

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);//암호화할 때 사용하는 키
        config.setPoolSize("1");//인스턴스 pool
        config.setAlgorithm("PBEWithMD5AndDES");//암호화 알고리즘
        config.setStringOutputType("base64");
        config.setKeyObtentionIterations("1000");//반복할 해싱 회수
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        encryptor.setConfig(config);
        return encryptor;
    }
}

package pakaCoding.flower;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JasyptTest {

    @Value("${properties.test.value")
    private String propertiesTestValue;


    public String getPropertiesTestValue() {
        return propertiesTestValue;
    }
}

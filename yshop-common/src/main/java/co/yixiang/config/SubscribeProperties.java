package co.yixiang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "yshop.subscribe")
public class SubscribeProperties {
    private Map<String, String> subscribeMaps;
}

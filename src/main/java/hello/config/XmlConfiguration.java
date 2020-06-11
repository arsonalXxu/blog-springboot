package hello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

// xml配置的方式生成bean
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class XmlConfiguration {
}

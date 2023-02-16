package chinapopin.apply.fireproofing.outweb.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *     配置：
 * https://blog.csdn.net/weixin_38413579/article/details/82850964
 * https://zhuanlan.zhihu.com/p/69531219
 * https://segmentfault.com/a/1190000023346414?utm_source=sf-similar-article
 * https://segmentfault.com/a/1190000023387540
 * </p>
 *
 * @author caiqingzhou
 * @since 2021/12/25
 */
@Configuration
public class JacksonConfig {
    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //忽略实体类中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //LocalDateTime系列序列化和反序列化模块，继承自jsr310
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 自定义 全局把返回时间转为 时间戳
        javaTimeModule.addSerializer(LocalDateTime.class, new TemporalAccessorToLongSerializer());
        javaTimeModule.addSerializer(LocalDate.class, new TemporalAccessorToLongSerializer());

        // 自定义 全局把入参时间转为 时间戳
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}

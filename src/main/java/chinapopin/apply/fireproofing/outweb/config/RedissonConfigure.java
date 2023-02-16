package chinapopin.apply.fireproofing.outweb.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Slf4j
@Configuration
public class RedissonConfigure {
    @Bean
    public RedissonClient redissonClient(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") String  port,
                                         @Value("${spring.redis.password}") String password) {
        Config config = new Config();
        // 单节点
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://"+host+":"+port);
        if(StringUtils.hasText(password)){
            singleServerConfig.setPassword(password);
        }
        // 使用json序列化方式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        config.setCodec(new JsonJacksonCodec(objectMapper));
        return Redisson.create(config);
    }
}

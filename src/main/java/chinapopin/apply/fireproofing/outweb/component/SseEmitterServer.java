package chinapopin.apply.fireproofing.outweb.component;


import chinapopin.apply.fireproofing.outweb.listen.SseTopicListener;
import chinapopin.apply.fireproofing.outweb.model.MsgModel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p>
 *
 * </p>
 *
 * @author caiqingzhou
 * @since 2023/2/15
 */
@Slf4j
@Component
public class SseEmitterServer {
    @Value("${platform.notice.redis.topic}")
    private String redisTopic;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SseTopicListener sseTopicListener;
    public static Map<String, SseEmitter> onlineUserEmittersMap = new HashMap<>(100);

    public static Map<String, SseEmitter> getOnlineUserEmittersMap() {
        return onlineUserEmittersMap;
    }

    public static Map<String, SseEmitter> onlineUserMap = new HashMap<>(100);

    public SseEmitter connect(String userId) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(this.completionCallBack(userId));
        sseEmitter.onError(this.onError(userId));
        sseEmitter.onTimeout(this.onTimeout(userId));
        onlineUserEmittersMap.put(userId, sseEmitter);
        RTopic topic = redissonClient.getTopic(redisTopic);
        if (topic.countListeners() == 0) {
            topic.addListener(MsgModel.class, sseTopicListener);
        }
        sseEmitter.send(String.format("%s号用户，欢迎登录！", userId));

        return sseEmitter;
    }

    private Runnable onTimeout(String userId) {
        return () -> {
            log.info("用户{}连接超时", userId);
        };
    }

    private Consumer<Throwable> onError(String userId) {
        return throwable -> {
            log.error("用户{}连接异常", userId);
            onlineUserMap.remove(userId);
        };
    }

    private Runnable completionCallBack(String userId) {
        return () -> {
            log.info("用户{}连接结束", userId);
            onlineUserMap.remove(userId);
        };
    }

    public void publish(MsgModel msgModel) {
        //todo持久化，避免redis导致消息丢失、避免用户不在线
        redissonClient.getTopic(redisTopic).publish(msgModel);
    }
}

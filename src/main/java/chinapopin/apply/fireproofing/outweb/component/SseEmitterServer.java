package chinapopin.apply.fireproofing.outweb.component;


import lombok.extern.slf4j.Slf4j;
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

    public static Map<String, SseEmitter> onlineUserMap = new HashMap<>(100);

    public SseEmitter connect(String userId) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(this.completionCallBack(userId));
        sseEmitter.onError(this.onError(userId));
        sseEmitter.onTimeout(this.onTimeout(userId));
        sseEmitter.send(String.format("%s号用户，欢迎登陆！", userId));
        onlineUserMap.put(userId, sseEmitter);
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

    public void publish(String userId, String msg) throws IOException {
        SseEmitter sseEmitter = onlineUserMap.get(userId);
        sseEmitter.send(msg);
    }
}

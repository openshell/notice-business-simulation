package chinapopin.apply.fireproofing.outweb.listen;

import chinapopin.apply.fireproofing.outweb.component.SseEmitterServer;
import chinapopin.apply.fireproofing.outweb.model.MsgModel;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
/**
 * <p>
 *
 * </p>
 *
 * @author caiqingzhou
 * @since 2023/2/16
 */
@Slf4j
@Component
public class SseTopicListener implements MessageListener<MsgModel> {
    @Override
    public void onMessage(CharSequence charSequence, MsgModel msgModel) {
        SseEmitter sseEmitter = SseEmitterServer.getOnlineUserEmittersMap().get(msgModel.getReceiveId());
        if (ObjectUtil.isNotNull(sseEmitter)) {
            try {
                sseEmitter.send(msgModel.getMsg());
            } catch (IOException e) {
                log.warn("站内信发送失败，用户：{}", msgModel.getReceiveId());
                e.printStackTrace();
            }
        }


    }
}

package chinapopin.apply.fireproofing.outweb.listen;
/**
 * 描述:
 * 包名:chinapopin.apply.fireproofing.outweb.listen
 * 版本信息: 版本1.0
 * 日期:2023/2/14
 * Copyright 成都三合力通科技有限公司
 */


import chinapopin.apply.fireproofing.outweb.component.SseEmitterServer;
import chinapopin.apply.fireproofing.outweb.model.MsgModel;
import chinapopin.apply.fireproofing.outweb.model.TaskInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author caiqingzhou/成都三合力通科技有限公司
 * @version v1.0
 * @describe
 * @date 2023/2/14 15:42
 */
@Slf4j
@Component
public class KafkaListen {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SseEmitterServer sseEmitterServer;

    @KafkaListener(topics = "platform-notice", groupId = "fireproofing")
    public void receiveMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {

        String value = record.value();
        try {
            List<TaskInfo> taskInfos = objectMapper.readValue(value, new TypeReference<List<TaskInfo>>() {
            });
            taskInfos.forEach(o -> {
                o.getReceiver().forEach(receive -> {
                    MsgModel msgModel = new MsgModel();

                    msgModel.setReceiveId(receive);
                    msgModel.setMsg(o.getContentModel().getContent());
                    sseEmitterServer.publish(msgModel);
                });
            });
        } catch (JsonProcessingException e) {
            log.warn("站内信发送失败：反序列化错误");
            e.printStackTrace();
        }
        acknowledgment.acknowledge();
    }
}

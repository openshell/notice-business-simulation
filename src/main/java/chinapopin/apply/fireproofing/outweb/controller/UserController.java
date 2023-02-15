package chinapopin.apply.fireproofing.outweb.controller;


import chinapopin.apply.fireproofing.outweb.component.SseEmitterServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * <p>
 * 
 * </p>
 *
 * @author caiqingzhou
 * @since 2023/2/15
 */
@RestController
public class UserController {

    @Autowired
    private SseEmitterServer sseEmitterServer;

    @GetMapping("/user/online/{userId}")
    public SseEmitter online(@PathVariable String userId) throws IOException {
        //示例代码，仅功能演示，异常建议捕获
        return sseEmitterServer.connect(userId);
    }

    @GetMapping("/user/publish/")
    public void publish(String userId, String msg) throws IOException {
        sseEmitterServer.publish(userId, msg);
    }
}

package chinapopin.apply.fireproofing.outweb.model;
/**
 * 描述:
 * 包名:com.chinapopin.platform.notice.shortmsg.common.pojo
 * 版本信息: 版本1.0
 * 日期:2023/2/13
 * Copyright 成都三合力通科技有限公司
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author caiqingzhou/成都三合力通科技有限公司
 * @version v1.0
 * @describe 消息任务信息
 * @date 2023/2/13 10:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {
    /**
     * 模板id
     */
    private Long templateId;
    /**
     * 模板类型
     */
    private Integer templateType;
    /**
     * 业务id
     */
    private Long businessId;
    /**
     * 消息接收者
     */
    private Set<String> receiver;
    /**
     * 接受者的id类型
     */
    private Integer idType;
    /**
     * 发送渠道
     */
    private Integer sendChannel;

    /**
     * 消息模型，针对不同消息有不同的内容
     */
    private ContentModel contentModel;
    /**
     * 发消息的账号
     */
    private Integer sendAccount;
}

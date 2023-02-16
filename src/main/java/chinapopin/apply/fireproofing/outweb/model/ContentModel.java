package chinapopin.apply.fireproofing.outweb.model;
/**
 * 描述:
 * 包名:com.chinapopin.platform.notice.shortmsg.common.model
 * 版本信息: 版本1.0
 * 日期:2023/2/13
 * Copyright 成都三合力通科技有限公司
 */


import lombok.Data;

import java.io.Serializable;

/**
 * @author caiqingzhou/成都三合力通科技有限公司
 * @version v1.0
 * @describe
 * @date 2023/2/13 10:31
 */
@Data
public class ContentModel implements Serializable {
    private static final long serialVersionUID = -8473299277981196058L;
    /**
     * 默认文本
     */
    private String content;
}

package chinapopin.apply.fireproofing.outweb.config;
/**
 * 描述:
 * 包名:com.chinapopin.zdr.config
 * 版本信息: 版本1.0
 * 日期:2022/4/28
 * Copyright 成都三合力通科技有限公司
 */


import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.temporal.TemporalAccessor;

/**
 * @author caiqingzhou/成都三合力通科技有限公司
 * @version v1.0
 * @describe
 * @date 2022/4/28 17:53
 */
public class TemporalAccessorToLongSerializer extends JsonSerializer<TemporalAccessor> {
    @Override
    public void serialize(TemporalAccessor temporalAccessor, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        long milli = LocalDateTimeUtil.toEpochMilli(temporalAccessor);
        jsonGenerator.writeNumber(milli);
    }
}
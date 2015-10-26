package com.perfect.commons.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xiaowei
 * @title java BigDecimal 转 double ，取出转double
 * @package com.perfect.core
 * @description
 * @update 2015年10月23日. 下午5:52
 */
@Component
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double aDouble) {
        return new BigDecimal(aDouble);
    }
}

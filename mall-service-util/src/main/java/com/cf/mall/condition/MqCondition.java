package com.cf.mall.condition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author chen
 * 2020/5/27
 */
public class MqCondition implements Condition {

    @Value("${spring.activemq.broker-url:disabled}")
    String brokerURL;

    @Value("${activemq.listener.enable:disabled}")
    String listenerEnable;
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        if (null == brokerURL|| null == listenerEnable || "disabled".equals(brokerURL) || "disabled".equals(listenerEnable)) {
            return false;
        }
        return true;
    }
}

package com.bosch.diabo.config;

import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;

public class JHipsterBlockHoundIntegration implements BlockHoundIntegration {

    @Override
    public void applyTo(BlockHound.Builder builder) {
        // Workaround until https://github.com/reactor/reactor-core/issues/2137 is fixed
        builder.allowBlockingCallsInside("reactor.core.scheduler.BoundedElasticScheduler$BoundedState", "dispose");
        builder.allowBlockingCallsInside("reactor.core.scheduler.BoundedElasticScheduler", "schedule");
        builder.allowBlockingCallsInside("org.springframework.validation.beanvalidation.SpringValidatorAdapter", "validate");
        builder.allowBlockingCallsInside("com.bosch.diabo.service.MailService", "sendEmailFromTemplate");
        builder.allowBlockingCallsInside("com.bosch.diabo.security.DomainUserDetailsService", "createSpringSecurityUser");
        builder.allowBlockingCallsInside("io.r2dbc.mssql.client.ssl.TdsSslHandler", "createSslHandler");
    }
}

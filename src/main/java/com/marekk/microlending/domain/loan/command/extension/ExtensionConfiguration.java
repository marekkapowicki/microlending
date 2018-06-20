package com.marekk.microlending.domain.loan.command.extension;

import com.marekk.microlending.MicrolendingProperties;
import com.marekk.microlending.infrastructure.Profiles;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableConfigurationProperties(MicrolendingProperties.class)
class ExtensionConfiguration {

    @Bean
    ExtensionsParameters extensionsParameters(MicrolendingProperties properties) {
        MicrolendingProperties.Extension extensionProperties = properties.getExtension();
        return new ExtensionsParameters(extensionProperties.getPlusDays(), extensionProperties.getInterestMultiplier());
    }

    @Bean
    ExtensionFacade loanExtensionFacade(ExtensionsParameters extensionsParameters,
                                        ExtensionCreatorRepository repository) {
        return new ExtensionFacade(new ExtensionFactory(extensionsParameters), repository);
    }

    @Bean
    @Profile(Profiles.TEST)
    ExtensionFacade loanExtensionFacade(ExtensionsParameters extensionsParameters) {
        return new ExtensionFacade(new ExtensionFactory(extensionsParameters),
                new InMemoryExtensionCreatorRepository());
    }

}

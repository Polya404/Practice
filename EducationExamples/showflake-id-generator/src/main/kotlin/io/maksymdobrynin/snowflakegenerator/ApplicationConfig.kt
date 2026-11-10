package io.maksymdobrynin.snowflakegenerator

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ApplicationConfig {
	@Bean
	@ConfigurationProperties(prefix = "snowflake-settings")
	open fun nexoDeviceHandlerSenderProperties(): GeneratorSettings = GeneratorSettings()
}

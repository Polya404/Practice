package org.dobmax.user.pool

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient

@Configuration
class AwsConfiguration(
    @Value("\${aws.region}") val region: Region,
) {
    @Bean
    fun identityProviderClient(): CognitoIdentityProviderClient {
        return CognitoIdentityProviderClient.builder()
            .region(region)
            .build()
    }
}

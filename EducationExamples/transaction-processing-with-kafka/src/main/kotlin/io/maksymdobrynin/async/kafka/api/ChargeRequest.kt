package io.maksymdobrynin.async.kafka.api

import jakarta.validation.constraints.Email
import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

class ChargeRequest {

    /**
     * The amount to be charged by this payment, in decimal format (e.g., 100.50 for $100.50).
     * Example: "1090.50"
     */
    var amount: BigDecimal? = null

    /**
     * Three-letter ISO currency code, in uppercase. Must conform with ISO 4217.
     * Example: "USD"
     */
    var currency: Currency? = null

    /**
     * Full name of the customer this payment belongs to.
     * Example: "Henry Ford"
     */
    var customer: String? = null

    /**
     * Unique identifier of the order this payment belongs to. Also used as idempotency key.
     * Example: "f47ac10b-58cc-4372-a567-0e02b2c3d479"
     */
    var order: UUID? = null

    /**
     * Email address to send the receipt to.
     * Example: "test@email.com"
     */
    @Email
    var receiptEmail: String? = null

    /**
     * Set of key-value pairs that you can attach to an object.
     */
    var metadata: Map<String, Any> = emptyMap()
}



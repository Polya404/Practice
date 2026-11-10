package io.maksymdobrynin.async.kafka.api

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

class ChargeResponse (

    /**
     * Unique identifier for the charge the inquired payment belongs to.
     * Must conform to UUID format.
     * Example: "d3b07384-d9a4-4c91-94e1-4a9b0a93cdbf"
     */
    private var id: UUID,

    /**
     * The amount to be charged by this payment, in decimal format (e.g., 100.50 for $100.50).
     * Example: "2000.00"
     */
    private var amount: BigDecimal,

    /**
     * Three-letter ISO currency code, in uppercase. Must conform with ISO 4217.
     * Example: "USD"
     */
    private var currency: String,

    /**
     * Amount eventually charged by this payment.
     * Example: "0.00"
     */
    private var amountReceived: BigDecimal,

    /**
     * Time at which the payment was created but not yet charged. Must conform to ISO 8601.
     * Example: "2011-12-03T10:15:30Z"
     */
    private var createdAt: OffsetDateTime,

    /**
     * Time at which the payment was charged. Must conform to ISO 8601.
     * Example: "2011-12-03T10:15:30Z"
     */
    private var chargedAt: OffsetDateTime,

    /**
     * Full name of the customer this payment belongs to.
     * Example: "Henry Ford"
     */
    private var customer: String,

    /**
     * Unique identifier of the order this payment belongs to.
     * Example: "d2c6c5aa-f17c-4333-88b6-1fc066c6c6c6"
     */
    private var order: UUID,

    /**
     * Email address to send the receipt to.
     * Example: "henry.ford@email.com"
     */
    private var receiptEmail: String,

    /**
     * Status of this payment: one of "processing", "canceled", or "succeeded".
     * Example: "processing"
     */
    private var status: String,

    /**
     * Set of key-value pairs that you can attach to a payment.
     */
    var metadata: Map<String, Any> = emptyMap(),
)

package com.gihub.jefersondeoliveira.dpop.dpop

import java.security.interfaces.RSAPublicKey

object JWKUtil {

    fun createJWK(publicKey: RSAPublicKey): String {
        val modulus = publicKey.modulus.toUnsignedByteArray().toBase64()
        val exponent = publicKey.publicExponent.toByteArray().toBase64()
        return """{"kty":"RSA","n":"$modulus","e":"$exponent"}"""
    }

}
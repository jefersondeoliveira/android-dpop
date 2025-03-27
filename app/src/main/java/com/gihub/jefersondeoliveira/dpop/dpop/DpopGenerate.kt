package com.gihub.jefersondeoliveira.dpop.dpop

import com.gihub.jefersondeoliveira.dpop.dpop.JWKUtil.createJWK
import com.gihub.jefersondeoliveira.dpop.dpop.KeyGeneratorUtil.generateKeyPair
import java.security.Signature
import java.security.interfaces.RSAPublicKey
import java.util.Date
import java.util.UUID

object DpopGenerate {

    fun createDpopProof(httpMethod: String, httpUri: String): String {

        val keyPair = generateKeyPair()

        val jti = UUID.randomUUID().toString()
        val iat = Date().time / 1000
        val publicKey = keyPair.public as RSAPublicKey

        val jwk = createJWK(publicKey)
        val header = """{"alg":"RS256","typ":"JWT","jwk":$jwk}"""
        val payload = """{"htm":"$httpMethod","htu":"$httpUri","jti":"$jti","iat":$iat}"""

        val signingInput = "${header.toByteArray().toBase64()}.${payload.toByteArray().toBase64()}"

        val signature = Signature.getInstance("SHA256withRSA").apply {
            initSign(keyPair.private)
            update(signingInput.toByteArray())
        }

        val signatureEncoded = signature.sign().toBase64()
        return "$signingInput.$signatureEncoded"
    }

}
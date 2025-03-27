package com.gihub.jefersondeoliveira.dpop.dpop

import java.security.KeyPair
import java.security.KeyPairGenerator

object KeyGeneratorUtil {

    fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }

}
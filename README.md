# DPoP Implementation in Android

This repository provides an example implementation of **DPoP (Demonstration of Proof-of-Possession)** in an Android application using **Jetpack Compose**.

## 📌 What is DPoP?

DPoP is a security mechanism that enhances OAuth 2.0 by preventing **token replay attacks**. It ensures that only the legitimate client can use an access token by binding it to a **cryptographic proof**.

### 🚀 Why Use DPoP?
- **Prevents replay attacks** – Stolen tokens cannot be reused.
- **Ensures integrity** – Authentication depends on cryptographic proof.
- **Enhances OAuth 2.0 security** – Strengthens existing authentication methods.

## 🛠️ Project Structure

The implementation is divided into the following components:

### 1️⃣ Generating an RSA Key Pair
```kotlin
import java.security.KeyPair
import java.security.KeyPairGenerator

object KeyGeneratorUtil {
    fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }
}
```

### 2️⃣ Creating a DPoP Proof (JWT)
```kotlin
import android.util.Base64
import java.security.KeyPair
import java.security.Signature
import java.security.interfaces.RSAPublicKey
import java.util.Date
import java.util.UUID

object DPoPUtil {
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
```

### 3️⃣ Handling RSA Key Conversion and utils extensions
```kotlin
import java.math.BigInteger

object JWKUtil {
    fun createJWK(publicKey: RSAPublicKey): String {
        val modulus = publicKey.modulus.toUnsignedByteArray().toBase64()
        val exponent = publicKey.publicExponent.toByteArray().toBase64()
        return "{\"kty\":\"RSA\",\"n\":\"$modulus\",\"e\":\"$exponent\"}"
    }
}

fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)

fun BigInteger.toUnsignedByteArray(): ByteArray {
    val byteArray = this.toByteArray()
    return if (byteArray[0] == 0.toByte()) byteArray.copyOfRange(1, byteArray.size) else byteArray
}

```


## 📲 How to Use

1️⃣ Clone this repository:

2️⃣ Open the project in **Android Studio**.

3️⃣ Run the application on an emulator or a physical device.

4️⃣ Click the **"Generate DPoP Token"** button to generate a valid DPoP proof.

## 📝 License

This project is licensed under the MIT License.

---

🔹 **Author:** Jeferson de Oliveira Lopes

🔹 **GitHub:** [jefersondeoliveira](https://github.com/jefersondeoliveira)

🔹 **LinkedIn:** [jefersondeoliveira](https://www.linkedin.com/in/jefersondeoliveira)

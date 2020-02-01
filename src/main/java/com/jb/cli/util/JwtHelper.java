package com.jb.cli.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/**
 * Helper class used to generate JWT token required by the API
 */
@Component
public class JwtHelper {

    /**
     * Reads private key file using specified filePath
     * TODO: Move to separate class
     *
     * @param filePath path to the private key
     * @return returns contents of file as a string
     * @throws IOException if file cannot be found or read
     */
    private String readFile(String filePath) throws IOException {
        //If path contains ~, replace it with system's user home directory
        filePath = filePath.replace("~", System.getProperty("user.home"));
        Path path = Paths.get(filePath);

        byte[] data = Files.readAllBytes(path);
        return new String(data);
    }

    /**
     * Helper method used to remove private key header, footer as well as any whitespace
     *
     * @param keyContent the private key content
     * @return returns the string containing the private key
     */
    private String pruneKey(String keyContent) {
        return keyContent.replace("-----BEGIN PRIVATE KEY-----\n", "")
                .replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");
    }

    /**
     * Reads private key from specified file path, prunes header and footer, encodes it and initializes Key object
     *
     * @param filePath the path to the private key
     * @return returns the key object
     * @throws Exception if not able to read key
     */
    private Key readPrivateKey(String filePath) throws Exception {
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        String keyContent = readFile(filePath);
        keyContent = pruneKey(keyContent);
        return rsa.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyContent)));
    }

    /**
     * Returns JWT token signed with private key and with the claims requierd by the API
     *
     * @param privateKeyPath path to the private key file
     * @param appId          the application ID
     * @return the JWT token as a string
     */
    public String createJWT(String privateKeyPath, String appId) {
        String token;
        try {
            JwtBuilder builder = Jwts.builder()
                    .setHeaderParam("type", "JWT")
                    .claim("application_id", appId)
                    .claim("iat", Instant.now().getEpochSecond())
                    .claim("jti", UUID.randomUUID().toString());
            Key privateKey = readPrivateKey(privateKeyPath);
            token = builder.signWith(SignatureAlgorithm.RS256, privateKey).compact();
        } catch (Exception exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new IllegalArgumentException("Unable to generate jwt token " + exception.getMessage(), exception);
        }
        return token;
    }

}

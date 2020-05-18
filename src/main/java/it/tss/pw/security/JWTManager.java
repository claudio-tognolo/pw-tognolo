/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.security;

/**
 *
 * @author claudio
 */
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import it.tss.pw.users.User;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author alfonso
 */
public class JWTManager {

    private static final String PRIVATE_KEY = "privateKey.pem";
    private static final String ISS = "it.tss.projectwork";

    public String generate(User usr) {

        try {
            JSONObject jwt = generateJWT(usr);

            JWSHeader header     = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .keyID(PRIVATE_KEY)
                    .type(JOSEObjectType.JWT)
                    .build();

            JWTClaimsSet claimSet   = JWTClaimsSet.parse(jwt);

            SignedJWT signedJWT = new SignedJWT(header,claimSet);

            PrivateKey privateKey = readPrivateKey(PRIVATE_KEY);
            
            RSASSASigner signer = new RSASSASigner(privateKey);
            
            signedJWT.sign(signer);

            return signedJWT.serialize();
            
        } catch (ParseException ex) {
            Logger.getLogger(JWTManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new JwtTokenException("Impossibile generare il JWT token ");
        } catch (Exception ex) {
            Logger.getLogger(JWTManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new JwtTokenException("Impossibile generare il JWT token ");
        }
    }

    private PrivateKey readPrivateKey(String resourceName) throws Exception {
        byte[] byteBuffer = new byte[16384];
        int length = Thread.currentThread().getContextClassLoader()
                .getResource(resourceName)
                .openStream()
                .read(byteBuffer);

        String key = new String(byteBuffer, 0, length).replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();

        byte[] decodedKey = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
    }

    private JSONObject generateJWT(User usr) {
        long currentTimeInSecs = (System.currentTimeMillis() / 1000);
        long expirationTime = currentTimeInSecs + 1000;
        /*
            JsonObject jwt =
                    Json.createObjectBuilder()
                            .add(Claims.iss.name(), ISS)
                            .add(Claims.iat.name(), currentTimeInSecs)
                            .add(Claims.auth_time.name(), currentTimeInSecs)
                            .add(Claims.exp.name(), expirationTime)
                            .add(Claims.upn.name(), usr)
                            .add(Claims.groups.name(), loadGroups(groups))
                            .build();
         */

        JSONObject jwt = new JSONObject();
        jwt.put(Claims.iss.name(), ISS);
        jwt.put(Claims.iat.name(), currentTimeInSecs);
        jwt.put(Claims.auth_time.name(), currentTimeInSecs);
        jwt.put(Claims.exp.name(), expirationTime);
        jwt.put(Claims.sub.name(), usr.getId().toString());
        jwt.put(Claims.upn.name(), usr.getUsr());
        jwt.put(Claims.groups.name(), loadGroups());
        return jwt;
    }

    private JSONArray loadGroups() {
        JSONArray result = new JSONArray();
        result.add("users");
        return result;
    }
}

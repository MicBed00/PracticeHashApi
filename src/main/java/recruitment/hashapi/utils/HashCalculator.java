package recruitment.hashapi.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class HashCalculator {

    public String hashCalc(String input) {
        byte[] mdUser = alg(input);
        return converterDigestToString(mdUser);
    };
     String converterDigestToString(byte[] digest) {
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<digest.length;i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        return hexString.toString();
    }

    byte[] alg(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(getAlgName());
        } catch (
                NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return md.digest(input.getBytes());
    }

    protected abstract String getAlgName();
}

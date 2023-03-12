package recruitment.hashapi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sh1Alg extends HashCalculator {

    @Override
    protected String getAlgName() {
        return "SHA-1";
    }
}

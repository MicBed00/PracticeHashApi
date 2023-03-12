package recruitment.hashapi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Alg extends HashCalculator{

    @Override
    protected String getAlgName() {
        return "MD5";
    }
}

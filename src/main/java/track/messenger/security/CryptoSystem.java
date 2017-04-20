package track.messenger.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CryptoSystem {

    private MessageDigest hasher;
    private String hashAlgorithm;
    private Long numberOfIterations;

    public CryptoSystem() {}

    public CryptoSystem(String hashAlgorithm) throws InstantiationException {
        setHashAlgorithm(hashAlgorithm);
    }

    public void setHashAlgorithm(String hashAlgorithm) throws InstantiationException {
        this.hashAlgorithm = hashAlgorithm;
        try {
            hasher = MessageDigest.getInstance(hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new InstantiationException("There isn't this method of code");
        }
    }

    public void setNumberOfIterations(Long numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public String encrypt(String value) {

        if (value == null) {
            return null;
        }
        for (int count = 0; count < numberOfIterations; count ++ ) {
            hasher.update((value).getBytes());
            value = new String(hasher.digest());
            hasher.reset();
        }
        return value;
    }

    public boolean check(String encrypted, String raw) {
        if (encrypted == null) {
            return false;
        } else {
            return (encrypted.compareTo(encrypt(raw)) == 0);
        }
    }


}

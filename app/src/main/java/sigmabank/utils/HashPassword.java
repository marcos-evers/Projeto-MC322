package sigmabank.utils;

import java.util.UUID;

public class HashPassword {
    public static String hashPassword(UUID clientUUID,String password) {
        return Sha3Util.sha3(clientUUID.toString() + password);
    }
}

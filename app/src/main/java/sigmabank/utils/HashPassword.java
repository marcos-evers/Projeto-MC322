package sigmabank.utils;

public class HashPassword {
    /**
     * Hashes the password using the SHA-3 256 algorithm.
     * It uses the cpf string as a salt for the begining of the string to be hashed.
     * 
     * @param cpf The CPF of the user
     * @param password The password to be hashed
     * @return The hashed password
     */
    public static String hashPassword(String cpf,String password) {
        return Sha3Util.sha3(cpf + password);
    }
}

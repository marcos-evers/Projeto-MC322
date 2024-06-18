package sigmabank.utils;

public class HashPassword {
    public static String hashPassword(String cpf,String password) {
        return Sha3Util.sha3(cpf + password);
    }
}

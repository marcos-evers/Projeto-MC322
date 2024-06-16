package sigmabank.utils;

public class DocumentValidator {

    /**
     * Validates a CPF number.
     * Conditions to be valid: CPF must have 11 digits, the first and the second verifying digit must be valid.
     * 
     * @param cpf the CPF number to be validated as a String.
     * @return true if the CPF number is valid, false otherwise.
     */
    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // remove all non-digit characters

        if (cpf.length()!= 11) {
            return false; // CPF must have 11 digits
        }

        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }

        int sum = 0;
        int weight = 10;

        for (int i = 0; i < 9; i++) {
            sum += digits[i] * weight;
            weight--;
        }

        int verifyingDigit1 = (sum * 10) % 11;
        if (verifyingDigit1 == 10 || verifyingDigit1 == 11) {
            verifyingDigit1 = 0;
        }

        if (verifyingDigit1!= digits[9]) {
            return false;
        }

        sum = 0;
        weight = 11;

        for (int i = 0; i < 10; i++) {
            sum += digits[i] * weight;
            weight--;
        }

        int verifyingDigit2 = (sum * 10) % 11;
        if (verifyingDigit2 == 10 || verifyingDigit2 == 11) {
            verifyingDigit2 = 0;
        }

        return verifyingDigit2 == digits[10];
    }

    /**
     * Validates a CNPJ number.
     * Conditions to be valid: CNPJ must have 14 digits, the first and the second verifying digit must be valid.
     * 
     * @param cnpj the CNPJ number to be validated as a String.
     * @return true if the CNPJ number is valid, false otherwise.
     */
    public static boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", ""); // remove all non-digit characters
    
        if (cnpj.length()!= 14) {
            return false; // CNPJ must have 14 digits
        }
    
        int[] digits = new int[14];
        for (int i = 0; i < 14; i++) {
            digits[i] = Integer.parseInt(cnpj.substring(i, i + 1));
        }
    
        int sum = 0;
        int weight = 5;
    
        for (int i = 0; i < 12; i++) {
            sum += digits[i] * weight;
            weight--;
            if (weight < 2) {
                weight = 9;
            }
        }
    
        int verifyingDigit1 = (sum * 10) % 11;
        if (verifyingDigit1 == 10 || verifyingDigit1 == 11) {
            verifyingDigit1 = 0;
        }
    
        if (verifyingDigit1!= digits[12]) {
            return false;
        }
    
        sum = 0;
        weight = 6;
    
        for (int i = 0; i < 13; i++) {
            sum += digits[i] * weight;
            weight--;
            if (weight < 2) {
                weight = 9;
            }
        }
    
        int verifyingDigit2 = (sum * 10) % 11;
        if (verifyingDigit2 == 10 || verifyingDigit2 == 11) {
            verifyingDigit2 = 0;
        }
    
        return verifyingDigit2 == digits[13];
    }
}
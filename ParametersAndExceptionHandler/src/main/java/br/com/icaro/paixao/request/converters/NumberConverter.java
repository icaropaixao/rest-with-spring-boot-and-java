package br.com.icaro.paixao.request.converters;

import br.com.icaro.paixao.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public  static Double convertToDouble(String strNumber) throws UnsupportedMathOperationException {

        // Se a string for nula ou vazia, lança exceção
        if (strNumber == null || strNumber.isEmpty())
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        // Substitui vírgulas por pontos para padronizar números decimais
        String number = strNumber.replace(",","."); // R$ 5,00 USD 5.00

        // Converte a string para Double e retorna
        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {

        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",","."); // R$ 5,00 USD 5.00
        return number.matches("[-+]?[0-9]*\\.?[0-9]+"); // Usa expressão regular (regex) para verificar se a string é um número válido

    }

}

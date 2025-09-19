package br.com.icaro.paixao.controllers;

import br.com.icaro.paixao.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")


public class MathController {

    // http://localhost:8080/math/sum/3/5
    @RequestMapping("sum/{numberOne}/{numberTwo}")
    public Double sum (
            @PathVariable("numberOne") String numberOne, // Captura o primeiro valor da URL
            @PathVariable("numberTwo") String numberTwo //  Captura o segundo valor da URL
    ) throws UnsupportedMathOperationException {
        // Valida se os dois valores são numéricos, senão lança exceção
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        // Converte os valores para Double e retorna a soma
        return convertToDouble(numberOne) + convertToDouble(numberTwo);

    }

    private Double convertToDouble(String strNumber) throws UnsupportedMathOperationException {

        // Se a string for nula ou vazia, lança exceção
        if (strNumber == null || strNumber.isEmpty())
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        // Substitui vírgulas por pontos para padronizar números decimais
        String number = strNumber.replace(",","."); // R$ 5,00 USD 5.00

        // Converte a string para Double e retorna
        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strNumber) {

        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",","."); // R$ 5,00 USD 5.00
        return number.matches("[-+]?[0-9]*\\.?[0-9]+"); // Usa expressão regular (regex) para verificar se a string é um número válido

    }


}

package br.com.icaro.paixao.controllers;

import br.com.icaro.paixao.exception.UnsupportedMathOperationException;
import br.com.icaro.paixao.math.SimpleMath;
import br.com.icaro.paixao.request.converters.NumberConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.icaro.paixao.request.converters.NumberConverter.isNumeric;

@RestController
@RequestMapping("/math")
public class MathController {

    private SimpleMath math = new SimpleMath();

    // http://localhost:8080/math/sum/3/5
    @RequestMapping("sum/{numberOne}/{numberTwo}")
    public Double sum (
            @PathVariable("numberOne") String numberOne, // Captura o primeiro valor da URL
            @PathVariable("numberTwo") String numberTwo //  Captura o segundo valor da URL
    ) throws UnsupportedMathOperationException {
        // Valida se os dois valores são numéricos, senão lança exceção
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("PLease set a numeric value");


        return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));

    }

    // http://localhost:8080/math/subtraction/3/5
    @RequestMapping("subtraction/{numberOne}/{numberTwo}")
    public Double subtraction (
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo

    ) throws UnsupportedMathOperationException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));

    }


    // http://localhost:8080/math/multiply/3/5
    @RequestMapping("multiply/{numberOne}/{numberTwo}")
    public Double multiply (
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo

    ) throws UnsupportedMathOperationException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        return math.multtply(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));

    }

    // http://localhost:8080/math/division/3/5
    @RequestMapping("division/{numberOne}/{numberTwo}")
    public Double division (
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo

    ) throws UnsupportedMathOperationException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));

    }

    // http://localhost:8080/math/average/3/5
    @RequestMapping("average/{numberOne}/{numberTwo}")
    public Double average (
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo

    ) throws UnsupportedMathOperationException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        return math.average((NumberConverter.convertToDouble(numberOne)), NumberConverter.convertToDouble(numberTwo));

    }

    // http://localhost:8080/math/source/3
    @RequestMapping("source/{numberOne}")
    public Double source (
            @PathVariable("numberOne") String numberOne
    ) throws UnsupportedMathOperationException {
        if (!isNumeric(numberOne))
            throw new UnsupportedMathOperationException("PLease set a numeric value");

        return math.source(NumberConverter.convertToDouble(numberOne));

    }


}

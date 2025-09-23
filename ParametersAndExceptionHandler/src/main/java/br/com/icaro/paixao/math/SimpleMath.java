package br.com.icaro.paixao.math;

import br.com.icaro.paixao.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class SimpleMath {

    public Double sum (Double numberOne, Double numberTwo)  {
        return numberOne + numberTwo;

    }

    public Double subtraction (Double numberOne, Double numberTwo)  {
        return numberOne - numberTwo;

    }

    public Double multtply (Double numberOne, Double numberTwo)  {
        return numberOne * numberTwo;

    }

    public Double division (Double numberOne, Double numberTwo)  {
        return numberOne / numberTwo;

    }

    public Double average (Double numberOne, Double numberTwo)  {

        double calc = 2;

        return (numberOne + numberTwo) / calc;

    }

    public Double source (Double number)  {
        return  Math.sqrt(number);

    }


}




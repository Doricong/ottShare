package com.project.ottshare.aop;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.stream.IntStream;

public class CustomSpringELParser {

    public static String getDynamicValue(String[] paramNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        IntStream.range(0, paramNames.length)
                .forEach(i -> context.setVariable(paramNames[i], args[i]));

        return parser.parseExpression(key).getValue(context, String.class);
    }
}
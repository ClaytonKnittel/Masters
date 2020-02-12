package src;

// https://stackoverflow.com/questions/11765996/aspectj-pointcut-expression-match-parameter-annotations-at-any-position

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Instruction {}



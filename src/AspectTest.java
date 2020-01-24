package src;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class AspectTest {
    
    @Before("execution (* *.print*(..))")
    public void trace(JoinPoint jp) {
        System.out.println("Before saying that '" + jp + "'");
    }
}


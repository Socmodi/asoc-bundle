package org.asocframework.bundle.continuation;


import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 * @author jiqing
 * @version $Id:ContinuationTest , v1.0 2020/10/20 下午2:44 jiqing Exp $
 * @desc
 */
public class ContinuationTest {


    public static void main(String[] args) {


        Fiber fiber = new Fiber<Void>() {
            @Override
            protected Void run() throws SuspendExecution, InterruptedException {
                System.out.println("Hello Fiber");
                return null;
            }};
        fiber.start();
    }

}

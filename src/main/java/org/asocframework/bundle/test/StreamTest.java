package org.asocframework.bundle.test;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiqing
 * @version $Id:StreamTest , v1.0 2020/5/10 下午5:22 jiqing Exp $
 * @desc
 */
public class StreamTest {

    public static void main(String[] args) {
        List list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 8, 0, 1);
        list.stream();
    }

}

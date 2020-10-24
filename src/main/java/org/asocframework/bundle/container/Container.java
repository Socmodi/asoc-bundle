package org.asocframework.bundle.container;

import org.springframework.context.ApplicationContext;

/**
 * @author jiqing
 * @version $Id:Container , v1.0 2020/6/19 下午4:43 jiqing Exp $
 * @desc
 */
public interface Container {
    /**
     *
     * @return
     */
    String getName();

    /**
     *
     * @return
     */
    String getPath();

    /**
     *
     * @return
     */
    String getType();

    /**
     *
     * @return
     */
    ClassLoader getClassLoader();

    /**
     *
     * @return
     */
    ApplicationContext getSpringApplicationContext();

    /**
     *
     * @return
     */
    String getVersion();

}

package org.asocframework.bundle.container;

import org.asocframework.bundle.classloader.ContainerClassLoader;
import org.springframework.context.ApplicationContext;

/**
 * @author jiqing
 * @version $Id:BaseContainer , v1.0 2020/6/19 下午4:52 jiqing Exp $
 * @desc
 */
public abstract class BaseContainer implements Container {

    private String name;

    private String path;

    private String verison;

    private ClassLoader parentClassLoader;

    private ClassLoader classLoader;

    private ApplicationContext applicationContext;

    private ApplicationContext parentContext;

    public BaseContainer(String name, String path, String verison, ClassLoader parentClassLoader,
                         ApplicationContext parentContext) {
        this.name = name;
        this.path = path;
        this.verison = verison;
        this.parentClassLoader = parentClassLoader;
        this.classLoader = classLoader;
        this.applicationContext = applicationContext;
        this.parentContext = parentContext;
        //this.classLoader = new ContainerClassLoader();
    }
}

package org.asocframework.bundle.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author jiqing
 * @version $Id:ContainerClassLoader , v1.0 2020/6/23 下午4:26 jiqing Exp $
 * @desc
 */
public class ContainerClassLoader extends URLClassLoader {

    public ContainerClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
}

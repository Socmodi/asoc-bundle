package org.asocframework.bundle.container;

import org.springframework.context.ApplicationContext;

/**
 * @author jiqing
 * @version $Id:CommonContainer , v1.0 2020/6/19 下午4:57 jiqing Exp $
 * @desc
 */
public class CommonContainer extends BaseContainer{

    public CommonContainer(String name, String path, String verison, ClassLoader parentClassLoader, ApplicationContext parentContext) {
        super(name, path, verison, parentClassLoader, parentContext);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public ApplicationContext getSpringApplicationContext() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

}

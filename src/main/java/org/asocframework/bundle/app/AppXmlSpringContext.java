package org.asocframework.bundle.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.util.Arrays;

/**
 * @author jiqing
 * @version $Id:AppXmlSpringContext , v1.0 2019/4/12 下午12:01 jiqing Exp $
 * @desc
 */
public class AppXmlSpringContext extends AbstractXmlApplicationContext {

    private Resource[] resources;

    public AppXmlSpringContext(Resource[] resources, boolean refresh, ApplicationContext parent) {
        super(parent);
        this.resources = resources;
        if (refresh) {
            refresh();
        }
    }

    @Override
    protected Resource[] getConfigResources() {
        return this.resources;
    }

    @Override
    public String toString() {
        return "AppXmlSpringContext{" +
                "resources=" + Arrays.toString(resources) +
                '}';
    }
}

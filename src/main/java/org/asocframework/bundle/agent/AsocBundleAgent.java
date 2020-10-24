package org.asocframework.bundle.agent;

import org.asocframework.bundle.app.AppContext;
import org.asocframework.bundle.utils.JarFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.Set;

/**
 * @author jiqing
 * @version $Id:AsocBundleAgent , v1.0 2019/5/13 下午5:11 jiqing Exp $
 * @desc
 */
public class AsocBundleAgent {

    //private static Logger logger = LoggerFactory.getLogger(AsocBundleAgent.class);

    public static void agentmain(String agentArgs, Instrumentation inst) {
        AsocBundleTransformer transformer = new AsocBundleTransformer();
        try {
            inst.addTransformer(transformer, true);
            inst.retransformClasses(AppContext.class);
            System.out.println("sdfsdfsf");
            System.out.println(agentArgs);

            /**
             * 通过热部署模块与
             */
/*
            Set<String> hotPatchClasses = JarFileUtils.extractClassNameFromJarFile(agentArgs);
            Class[] allLoadedClasses = inst.getAllLoadedClasses();
            for (Class clazz : allLoadedClasses) {
                if (hotPatchClasses.contains(clazz.getName())) {
                    try {
                        inst.retransformClasses(clazz);
                    } catch (Throwable e) {
                        //logger.error("transformer class error", e);
                    }
                }
            }
*/

        } catch (Exception e) {

        } finally {
            inst.removeTransformer(transformer);
        }
    }

}

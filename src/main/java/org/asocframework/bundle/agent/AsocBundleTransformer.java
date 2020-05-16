package org.asocframework.bundle.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author jiqing
 * @version $Id:AsocBundleTransformer , v1.0 2019/5/13 下午8:10 jiqing Exp $
 * @desc
 */
public class AsocBundleTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            System.out.println("x:" + className);
            ClassPool classPool = new ClassPool();
            classPool.appendClassPath(new LoaderClassPath(loader));
            final CtClass ctClass;
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(classfileBuffer)) {
                ctClass = classPool.makeClass(byteArrayInputStream);
            }
            ctClass.getDeclaredMethod("toString").setBody("return \""+className+" hot attach"+"\";");
            //return new byte[0];
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}

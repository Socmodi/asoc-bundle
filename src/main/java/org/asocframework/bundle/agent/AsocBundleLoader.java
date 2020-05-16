package org.asocframework.bundle.agent;

import org.asocframework.bundle.app.AppContext;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiqing
 * @version $Id:AsocBundleLoader , v1.0 2019/5/13 下午5:31 jiqing Exp $
 * @desc
 */
public class AsocBundleLoader {

    private static Lock lock = new ReentrantLock();

    private String bundleParh ="dfsdfsd";

    public void load() {
        try {
            lock.tryLock(1, TimeUnit.MINUTES);
            String pid = getCurrentProcessId();
            Class<?> virtualMachineClass    = Class.forName("com.sun.tools.attach.VirtualMachine");
            Method attachMethod           = virtualMachineClass.getDeclaredMethod("attach", String.class);
            Object   virtualMachineInstance = attachMethod.invoke(null, pid);
            Method   loadAgentMethod        = virtualMachineClass.getDeclaredMethod("loadAgent", String.class, String.class);
            loadAgentMethod.invoke(virtualMachineInstance, "/Users/jiqing/workspace/asoc-bundle/target/asoc-bundle-1.0-SNAPSHOT.jar", bundleParh);
            Method detachMethod = virtualMachineClass.getDeclaredMethod("detach");
            detachMethod.invoke(virtualMachineInstance);
           /* VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent("/Users/jiqing/repository/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar", "");
*/        } catch (Exception e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
    }

    private String getCurrentProcessId() {
        String vmName = ManagementFactory.getRuntimeMXBean().getName();
        if (vmName == null || vmName.isEmpty()) {
            return null;
        }
        String[] str = vmName.split("@");
        return str[0];
    }

}

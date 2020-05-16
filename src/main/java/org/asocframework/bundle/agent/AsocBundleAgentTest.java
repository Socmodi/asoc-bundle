package org.asocframework.bundle.agent;

import org.asocframework.bundle.app.AppContext;

/**
 * @author jiqing
 * @version $Id:AsocBundleAgentTest , v1.0 2019/5/13 下午5:25 jiqing Exp $
 * @desc
 */
public class AsocBundleAgentTest {

    private static AppContext appContext = new AppContext();

    public static void main(String[] args){
        try {
            AsocBundleLoader loader = new AsocBundleLoader();
            loader.load();
            System.out.println(appContext);
        }catch (Exception e){
            System.out.println(e);
        }
    }

}

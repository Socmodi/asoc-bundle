/**
 * @author jiqing
 * @version $Id:package-info , v1.0 2019/4/10 下午4:26 jiqing Exp $
 * @desc
 */
package org.asocframework.bundle;

/**
 * 解决业务容器化发布+hotPatch
 * 容器化思路：
 * 1.隔离化 ：包括资源目录隔离化；运行时隔离(类加载隔离+Spring上下文隔离)
 * 2.容器管理：生命周期管理 健康状态管理 对外操作管理
 * 3.负载均衡
 * */
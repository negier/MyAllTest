package com.xuebinduan.design_pattern.singleton;

/**
 * DoubleCheckLock
 * 使用频率最高的单例实现，适合绝大多数应用场景，但不适合高并发场景
 */
public class SingletonDCL {
    private static volatile SingletonDCL sInstance = null;

    private SingletonDCL() {
    }

    public void doSomething() {
        System.out.println("do sth.");
    }

    public static SingletonDCL getInstance() {
        if (sInstance == null) {
            synchronized (SingletonDCL.class) {
                if (sInstance == null) {
                    sInstance = new SingletonDCL();
                }
            }
        }
        return sInstance;
    }
}

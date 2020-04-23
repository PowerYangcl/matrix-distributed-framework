package com.matrix.sxt.e15;

import com.matrix.sxt.e14.HelloGc;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

public class MetaspaceDemo {
    public static void main(String[] args) {
        int i = 0;
        try {
            while (true){
                i++;
                Enhancer enhancer = new Enhancer();   // cglib动态字节码技术，单独章节介绍
                enhancer.setSuperclass(HelloGc.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o , args);
                    }
                });
                enhancer.create();
            }
        }catch (Throwable e){
            System.out.println("类创建了【" + i + "】次后发生异常");
            e.printStackTrace();
        }
    }
}

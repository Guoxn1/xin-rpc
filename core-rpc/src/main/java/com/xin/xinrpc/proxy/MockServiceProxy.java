package com.xin.xinrpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ClassName: MockServiceProxy
 * Package: com.xin.xinrpc.proxy
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/27 下午7:46
 * @Version 1.0
 */
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型， 生成特定的对象
        Class<?> returnType = method.getReturnType();
        System.out.println("mock invoke "+ method.getName());
        return getDefaultObject(returnType);
    }

    private Object getDefaultObject(Class<?> returnType) {
        if(returnType.isPrimitive()){
            if(returnType == int.class){
                return 0;
            }
            else if(returnType == long.class){
                return 0L;
            }else if (returnType == boolean.class){
                return false;
            }else if (returnType == double.class){
                return 0.0;
            }
        }
        return null;
    }
}

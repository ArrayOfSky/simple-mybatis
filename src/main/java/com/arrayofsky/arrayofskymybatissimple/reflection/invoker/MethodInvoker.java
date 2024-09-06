package com.arrayofsky.arrayofskymybatissimple.reflection.invoker;

import java.lang.reflect.Method;

/**
 * @description 方法调用者
 */
public class MethodInvoker implements Invoker {

    private Class<?> type;
    private Method method;

    public MethodInvoker(Method method) {
        this.method = method;

        // 如果只有一个参数，返回参数类型，否则返回 return 类型 暂时这么约定俗成
        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        // 调用实际method方法的执行
        return method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }

}

package com.arrayofsky.arrayofskymybatissimple.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @description setter 调用者
 */
public class SetFieldInvoker implements Invoker {

    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        //这里使用args[0]是因为通常在调用setter方法时，只需要传递一个参数作为要设置的值。
        // 最后返回null，因为setter方法不需要返回值。
        field.set(target, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

}
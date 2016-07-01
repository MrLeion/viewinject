package com.john.viewutils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JOHN
 * @Date 2016/6/17 16:38
 * @Description ${TODO}
 * @update $author$
 * 更新时间 2016/6/17$
 * 更新描述 ${TODO}
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {

    int value();

}

package com.john.viewutils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JOHN
 * @Date 2016/6/17 16:40
 * @Description ${TODO}
 * @update $author$
 * 更新时间 2016/6/17$
 * 更新描述 ${TODO}
 */
public class ViewUtils {


    public static void inject(Activity activity) {

        /**
         *绑定View成员变量
         */
        try {
            bindView(activity);

            bindClick(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }


    /**
     * 通过@ViewInject(R.id.XX)绑定view给对应成员变量
     *
     * @param activity activity组件
     * @throws IllegalAccessException
     */
    private static void bindView(Activity activity) throws IllegalAccessException {

        /**
         * 1.获取类的字节码文件
         */

        Class clazz = activity.getClass();

        /**
         * 2.获取所有字段
         */

        Field[] fields = clazz.getDeclaredFields();
        /**
         * 遍历所有的成员变量，将带有注解的成员变量和对应的view进行绑定
         */
        for (Field field : fields) {
            /**
             * 3.获取成员变量的注解
             */
            ViewInject viewInject = field.getAnnotation(ViewInject.class);

            if (viewInject != null) {

                /**
                 * 4.通过注解获取对应的资源id
                 */
                int resId = viewInject.value();


                /**
                 * 5.通过activity获取对应的view
                 */
                View view = activity.findViewById(resId);

                /**
                 * 6.将对应的view 设置给当前的成员变量
                 */

                //暴力反射
                field.setAccessible(true);
                field.set(activity, view);


            } else {
                //TODO do nothing
            }
        }
    }

    private static void bindClick(final Activity activity) {
        /**
         * 1.获取类的字节码文件
         */

        Class clazz = activity.getClass();

        /**
         * 2.获取所有方法
         */
        Method[] methods = clazz.getDeclaredMethods();


        /**
         * 遍历所有的所有方法，将带有注解的方法和对应的方法进行绑定
         */
        for (final Method method : methods) {
            /**
             * 3.获取方法的注解
             */
            final OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {

                /**
                 * 4.通过注解获取对应的资源id
                 */
                int resId = onClick.value();


                /**
                 * 5.通过activity获取对应的view
                 */
                final View view = activity.findViewById(resId);

                /**
                 * 6.给对应的view设置点击事件
                 */
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        method.setAccessible(true);
                        try {
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });


            } else {
                //TODO do nothing
            }
        }
    }
}

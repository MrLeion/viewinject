# viewinject
通过开源整个项目主要是学习git的使用，然后自我感觉类似于想ButterKnife以及Xutils的这个页面注入功能还是挺牛逼的，
所有希望更多的哥们可以和我一起完成这个高仿版butterKnife,谢谢！
当然，具体的使用也会在下面的代码中个大家演示一下：

```
package com.john.viewinjectdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.john.viewutils.OnClick;
import com.john.viewutils.ViewInject;
import com.john.viewutils.ViewUtils;

public class MainActivity extends AppCompatActivity {


    /**
     * 通过ViewInject可以后去到TextView的对象
     */
    @ViewInject(R.id.tv)
    private TextView mTv;
    private Handler  mSubHandler;
    private Looper mLooper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *真正去绑定控件和当前类中的成员变量
         */
        ViewUtils.inject(this);


        Log.d("tag", "text:" + mTv.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mSubHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                Toast.makeText(MainActivity.this, msg.obj.toString(),
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                mLooper = Looper.myLooper();
                /**
                 * 1.开启一个死循环，这样子线程就可以将自己的生命周期延迟到无限，从而导致了内存泄露的问题
                 */
                Looper.loop();
                Log.d("MY_TAG", "Looper 终止了！");
            }
        }).start();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //避免Handler的内存泄露问题
        if (mLooper!=null) {
            mLooper.quit();
            mLooper = null;
        }
    }

    @OnClick(R.id.btn)
    private void click(View view) {
        Toast.makeText(this, "我被点击了", Toast.LENGTH_SHORT).show();

    }


    @OnClick(R.id.btn_send)
    private void send(View view) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = "来自主线程的问候！！！";
        mSubHandler.sendMessage(msg);
    }
}

```

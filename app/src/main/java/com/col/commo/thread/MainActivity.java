package com.col.commo.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView tvCount;
    private int count = 0;
    private Handler handler;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button2);

//        handler = new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 0x100:
//                        tvCount.setText("当前count的值为：" + msg.arg1);
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        };
        tvCount = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (task == null) {
                    task = new Task();
                }
                task.execute(100);

            }
        });
    }

    private class Task extends AsyncTask<Integer,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Integer... params) {
            for(int i =0 ; i <= 100; i ++){
                count += i;
                publishProgress(i);
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled()) {
                    return null;
                }
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(progress);
            if (isCancelled()) {
                return;
            }

            tvCount.setText("当前count的值为：" + count);

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            tvContent.setText(result);
            button.setEnabled(true);
//            btnCancel.setEnabled(false);
            task = null;
        }

        /**
         * AsyncTask.cancel()并不意味着AsyncTask对象立即停止
         * 会在doInBackground()返回后触发onCanceled，而不是onPostExecute
         * 可以在doInBackground中定期调用AsyncTask.isCancelled()来检查，以便及早返回
         */
        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
            button.setEnabled(true);
//            btnCancel.setEnabled(false);
//            progressBar.setProgress(0);
//            tvContent.setText("已停止");
        }
    }

}

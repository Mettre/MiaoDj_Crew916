package cn.chenhai.miaodj_monitor.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class DownServer {

    private Context context;
    private String Url;
    private String name;
    private int totalSize;//总大小
    private int FileLength;
    private InputStream inputStream;
    private URLConnection connection;
    private OutputStream outputStream;

    private ProgressDialog progressDialog;

    private String savePAth;//文件夹
    private String savePathString;//文件名

    private Thread thread;
    public DownComplete mListener;

    public DownServer(Context context, String Url, String name) {
        this.context = context;
        this.Url = Url;
        this.name = name;
        savePAth = Environment.getExternalStorageDirectory() + "/DownFile";
        savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" + name + ".pdf";
    }


    public void downStar() {
        // TODO Auto-generated method stub
        thread = new Thread() {
            public void run() {
                try {
                    DownFile(Url);

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        };
        thread.start();
    }


    private void DownFile(String urlString) throws IOException {

		/*
         * 连接到服务器
		 */

        try {
            URL url = new URL(urlString);
            connection = url.openConnection();
            if (connection.getReadTimeout() == 5) {
                Log.i("---------->", "当前网络有问题");
                // return;
            }
            inputStream = connection.getInputStream();

        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		/*
         * 文件的保存路径和和文件名其中Nobody.mp3是在手机SD卡上要保存的路径，如果不存在则新建
		 */
        File file1 = new File(savePAth);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File file = new File(savePathString);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*
         * 向SD卡中写入文件,用Handle传递线程
		 */
        Message message = new Message();
        try {
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 4];
            FileLength = connection.getContentLength();
            message.what = 0;
            int readsize = 0;
            while ((readsize = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, readsize);
                totalSize += readsize;
                Log.i("-------->", totalSize + "");
                Message message1 = new Message();
                message1.what = 1;
            }
            outputStream.close();
            inputStream.close();
            Message message2 = new Message();
            SharedPrefsUtil.putValue(context, Url, true);//Url
            mListener.DownComplete(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block

            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            e.printStackTrace();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(false);
    }

    public void setmListener(DownComplete mListener) {
        this.mListener = mListener;
    }

    //下载完成
    public interface DownComplete {
        void DownComplete(boolean flog);
    }


}

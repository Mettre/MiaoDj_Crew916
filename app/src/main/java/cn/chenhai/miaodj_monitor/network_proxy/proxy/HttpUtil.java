package cn.chenhai.miaodj_monitor.network_proxy.proxy;

/**
 * Created by ChenHai--霜华 on 2016/5/3
 * 邮箱：248866527@qq.com
 */
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求的工具类
 *
 * @author zhy
 *
 */
public class HttpUtil
{

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack
    {
        void onRequestComplete(String result);
    }


    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack)
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    String result = doGet(urlStr);
                    if (callBack != null)
                    {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * 异步的Post请求
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) throws Exception
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    String result = doPost(urlStr, params);
                    if (callBack != null)
                    {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            };
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr)
    {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try
        {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200)
            {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1)
                {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else
            {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (baos != null)
                    baos.close();
            } catch (IOException e)
            {
            }
            conn.disconnect();
        }

        return null ;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type","multipart/form-data");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            //for (String key : rawParams.keySet())
            //{
                //String param = rawParams.get(key);
            if (param != null && !param.trim().equals(""))
            {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            //}

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String doPost(String url, JSONObject param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type","multipart/form-data");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            //for (String key : rawParams.keySet())
            //{
            //String param = rawParams.get(key);
            if (param != null && !param.equals(""))
            {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            //}

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
//public class HttpUtil {
//    // 创建HttpClient对象
//    public static HttpClient httpClient = new DefaultHttpClient();
//    public static final String BASE_URL = "http://192.168.3.14:8090/HelloWord/";
//
//    /**
//     *
//     * @param url
//     *            发送请求的URL
//     * @return 服务器响应字符串
//     * @throws Exception
//     */
//    public static String getRequest(String url) throws Exception {
//        // 创建HttpGet对象。
//        HttpGet get = new HttpGet(url);
//        // 发送GET请求
//        HttpResponse httpResponse = httpClient.execute(get);
//        // 如果服务器成功地返回响应
//        if (httpResponse.getStatusLine().getStatusCode() == 200) {
//            // 获取服务器响应字符串
//            String result = EntityUtils.toString(httpResponse.getEntity());
//            return result;
//        } else {
//            Log.d("服务器响应代码", (new Integer(httpResponse.getStatusLine()
//                    .getStatusCode())).toString());
//            return null;
//        }
//    }
//
//    /**
//     *
//     * @param url
//     *            发送请求的URL
//     * @param rawParams
//     *            请求参数
//     * @return 服务器响应字符串
//     * @throws Exception
//     */
//    public static String postRequest(String url, Map<String, String> rawParams)
//            throws Exception {
//        // 创建HttpPost对象。
//        HttpPost post = new HttpPost(url);
//        // 如果传递参数个数比较多的话可以对传递的参数进行封装
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        for (String key : rawParams.keySet()) {
//            // 封装请求参数
//            params.add(new BasicNameValuePair(key, rawParams.get(key)));
//        }
//        // 设置请求参数
//        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//        // 发送POST请求
//        HttpResponse httpResponse = httpClient.execute(post);
//        // 如果服务器成功地返回响应
//        if (httpResponse.getStatusLine().getStatusCode() == 200) {
//            // 获取服务器响应字符串
//            String result = EntityUtils.toString(httpResponse.getEntity());
//            return result;
//        }
//        return null;
//    }
//
//}

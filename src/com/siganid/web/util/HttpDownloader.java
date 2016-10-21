/*本类是http的访问类主要是控制访问时的操作*/

package com.siganid.web.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * 下载类
 *
 * @author Administrator
 */
public class HttpDownloader {
    private URL url = null;
    public static long fileMaxLength;

    public static String download(String urlStr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        byte[] all = null;
        try {
            String redirectUrl = getRedirectUrl(urlStr);
            if (redirectUrl == null || redirectUrl.equals(urlStr)) {
                redirectUrl = urlStr;
            } else {
                String otherPart = redirectUrl.replace(urlStr + "/", "");
                String afterEncode = new String(otherPart.getBytes("ISO-8859-1"), "utf8");
                redirectUrl = urlStr + "/" + afterEncode;
                redirectUrl = redirectUrl.replace("#_=_", "");
            }
            URL url = new URL(redirectUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();

            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setInstanceFollowRedirects(true);
            urlConn
                    .addRequestProperty(
                            "Accept",
                            "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/x-shockwave-flash, */*");
            urlConn.addRequestProperty("Accept-Language", "zh-cn;q=0.5");
            urlConn.setRequestProperty("Accept-Charset", "utf-8");
            urlConn.setRequestProperty("content-type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            urlConn.setRequestProperty("Charset", "utf-8");
            urlConn
                    .addRequestProperty(
                            "User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)");

            urlConn.setConnectTimeout(15000);
            urlConn.setReadTimeout(20000);
            InputStreamReader inputStreamReader = new InputStreamReader(urlConn
                    .getInputStream());

            buffer = new BufferedReader(inputStreamReader);

            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            // Message message = new Message();

            buffer.close();

        } catch (SocketTimeoutException e) {

            return "false";
        } catch (Exception e) {
            return "false";

        }

        String string = "";
        try {
            string = new String(sb.toString().getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return string;
    }

    public void downFile(final String urlStr, final String path,
                         final String fileName, FileUtils.OnDownLoadListener onDownLoadListener) {

        File resultFile = null;
        final FileUtils fileUtils = new FileUtils();
        InputStream inputStream = null;
        DownFileOperatorInfo downFileOperatorInfo = new DownFileOperatorInfo();
        try {
            inputStream = getInputStreamFromUrl(urlStr, downFileOperatorInfo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileUtils.isFileExist(path + fileName)) {
            downFileOperatorInfo.setResult(true);
        } else if (inputStream != null) {
            resultFile = fileUtils.write2SDFromInput(path, fileName,
                    inputStream, onDownLoadListener, downFileOperatorInfo);
        }
        if (resultFile == null) {
            downFileOperatorInfo.setResult(false);
        } else {
            downFileOperatorInfo.setResult(true);
        }

    }

    public void write2SD_from_string(final String res, final String path,
                                     final String fileName) {

        final FileUtils fileUtils = new FileUtils();
        fileUtils.write2SDFromString(path, fileName, res);

    }

    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public InputStream getInputStreamFromUrl(String urlStr, DownFileOperatorInfo downFileOperatorInfo)
            throws MalformedURLException, IOException {

        try {
            url = new URL(urlStr);
        } catch (Exception e) {
            return null;
        }

        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setInstanceFollowRedirects(true);
        urlConn.setDoInput(true);
        urlConn.setConnectTimeout(15 * 1000);
        urlConn
                .addRequestProperty(
                        "Accept",
                        "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/x-shockwave-flash, */*");
        urlConn.addRequestProperty("Accept-Language", "zh-cn;q=0.5");
        urlConn.setRequestProperty("Accept-Charset", "utf-8");
        urlConn.setRequestProperty("content-type",
                "application/x-www-form-urlencoded;charset=UTF-8");
        urlConn.setRequestProperty("Charset", "utf-8");
        urlConn
                .addRequestProperty(
                        "User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)");

        fileMaxLength = urlConn.getContentLength();
        System.out.println("photo_max: " + fileMaxLength);
        downFileOperatorInfo.setFileLength(fileMaxLength);
        InputStream inputStream = urlConn.getInputStream();
        return inputStream;
    }

    /**
     * 获取重定向地址
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(50000);
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("content-type",
                "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("Charset", "utf-8");
        return conn.getHeaderField("Location");
    }

}

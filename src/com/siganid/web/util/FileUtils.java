/*本类是文件操作类主要是生成查询文件,在服务器上下载文件时候用到*/

package com.siganid.web.util;

/**
 * 文件操作工具
 */

import java.io.*;


public class FileUtils {
    private String SDPATH;

    public String getSDPATH() {
        return SDPATH;
    }

    long len = 0;

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.length() == HttpDownloader.fileMaxLength;
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */

    public interface OnDownLoadListener {
        void onProcess(long speed, long cur, long totle);
    }

    public synchronized File write2SDFromInput(String path, String fileName,
                                               InputStream input, OnDownLoadListener onDownLoadListener, DownFileOperatorInfo downFileOperatorInfo) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            System.out.println("file:" + file.getAbsolutePath());
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024 * 1024];
            int temp = 0;
            while ((temp = input.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
                len = len + temp;
                if (onDownLoadListener != null) {
                    onDownLoadListener.onProcess(len, temp / 1024, downFileOperatorInfo.getFileLength());
                }
            }
            output.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public File write2SDFromString(String path, String fileName, String string) {
        File file = null;

        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);

            FileWriter fw = new FileWriter(file);
            BufferedWriter buffw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(buffw);

            pw.println(string);

            pw.close();
            buffw.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return file;
    }
}
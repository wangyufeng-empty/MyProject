package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

/**
 * @Package: servlets
 * @Author: sxf
 * @Date: 2020-3-31
 * @Description: 处理文件下载
 */
public class DownLoadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String downLoadPath = request.getParameter("downLoadPath").toString();
            String fileName = request.getParameter("fileName").toString();
            downLoadPath = URLDecoder.decode(downLoadPath,"UTF-8");
            fileName = URLDecoder.decode(fileName,"UTF-8");
            response.setCharacterEncoding("UTF-8");
            long fileLength = new File(downLoadPath).length();
            response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("gbk"),"iso-8859-1"));
            response.setContentType("application/x-download;");
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch (Exception e) {
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

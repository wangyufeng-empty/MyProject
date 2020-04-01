package servlets;

import beans.UFile;
import beans.UploadFile;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Package: servlets
 * @Author: sxf
 * @Date: 2020-3-31
 * @Description: 处理文件上传
 */
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        /*
         * 创建工厂，同时指定缓存大小和临时目录
         */

        DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 20, new File("E:/image/"));
        /*
         * 创建解析器
         *   同时指定单个文件的大小限制
         */
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //限制单个文件大小为1M
        sfu.setFileSizeMax(1024 * 1024);
        //设置总上传文件大小(有时候一次性上传多个文件，需要有一个上限,此处为10M)
        sfu.setSizeMax(10*1024*1024);
        /*
         * 解析，得到List<FileItem>
         */
        UploadFile uploadFile = new UploadFile();

        String baseurl="http://" + request.getServerName() //服务器地址
                + ":"
                + request.getServerPort();
        String contextPath =  request.getContextPath();
        if(contextPath != null && !contextPath.equals("")){
            baseurl = baseurl+"/"+contextPath;
        }
        try {
            writer = response.getWriter();
            // 这个方法可能会抛出异常！它会检查单个文件的大小，如果超出了1m，那么这个方法抛出异常。
            List<FileItem> fileItemList = sfu.parseRequest(request);
            if(fileItemList != null && fileItemList.size() > 0){
                /*
                 * 保存上传的文件
                 */
                // 得到文件表单项对象　
                FileItem item = fileItemList.get(0);//这里只保存第一个文件，即不支持多文件上传
                // 得到保存文件的根目录，获取的是真实路径！
                String realpath = this.getServletContext().getRealPath("/uploadFile");
                // 目录打散
                // * 得到文件名称，通过文件名称得到hashcode
                //   > 得到文件名称
                String filename = StringFilter(item.getName());
                //   > 处理绝对路径问题
                int index = filename.lastIndexOf("\\");
                if(index != -1) {
                    filename = filename.substring(index+1);
                }
                //通过文件名称得到hashCode, 转发成16进制
                String hCode = Integer.toHexString(filename.hashCode());
                //从hCode中提取前两个字母，用来作为目录名称
                String dir1 = hCode.charAt(0) + "";
                String dir2 = hCode.charAt(1) + "";
                // 连接到realpath后，得到文件的保存路径！
                File savedir = new File(realpath, dir1 + "/" + dir2);
                // 创建目录链
                savedir.mkdirs();

                // 处理文件同名问题
                filename = UUID.randomUUID().toString().replaceAll("-","") + "_" + filename;
                //使用路径和文件名创建目录文件
                File destFile = new File(savedir, filename);
                //保存文件
                item.write(destFile);
                UFile uf=new UFile();
                uf.setName(filename);
                String downLoadPath = URLEncoder.encode(savedir+"/"+filename, "UTF-8");
                String downLoadfileName = URLEncoder.encode(filename, "UTF-8");
                uf.setSrc(baseurl+"/downLoadFile?downLoadPath="+downLoadPath+"&fileName="+downLoadfileName);
                uploadFile.setData(uf);
            }
            uploadFile.setCode(0);
            uploadFile.setMsg("成功");
            writer.write(JSONObject.fromObject(uploadFile).toString());
        } catch (FileUploadException e) {
            uploadFile.setCode(-1);
            uploadFile.setMsg(e.getMessage());
            /*
             * 如果抛出了异常，需要处理
             */
            if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
                uploadFile.setMsg("成功");
                uploadFile.setMsg("您上传的单个文件超出了1M大小！");
                writer.write(JSONObject.fromObject(uploadFile).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                writer.flush();
                writer.close();
                writer = null;
            }
        }
    }

    // 过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符和空格
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}

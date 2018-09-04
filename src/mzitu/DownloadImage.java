package mzitu;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.URL;  
import java.net.URLConnection;  
  
  
public class DownloadImage {  
  

    public static void download(String urlString, String filename,String savePath,long restTime) throws Exception {  
    	
    	Thread.sleep(restTime);
    	
        // 构造URL  
        URL url = new URL(urlString);  
        // 打开连接  
        URLConnection con = url.openConnection();  
        
        //设置请求超时为5s  
        con.setConnectTimeout(5*1000);  
        String referer = url.getProtocol()+"://"+url.getHost();
        con.setRequestProperty("Referer", referer);
        // 输入流  
        InputStream is = con.getInputStream();  
      
        // 1K的数据缓冲  
        byte[] bs = new byte[1024];  
        // 读取到的数据长度  
        int len;  
        // 输出的文件流  
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }  
       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
        // 开始读取  
        while ((len = is.read(bs)) != -1) {  
          os.write(bs, 0, len);  
        }  
        
        System.out.println(sf.getPath()+"\\"+filename+"下载完成");
        // 完毕，关闭所有链接  
        os.close();  
        is.close();  
    }   
  
}  

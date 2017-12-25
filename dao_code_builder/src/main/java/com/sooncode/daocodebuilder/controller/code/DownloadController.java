package com.sooncode.daocodebuilder.controller.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sooncode.daocodebuilder.service.build.Project;

/**
 * 文件下载
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/downloadController")
public class DownloadController {


	@RequestMapping("/download")
	public String download( HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"";
		File folder = new File(path+"zip"); // 创建文件夹
		folder.mkdirs(); // 创建文件夹
		 
		String zipFileName =path+"zip/"+Project.projectName+".zip";
		String inputFileName = path+"code";
		//System.out.println("Download*************"+inputFileName+"**************");
		//压缩文件夹 
		Compress compress = new Compress();
		try{
			compress.zip(zipFileName,inputFileName );
			
		}catch(Exception e){
			return null;
		}
		
		String fileName=Project.projectName+".zip";
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			 // Thread.currentThread().getContextClassLoader().getResource("").getPath() + "download";// 这个download目录为啥建立在classes下的
			InputStream inputStream = new FileInputStream(new File(zipFileName));

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		
		
		//删除文件
		 deleteDirectory(path+"zip");
		 deleteDirectory(path+"code");
		 
		
		return null;
	}
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	   boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	} 
	
	public boolean deleteFile(String sPath) {  
	   boolean flag = false;  
	    File  file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
}
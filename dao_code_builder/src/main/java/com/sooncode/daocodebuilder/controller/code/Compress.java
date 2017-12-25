package com.sooncode.daocodebuilder.controller.code;
import java.io.File;
import org.apache.tools.zip.ZipOutputStream; //这个包在ant.jar里，要到官方网下载
import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
 * 文件夹压缩为 .zip
 * @author pc
 *
 */
public class Compress {
    public Compress() {}

    
    public void zip( String zipFileName  ,String inputFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
    }

    private void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        System.out.println("zip done");
        out.close();
    }

    private void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }

    public static void main(String [] temp){
        Compress book = new Compress();
        try {
           book.zip("d:\\ZIP.zip","d:\\zip");//你要压缩的文件夹
        }catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}
package cn.www.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;  


/**
 * 
 * @author li
 *
 */
public class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	public static final String separator1 = File.separator; //“\”
	
	public static final String separator2 = "/"; //“/”
	
	/**
     * @param originalName 原图文件名称
     * @return String
     */
    public static String genFileName(String originalName) {
    	//logger.info("生成新文件名，原文件名为：" + originalName);
    	RandomUtil random = new RandomUtil();
        try {
            return random.getRandomFilename(originalName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    
    /**
     * 获得文件夹路径
     * @return /2012/02/27/
     */
    public static String getFolderDir() {
    	Date date = new Date();
    	
    	String path = separator2+ImageUtil.getFormatDate(date, "yyyy");
    		   path += separator2+ImageUtil.getFormatDate(date, "MM");
    		   path += separator2+ImageUtil.getFormatDate(date, "dd") + separator2;
    		   
    	return path;
    }
    
    /**
     * 获得文件夹路径
     * @return \2012\02\27\
     */
    public static String getFolderDir2() {
    	Date date = new Date();
    	
    	String path = separator1+ImageUtil.getFormatDate(date, "yyyy");
    		   path += separator1+ImageUtil.getFormatDate(date, "MM");
    		   path += separator1+ImageUtil.getFormatDate(date, "dd") + separator1;
    		   
    	return path;
    }
    
    /**
     * 将要保存到哪个目录下
     * @return images/ 
     */
    public static String getPrefixFolder() {
    	return "images"+separator2;
    }
    
    /**
     * 创建文件夹
     * @param fileTargetDir
     * @return false :文件夹已存在 , true:创建文件夹成功
     */
    public static boolean createFolder(String fileTargetDir) {
        File targetFolder = new File(fileTargetDir);
        if (!targetFolder.exists()) {
            logger.info("要保存到的目录不存在，现在创建： " + targetFolder);
            return targetFolder.mkdirs();
        }
        return false;
    }
    
    private static String getFormatDate(Date date,String format) {
    	DateFormat sdf = new SimpleDateFormat(format);
    	return sdf.format(date);
    }
    
    public static void main(String[] args) {
		String str ="我的文件.jpg";
		System.out.println(ImageUtil.getFolderDir()+ ImageUtil.genFileName(str));
		
		String path ="D:/temp"+ImageUtil.getFolderDir();
		System.out.println(path);
	}
    
    
    /******************图片压缩***************************/
    /***
     * outputDir 完整的路径
     * inputFileName 源文件名
     * outputFileName 文件名
     * outputWidth 新图片的宽
     * outputHeight 新图片的高
     * proportion 是否是等比压缩
     */
    public static void compressPic( String outputDir,String inputFileName,String outputFileName,int outputWidth,int outputHeight,boolean proportion ){
    	 File file = new File( outputDir + inputFileName );
    	 if (!file.exists()) {   
    		 return;
         } 
    	 try {
			Image img = ImageIO.read(file);
			if (img.getWidth(null) == -1) {  
				return;
			}
			int newWidth; 
			int newHeight; 
			//判断是否是等比缩放   
			if (proportion == true) {   
				// 为等比缩放计算输出的图片宽度及高度   
		        double rate1 = ((double) img.getWidth(null))/ (double) outputWidth + 0.1;
				double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
				// 根据缩放比率大的进行缩放控制   
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
			} else {
				newWidth = outputWidth; // 输出的图片宽度   
				newHeight = outputHeight; // 输出的图片高度   
			}
			BufferedImage tag = new BufferedImage((int) newWidth,(int) newHeight, BufferedImage.TYPE_INT_RGB);   
			/* * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
			 * 优先级比速度高 生成的图片质量比较好 但速度慢 
			 */   
			tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
			FileOutputStream out = new FileOutputStream(outputDir + outputFileName);  
			// JPEGImageEncoder可适用于其他图片类型的转换   
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
			encoder.encode(tag);   
			out.close();   
		} catch (IOException e) {
			e.printStackTrace();
		}   

    }
    
    
    public static void cutImage(String srcPath,String newFileName, int width, int height) throws IOException {  
	   File srcFile = new File(srcPath);   
	   BufferedImage image = ImageIO.read(srcFile);   
	   int srcWidth = image.getWidth(null);   
	   int srcHeight = image.getHeight(null);   
	   int newWidth = 0, newHeight = 0;   
	   int x = 0, y = 0;   
	   double scale_w = (double)width/srcWidth;   
	   double scale_h = (double)height/srcHeight;   
	    System.out.println("scale_w="+scale_w+",scale_h="+scale_h);   
	    //按原比例缩放图片   
	    if(scale_w < scale_h) {   
	        newHeight = height;   
	        newWidth = (int)(srcWidth * scale_h);   
	        x = (newWidth - width)/2;   
	    } else {   
	        newHeight = (int)(srcHeight * scale_w);   
	        newWidth = width;   
	        y = (newHeight - height)/2;   
	    }   
	    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);   
	    newImage.getGraphics().drawImage(   
	    image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);   
	    // 保存缩放后的图片  
	    String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);   
	    File destFile = new File(srcFile.getParent(), newFileName+ "." + fileSufix);   
	    // ImageIO.write(newImage, fileSufix, destFile);   
	    // 保存裁剪后的图片   
	    ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix, destFile);   
    }  
    
}

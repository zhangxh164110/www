package test.freemarker.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 模板工具类
 * @author hailang
 * @date 2009-7-9 上午10:21:25
 * @version 1.0
 */
public class FreeMarkerUtil {
	/**
	 * @date 2009-7-9 上午09:55:43
	 * @param templateName 模板文件名称 
	 * @param root 数据模型根对象
	 * @param templateEncoding 模板文件的编码方式
	 * @param outPath 生成的html文件路径（）
	 */
	public static void analysisTemplate(String templateName,String templateEncoding,Map<?,?> root,String outPath){
		try {
			Configuration config=new Configuration();
			File file=new File("src/templates");
			//设置要解析的模板所在的目录，并加载模板文件
			config.setDirectoryForTemplateLoading(file);
			//设置包装器，并将对象包装为数据模型
			config.setObjectWrapper(new DefaultObjectWrapper());
			
			//获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			Template template=config.getTemplate(templateName,templateEncoding);
			//合并数据模型与模板
			//Writer out = new OutputStreamWriter(System.out);
			//File afile = new File("F:/FreeMarkerDemo/shtmls/abcd.htm");
			File afile = new File(outPath);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile)));
		    template.process(root, out);
		    out.flush();
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (TemplateException e) {
			e.printStackTrace();
		}
		
	} 
}

package cn.www.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

public class ZoomImage {

	public void zoomTheImage(String fileUrl, String newUrl, int width, int height) {
		java.io.File file = new java.io.File(fileUrl); // 读入刚才上传的文件
		// Image src = null;
		try {
			BufferedImage source = javax.imageio.ImageIO.read(file);
			int type = source.getType();
			BufferedImage target = null;
			String imgType = "JPG";
			if (fileUrl.toLowerCase().indexOf(".gif") != -1) {
				imgType = "GIF";
			}
			double sx = (double) width / source.getWidth();
			double sy = (double) height / source.getHeight();
			// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
			// 则将下面的if else语句注释即可
			if (sx > sy) {
				sx = sy;
				width = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				height = (int) (sy * source.getHeight());
			}

			if (type == BufferedImage.TYPE_CUSTOM) { // handmade
				ColorModel cm = source.getColorModel();
				WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
				boolean alphaPremultiplied = cm.isAlphaPremultiplied();
				target = new BufferedImage(cm, raster, alphaPremultiplied, null);
			} else
				target = new BufferedImage(width, height, type);
			Graphics2D g = target.createGraphics();
			// smoother than exlax:
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
			g.dispose();
			FileOutputStream newimage = new FileOutputStream(newUrl);
			ImageIO.write(target, imgType, newimage);
			newimage.close();
			// src = javax.imageio.ImageIO.read(file);
			// // 构造Image对象
			// BufferedImage tag = new BufferedImage(width, height,
			// BufferedImage.TYPE_INT_RGB);
			// tag.getGraphics().drawImage(src, 0, 0, width, height, null); //
			// 绘制缩小后的图
			// FileOutputStream newimage = new FileOutputStream(newUrl); //
			// 输出到文件流
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			// encoder.encode(tag); // 近JPEG编码
			// newimage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

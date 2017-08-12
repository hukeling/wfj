package util.other;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import util.upload.FileTypeUtil;

import com.swetake.util.Qrcode;
/**
 * java生成二维码带logo工具类
 * @author ss
 *
 */
public class QRCode {
	/** 
     * 生成二维码(QRCode)图片 
     * @param content 二维码图片的内容
     * @param imgPath 生成二维码图片完整的路径
     * @param ccbpath  二维码图片中间的logo路径
     */  
    public static int createQRCode(String content, String imgPath,String ccbPath) {  
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            qrcodeHandler.setQrcodeVersion(7);  
            byte[] contentBytes = content.getBytes("gb2312");  
            BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, 140, 140);  
            // 设定图像颜色 > BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量 不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容 > 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 120) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {  
                System.err.println("QRCode content bytes length = "  
                        + contentBytes.length + " not in [ 0,120 ]. ");  
                return -1;
            }  
            Image img = ImageIO.read(new File(ccbPath));//实例化一个Image对象。
            gs.drawImage(img, 55, 55,30,30, null);//图片logo的大小设置
            gs.dispose();  
            bufImg.flush();  
            // 生成二维码QRCode图片  
            File imgFile = new File(imgPath);  
            ImageIO.write(bufImg, "png", imgFile);  
        } catch (Exception e) 
        {  
            e.printStackTrace();  
            return -100;
        }  
        return 0;
    }
    /**
     * 生成二维码
     * @param content        二维码内容
     * @param ccbPath        二维码中间小图片路径	
     * @param fileUrlConfig  全局上传文件总根
     * @param dirName        路径/根目录
     * @param imageInfoPath  路径/子目录
     * @return
     */
    public static String createQRPng(String content,String ccbPath,Map<Object,Object> fileUrlConfig,String dirName,String imageInfoPath){
    	String imagePathFileName = null;
    	try {  
    		String newName = ".png";
    		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
			String randomCode=Utils.getRandomCode();
			//原始图片文件编译后的新名称
			newName = randomCode + newName;
			//定义新文件保存实际路径+配置文件systemConfig.properties中定义的文件生成目录规则：年月日
			String newImagePath = fileUrlConfig.get(dirName)+ "/"+ fileUrlConfig.get(imageInfoPath) + "/"+ FileTypeUtil.getSerial(new Date(), fileUrlConfig.get("fileRule")) + "/" ;
			//定义文件对象，需要加上盘符
			File savefile = new File((fileUrlConfig.get("fileUploadRoot") + "/"+ newImagePath), newName);
			//判断文件是否存在，不存在则新创建
			if (!savefile.getParentFile().exists()) {
				savefile.getParentFile().mkdirs();
			}
			imagePathFileName = newImagePath + newName;
            Qrcode qrcodeHandler = new Qrcode();  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            qrcodeHandler.setQrcodeVersion(7);  
            byte[] contentBytes = content.getBytes("gb2312");  
            BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, 140, 140);  
            // 设定图像颜色 > BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量 不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容 > 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 120) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {  
                System.err.println("QRCode content bytes length = "  
                        + contentBytes.length + " not in [ 0,120 ]. ");  
                return null;
            }  
            if(ccbPath!=null){
            	Image img = ImageIO.read(new File(fileUrlConfig.get("fileUploadRoot")+ "/"+ccbPath));//实例化一个Image对象。
            	gs.drawImage(img, 55, 55,30,30, null);//图片logo的大小设置
            	gs.dispose();  
            }
            bufImg.flush();  
            // 生成二维码QRCode图片   
            ImageIO.write(bufImg, "png", savefile);  
        } catch (Exception e) 
        {  
            e.printStackTrace();  
            return null;
        }  
    	return imagePathFileName;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createQRCode("http://www.baidu.com","D:/server/thshop/QRCode/cs.png","C:/Users/ss/Desktop/logo.png");
	}
}

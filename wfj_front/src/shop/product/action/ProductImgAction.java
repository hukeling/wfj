package shop.product.action;
import java.io.File;
import java.io.PrintWriter;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import shop.product.pojo.ProductImg;
import shop.product.service.IProductImgService;
import util.action.BaseAction;
import util.upload.ImageUtil;
/**
 * ProductImgAction - 商品图片action类
 */
@SuppressWarnings("serial")
public class ProductImgAction extends BaseAction{
	private IProductImgService productImgService;//商品图片service
	private ProductImg productImgObj=new ProductImg();//商品图片对象
	// 上传文件路径
		private File imagePath;
		// 上传文件名称
		private String imagePathFileName;
		// 异步ajax 图片上传
		public void uploadImage() throws Exception {
			JSONObject jo = new JSONObject();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// 1图片上传
			if (imagePath != null) {
				// 1上传文件的类型
				String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf(".") + 1);
				if ("jpg".equals(typeStr) || "JPG".equals(typeStr) || "png".equals(typeStr) || "PNG".equals(typeStr) || "GIF".equals(typeStr) ||"gif".equals(typeStr) || "".equals(typeStr)) {
					// 需要修改图片的存放地址
					String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
					newName = UUID.randomUUID() + newName;
					File savefile = new File(new File((String) fileUrlConfig.get("fileUploadRoot") + "/"+ (String) fileUrlConfig.get("shop") + "/image/product"), newName);
					if (!savefile.getParentFile().exists()) {
						savefile.getParentFile().mkdirs();
					}
					FileUtils.copyFile(imagePath, savefile);
					imagePathFileName = (String) fileUrlConfig.get("shop") + "/image/product/" + newName;
					//设置图片处理的参数 
					String imagePath="D:/server/tomcat/hyytweb/shop/image/product/";//存储路径
					String srcImageFile=imagePath+newName;//源图片
					String large=imagePath+"large_"+newName;//水印大图
					String scaleImageFile=imagePath+"scaleImageFile_"+newName;//中图临时文件
					String medium=imagePath+"medium_"+newName;//水印中图
					String thumbnail=imagePath+"thumbnail_"+newName;//滚动小图
					//配置文件中上传文件路径的长度
					//Z:/thshop/
					String lstr=fileUrlConfig.get("fileUploadRoot")+ "/";
					int l=lstr.length();
					ImageUtil.imageProcessing("d:/logo.gif", srcImageFile, large, scaleImageFile, medium, thumbnail, 500, 500, 300, 300, 1, 1, 0.5f, true,l);
					//URL入库
					String roadURL=(String) fileUrlConfig.get("shop") + "/image/product/";
					//源图URL
					productImgObj.setSource(roadURL+newName);
					//处理后大的URL
					productImgObj.setLarge(roadURL+"large_"+newName);
					//处理后中等的URL
					productImgObj.setMedium(roadURL+"medium_"+newName);
					//处理后缩略图URL
					productImgObj.setThumbnail(roadURL+"thumbnail_"+newName);
					productImgService.saveOrUpdateObject(productImgObj);
					jo.accumulate("photoUrl", imagePathFileName);
					jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
				} else {
					jo.accumulate("photoUrl", "false2");
				}
			} else {
				jo.accumulate("photoUrl", "false1");
			}
			out.println(jo.toString());
			out.flush();
			out.close();
		}
		/**
		 * setter getter
		 * @return
		 */
		public File getImagePath() {
			return imagePath;
		}
		public void setImagePath(File imagePath) {
			this.imagePath = imagePath;
		}
		public String getImagePathFileName() {
			return imagePathFileName;
		}
		public void setImagePathFileName(String imagePathFileName) {
			this.imagePathFileName = imagePathFileName;
		}
		public void setProductImgService(IProductImgService productImgService) {
			this.productImgService = productImgService;
		}
		public ProductImg getProductImgObj() {
			return productImgObj;
		}
		public void setProductImgObj(ProductImg productImgObj) {
			this.productImgObj = productImgObj;
		}
}

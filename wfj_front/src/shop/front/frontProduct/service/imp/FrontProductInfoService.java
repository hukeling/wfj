package shop.front.frontProduct.service.imp;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.front.frontProduct.dao.IFrontProductInfoDao;
import shop.front.frontProduct.service.IFrontProductInfoService;
import shop.front.store.dao.IShopProCateClassDao;
import shop.front.store.pojo.ShopProCateClass;
import shop.product.dao.IBrandDao;
import shop.product.dao.IProductAttrIndexDao;
import shop.product.dao.IProductImgDao;
import shop.product.dao.IProductSpecificationDao;
import shop.product.dao.IProductSpecificationValueDao;
import shop.product.dao.ISpecificationValueDao;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductAttrIndex;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductSpecification;
import shop.product.pojo.ProductSpecificationValue;
import shop.product.pojo.SpecificationValue;
import shop.tags.dao.IShopProductTradeSituationTagDao;
import shop.tags.pojo.ShopProductTradeSituationTag;
import util.other.CopyObject;
import util.service.BaseService;
import util.upload.ImageFileUploadUtil;
import util.upload.ImageUtil;
/**
 * 商品Service接口实现类
 * @author 
 *
 */
public class FrontProductInfoService extends BaseService  <ProductInfo> implements IFrontProductInfoService{
	private IFrontProductInfoDao frontProductInfoDao;
	private IProductSpecificationDao productSpecificationDao;//商品和规格中间表Service
	private IProductSpecificationValueDao productSpecificationValueDao;//商品和规格值中间表Service 
	private IProductImgDao productImgDao;//商品图片
	private IShopProCateClassDao shopProCateClassDao;//商品和店铺内部商品分类关系
	private ISpecificationValueDao specificationValueDao;//规格值Dao
	private IShopProductTradeSituationTagDao shopProductTradeSituationTagDao;//商品标签关联表Dao
	private IProductAttrIndexDao productAttrIndexDao;
	private IBrandDao brandDao;
	/**
	 * 添加商品没有规格
	 * @param shopProCategoryId店铺内部分类
	 * @param productInfo商品信息
	 * @param listProductImage商品图片
	 * @param fileUrlConfig上传图片的配置文件
	 * @param listProductUploadImgs上传图片路径
	 * @param listProductUploadImgsFileName上传图片名称
	 */
	@SuppressWarnings("rawtypes")
	public void saveOrUpdateProductInfo(String shopProCategoryId,ProductInfo productInfo,List<ProductImg> listProductImage,Map fileUrlConfig,List<File> listProductUploadImgs,List<String>  listProductUploadImgsFileName){
		try {
			productInfo.setCreateDate(new Date());
			productInfo.setUpdateDate(new Date());
			productInfo.setPutSaleDate(new Date());
			productInfo.setProductFullName(productInfo.getProductName());
			productInfo = frontProductInfoDao.saveOrUpdateObject(productInfo);
			for(int i=0;i<listProductImage.size();i++){
					ProductImg productImg = new ProductImg();
					productImg=listProductImage.get(i);
					productImg.setProductId(productInfo.getProductId());
					productImgDao.saveOrUpdateObject(productImg);
			}
			//保存商品和店铺内部商品分类的关系
			ShopProCateClass spcc = new ShopProCateClass();
			spcc.setShopProCategoryId(Integer.parseInt(shopProCategoryId));
			spcc.setProductId(productInfo.getProductId());
			shopProCateClassDao.saveOrUpdateObject(spcc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据多条的规格和规格值组，生成多个商品
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public void saveOrUpdateListProductInfo(String shopProCategoryId,ProductInfo productInfo,String parameters,List<ProductImg> listProductImage, Map fileUrlConfig,List<File> listProductUploadImgs,List<String>  listProductUploadImgsFileName,JSONArray jsonArray,List<ProductAttrIndex> paiList){
		//重新构建商品名称（带品牌的）
		if(productInfo.getBrandId()!=1){
			Brand brand = brandDao.get(" where o.brandId="+productInfo.getBrandId());
			productInfo.setProductName(brand.getBrandName()+" "+productInfo.getProductName());
		}
		//SKU
		String sku = null;
		String[] selectColumns = {"sku","productId"};
		List<ProductInfo> list = frontProductInfoDao.findSome(selectColumns, 0, 1, " order by o.productId desc");
		if(list!=null&&list.size()>0){
			sku =util.other.SKUUtilityClass.getGeneratedSKU(list.get(0).getSku());
		}else{
			sku =util.other.SKUUtilityClass.getGeneratedSKU(null);
		}
		try {
			productInfo.setIsShow(1);//0：不显示， 1：显示；
			//添加商品的规格和规格值
			if(parameters!=null && !"".equals(parameters)){
				String[] oneSplit = parameters.split(",");//根据规格组拼生成一个新商品的规格组
				//更改商品在列表显示的状态
				Integer k = 0;
				for(int i =0;i<oneSplit.length;i++){
					if(i!=0){
						sku =util.other.SKUUtilityClass.getGeneratedSKU(sku);
					}
					productInfo.setSku(sku);
					if(k>0){
						productInfo.setIsShow(0);
					}
					k++;
					String[] secSplit = oneSplit[i].split("&");//把一个新的商品规格组解析成规格对应规格值
					//复制商品基本信息
					ProductInfo productInfoCopy = null;
					//复制商品信息
					productInfoCopy = (ProductInfo) new CopyObject().copy(productInfo);
					productInfoCopy = (ProductInfo) frontProductInfoDao.saveOrUpdateObject(productInfoCopy);//复制保存商品
					ShopProCateClass spcc = new ShopProCateClass();
					spcc.setProductId(productInfoCopy.getProductId());
					spcc.setShopProCategoryId(Integer.parseInt(shopProCategoryId));
					shopProCateClassDao.saveOrUpdateObject(spcc);
					for(ProductImg pi:listProductImage){
						ProductImg productImg = new ProductImg();
						productImg.setLarge(pi.getLarge());
						productImg.setMedium(pi.getMedium());
						productImg.setOrders(pi.getOrders());
						productImg.setSource(pi.getSource());
						productImg.setThumbnail(pi.getThumbnail());
						productImg.setTitle(pi.getTitle());
						productImg.setProductId(productInfoCopy.getProductId());
						productImgDao.saveOrUpdateObject(productImg);
					}
					StringBuffer sb = new StringBuffer();
					sb.append(productInfoCopy.getProductName()+" [");
					for(String ss : secSplit){
						String[] thirSplit = ss.split("=");//把规格和规格值分开
						String[] fourSplit = thirSplit[0].split("_");//解析规格前缀，如：specification_1
						if("kucun".equals(thirSplit[0])){//库存标识
							productInfoCopy.setStoreNumber(util.other.Utils.parseInt(thirSplit[1], 0));
							continue;
						}else if("bianhao".equals(thirSplit[0])){//编号标识
							productInfoCopy.setProductCode(thirSplit[1]);
							continue;
						}
						//规格值
						ProductSpecificationValue productSpecificationValue = new ProductSpecificationValue();
						productSpecificationValue.setProductId(productInfoCopy.getProductId());
						//规格值id
						productSpecificationValue.setSpecificationValueId(Integer.parseInt(thirSplit[1]));
						//当前分组号
						productSpecificationValue.setGoodId(productInfoCopy.getGoods());
						//规格id
						productSpecificationValue.setSpecificationId(Integer.parseInt(fourSplit[1]));
						productSpecificationValueDao.saveOrUpdateObject(productSpecificationValue);
						SpecificationValue sv = (SpecificationValue) specificationValueDao.get(" where o.specificationValueId='"+thirSplit[1]+"'");
						sb.append(sv.getName()+",");
					}
					sb.deleteCharAt(sb.lastIndexOf(","));
					sb.append("]");
					productInfoCopy.setProductFullName(sb.toString());
					frontProductInfoDao.saveOrUpdateObject(productInfoCopy); //更新商品信息;
					//保存商品与标签表关系.
					//1.删除相关数据
					shopProductTradeSituationTagDao.deleteByParams(" where o.productId="+productInfoCopy.getProductId());
					if(jsonArray!=null&&jsonArray.size()>0){
						//2.将新数据存入数据库中
						int jsonLength = jsonArray.size();
						//对json数组进行循环
						for (int p = 0; p < jsonLength;p++) {
							JSONObject jo=JSONObject.fromObject(jsonArray.get(p));
							String ttId=jo.getString("ttId");
							String ids=jo.getString("ids");
							if(StringUtils.isNotEmpty(ids)){
								String[] split = ids.split(",");
								for(String stId:split){
									ShopProductTradeSituationTag sptst=new ShopProductTradeSituationTag();
									sptst.setProductId(productInfoCopy.getProductId());
									sptst.setStId(Integer.parseInt(stId));
									sptst.setTtId(Integer.parseInt(ttId));
									shopProductTradeSituationTagDao.saveOrUpdateObject(sptst);
								}
							}
						}
					}
					//商品属性关系表操作
					if(paiList!=null && paiList.size()>0){
						for(ProductAttrIndex pai:paiList){
							ProductAttrIndex p = new ProductAttrIndex();
							p.setAttrValueId(pai.getAttrValueId());
							p.setProductAttrId(pai.getProductAttrId());
							p.setProductId(productInfoCopy.getProductId());
							p.setProductTypeId(productInfoCopy.getProductTypeId());
							productAttrIndexDao.saveOrUpdateObject(p);
						}
					}
				}
			}else{
				//复制商品基本信息
				productInfo.setSku(sku);
				ProductInfo productInfoCopy = null;
				productInfoCopy = (ProductInfo) new CopyObject().copy(productInfo);
				productInfoCopy.setProductFullName(productInfoCopy.getProductName());
				productInfoCopy.setProductId(null);
				productInfoCopy = (ProductInfo) frontProductInfoDao.saveOrUpdateObject(productInfoCopy);//复制保存商品
				ShopProCateClass spcc = new ShopProCateClass();
				spcc.setProductId(productInfoCopy.getProductId());
				spcc.setShopProCategoryId(Integer.parseInt(shopProCategoryId));
				shopProCateClassDao.saveOrUpdateObject(spcc);
				//保存商品与标签表关系.
				//1.删除相关数据
				shopProductTradeSituationTagDao.deleteByParams(" where o.productId="+productInfoCopy.getProductId());
				if(jsonArray!=null&&jsonArray.size()>0){
					
					//2.将新数据存入数据库中
					int jsonLength = jsonArray.size();
					//对json数组进行循环
					for (int p = 0; p < jsonLength;p++) {
						JSONObject jo=JSONObject.fromObject(jsonArray.get(p));
						String ttId=jo.getString("ttId");
						String ids=jo.getString("ids");
						if(StringUtils.isNotEmpty(ids)){
							String[] split = ids.split(",");
							for(String stId:split){
								ShopProductTradeSituationTag sptst=new ShopProductTradeSituationTag();
								sptst.setProductId(productInfoCopy.getProductId());
								sptst.setStId(Integer.parseInt(stId));
								sptst.setTtId(Integer.parseInt(ttId));
								shopProductTradeSituationTagDao.saveOrUpdateObject(sptst);
							}
						}
					}
				}
				if(listProductImage!=null && listProductImage.size()>0){
					//商品详情图片
					for(ProductImg pi:listProductImage){
						ProductImg productImg = new ProductImg();
						productImg.setLarge(pi.getLarge());
						productImg.setMedium(pi.getMedium());
						productImg.setOrders(pi.getOrders());
						productImg.setSource(pi.getSource());
						productImg.setThumbnail(pi.getThumbnail());
						productImg.setTitle(pi.getTitle());
						productImg.setProductId(productInfoCopy.getProductId());
						productImgDao.saveOrUpdateObject(productImg);
					}
				}
				//商品属性关系表操作
				if(paiList!=null && paiList.size()>0){
					for(ProductAttrIndex pai:paiList){
						ProductAttrIndex p = new ProductAttrIndex();
						p.setAttrValueId(pai.getAttrValueId());
						p.setProductAttrId(pai.getProductAttrId());
						p.setProductId(productInfoCopy.getProductId());
						p.setProductTypeId(productInfoCopy.getProductTypeId());
						productAttrIndexDao.saveOrUpdateObject(p);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//直接修改规格调用的方法
	public void updateFrontProductSpecification(Integer productId,String goods,String parameters){
		List<String> groupProductIdList = new ArrayList<String>();//同组商品ID
		List<String> retainProductIdList = new ArrayList<String>();//保留商品ID
		if(productId!=null){
			ProductInfo product = (ProductInfo) frontProductInfoDao.get(" where o.productId="+productId);
			List<ProductInfo> productList = frontProductInfoDao.findObjects(" where o.goods='"+goods+"'");
			for(ProductInfo p : productList){
				groupProductIdList.add(String.valueOf(p.getProductId()));
			}
			//判断是否有删除的现有规格
			if(!StringUtils.isEmpty(parameters)){
				String[] commaSplits = parameters.split(",");//按逗号分割
				for(String commaSplit : commaSplits){
					if(commaSplit.contains("productId")){
						String[] alsoSplits = commaSplit.split("&");
						String[] gtSplits = alsoSplits[0].split("=");//用=符分割
						retainProductIdList.add(gtSplits[1]);
					}
				}
			}
			if(groupProductIdList.size()!=retainProductIdList.size()){
				//比对新传的数据商品ID和原有组里面的商品ID，如果有对应不上的就说明此规格的商品已经删除，那么就把关联的规格和规格值删除
				for(String groupProductId : groupProductIdList){
					if(!retainProductIdList.contains(groupProductId)){
						frontProductInfoDao.deleteByParams(" where o.productId='"+groupProductId+"'");
						productSpecificationDao.deleteByParams(" where o.productId='"+groupProductId+"'");
						productSpecificationValueDao.deleteByParams(" where o.productId='"+groupProductId+"'");
					}
				}
			}
			//循环没有删除的商品和新添加的商品，并更新规格和规格值
			if(!StringUtils.isEmpty(parameters)){
				String[] commaSplits = parameters.split(",");//按逗号分割
				for(String commaSplit : commaSplits){
					//判断是修改原始的商品规格和规格值数据还是添加新的数据
					if(commaSplit.contains("productId")){
						//先删除此商品挂钩的规格和规格值数据
						String[] alsoSplits = commaSplit.split("&");//用&符分割
						String[] gtSplits = alsoSplits[0].split("=");//用=符分割
						ProductInfo p = (ProductInfo) frontProductInfoDao.get(" where o.productId='"+gtSplits[1]+"'");
						for(String alsoSplit : alsoSplits){
							if(alsoSplit.contains("productId")){
								continue;
							}else{
								productSpecificationDao.deleteByParams(" where o.productId='"+p.getProductId()+"'");//删除商品和规格关系
								productSpecificationValueDao.deleteByParams(" where o.productId='"+p.getProductId()+"'");//删除商品和规格值关系
								StringBuffer sb = new StringBuffer();
								sb.append(p.getProductName()+" [");
								String[] thirSplit = alsoSplit.split("=");//把规格和规格值分开
								String[] fourSplit = thirSplit[0].split("_");//解析规格前缀，如：specification_1
								//规格
								ProductSpecification ps= new ProductSpecification();
								ps.setProductId(p.getProductId());
								ps.setSpecificationId(Integer.parseInt(fourSplit[1]));
								productSpecificationDao.saveOrUpdateObject(ps);
								//规格值
								ProductSpecificationValue psv = new ProductSpecificationValue();
								psv.setProductId(p.getProductId());
								psv.setSpecificationValueId(Integer.parseInt(thirSplit[1]));
								productSpecificationValueDao.saveOrUpdateObject(psv);
								SpecificationValue sv = (SpecificationValue) specificationValueDao.get(" where o.specificationValueId='"+thirSplit[1]+"'");
								if(sv!=null){
									sb.append(sv.getName()+",");
								}
								if(sb.length()>0){
									sb.deleteCharAt(sb.lastIndexOf(","));
								}
								sb.append("]");
								p.setProductFullName(sb.toString());
							}
						}
						frontProductInfoDao.saveOrUpdateObject(p); //更新商品信息
					}else{
						try {
							
							//保存新添加的数据
							ProductInfo productInfoCopy = (ProductInfo) new CopyObject().copy(product);
							productInfoCopy.setProductId(null);
							//SKU
							String sku = null;
							String[] selectColumns = {"sku","productId"};
							List<ProductInfo> list = frontProductInfoDao.findSome(selectColumns, 0, 1, " order by o.productId desc");
							if(list!=null&&list.size()>0){
								sku =util.other.SKUUtilityClass.getGeneratedSKU(list.get(0).getSku());
							}else{
								sku =util.other.SKUUtilityClass.getGeneratedSKU(null);
							}
							productInfoCopy.setSku(sku);
							productInfoCopy = (ProductInfo) frontProductInfoDao.saveOrUpdateObject(productInfoCopy);
							//操作商品标签
							List<ShopProductTradeSituationTag> findObjects = shopProductTradeSituationTagDao.findObjects(" where o.productId="+productId);
							if(findObjects!=null&&findObjects.size()>0){
								for(ShopProductTradeSituationTag sptt:findObjects){
									ShopProductTradeSituationTag st=new ShopProductTradeSituationTag();
									st.setProductId(productInfoCopy.getProductId());
									st.setPtstId(sptt.getPtstId());
									st.setStId(sptt.getStId());
									st.setTtId(sptt.getTtId());
									shopProductTradeSituationTagDao.saveOrUpdateObject(st);
								}
							}
							//把商品图片复制给新选择的规格
							List<ProductImg> productImgList = productImgDao.findObjects(" where o.productId='"+product.getProductId()+"'");
							if(productImgList!=null && productImgList.size()>0){
								for(ProductImg pi : productImgList){
									ProductImg productImgCopy = new ProductImg();
									productImgCopy = (ProductImg) new CopyObject().copy(pi);
									productImgCopy.setProductImageId(null);
									productImgCopy.setProductId(productInfoCopy.getProductId());
									productImgDao.saveOrUpdateObject(productImgCopy);
								}
							}
							//新添加的规格
							StringBuffer sb = new StringBuffer();
							sb.append(productInfoCopy.getProductName()+" [");
							String[] alsoSplits = commaSplit.split("&");
							for(String alsoSplit : alsoSplits){
								String[] thirSplit = alsoSplit.split("=");//把规格和规格值分开
								String[] fourSplit = thirSplit[0].split("_");//解析规格前缀，如：specification_1
								//规格
								ProductSpecification productSpecification = new ProductSpecification();
								productSpecification.setProductId(productInfoCopy.getProductId());
								productSpecification.setSpecificationId(Integer.parseInt(fourSplit[1]));
								productSpecificationDao.saveOrUpdateObject(productSpecification);
								//规格值
								ProductSpecificationValue productSpecificationValue = new ProductSpecificationValue();
								productSpecificationValue.setProductId(productInfoCopy.getProductId());
								productSpecificationValue.setSpecificationValueId(Integer.parseInt(thirSplit[1]));
								productSpecificationValueDao.saveOrUpdateObject(productSpecificationValue);
								SpecificationValue sv = (SpecificationValue) specificationValueDao.get(" where o.specificationValueId='"+thirSplit[1]+"'");
								if(sv!=null){
									sb.append(sv.getName()+",");
								}
							}
							if(sb.length()>0){
								sb.deleteCharAt(sb.lastIndexOf(","));
							}
							sb.append("]");
							productInfoCopy.setProductFullName(sb.toString());
							frontProductInfoDao.saveOrUpdateObject(productInfoCopy); //更新商品信息;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
//  详情页滚动图片上传
	public ProductImg uploadProductImage(File imagePath,String imagePathFileName,ProductImg productImg,Map<Object,Object> fileUrlConfig) throws Exception {
		//原图
		imagePathFileName = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_product");
		productImg.setSource(imagePathFileName);
		//原图片上传至服务器后的全路径
		String url=fileUrlConfig.get("fileUploadRoot") +"/"+imagePathFileName;
		//将路径new成文件
		File f=new File(url);
		//生成原文件名称
		String name=f.getName();
		//去文件名称（去除后缀.jpg）
		String[] nameArray = name.split("\\.");
		//原文件的父目录
		//Z:\thshop\shop\image\product\20140220
		String pathParent=f.getParent();
		//配置文件中上传文件路径的长度
		//Z:/thshop/
		String lstr=String.valueOf(fileUrlConfig.get("fileUploadRoot"));
		int l=lstr.length();
		//生成大图
		productImg.setLarge(ImageUtil.scaleByHeightAndWidth(url,pathParent+"/"+nameArray[0]+"_large."+nameArray[1], 1000, 1000, true,l));
		//生成中图
		productImg.setMedium(ImageUtil.scaleByHeightAndWidth(url,pathParent+"/"+nameArray[0]+"_medium."+nameArray[1], 600, 600, true,l));
		//生成小图
		productImg.setThumbnail(ImageUtil.scaleByHeightAndWidth(url,pathParent+"/"+nameArray[0]+"_thumbnail."+nameArray[1], 200, 200, true,l));
		return productImg;
	}
	/***
	 * 修改商品的基本信息，参数，属性，描述
	 * @param product
	 * @param shopProCategoryId
	 */
	public Boolean saveOrUpdateBasicProduct(ProductInfo product,Integer shopProCategoryId){
		Boolean flag = true;
		try{
			//店铺内部分类是否修改。若修改了，则修改中间表
			ShopProCateClass shopProCateClass = shopProCateClassDao.get("where o.productId="+product.getProductId());
			if(shopProCateClass!=null && shopProCateClass.getProductId()!=null && shopProCateClass.getShopProCategoryId().compareTo(shopProCategoryId)!=0){
				//使用sql语句的方式对原始数据进行更新
				shopProCateClassDao.updateObject(" update shop_shopprocateclass set shopProCategoryId="+shopProCategoryId+" where productId="+shopProCateClass.getProductId());
			}else{
				if(shopProCateClass==null){
					shopProCateClass = new ShopProCateClass();
					shopProCateClass.setProductId(product.getProductId());
					shopProCateClass.setShopProCategoryId(shopProCategoryId);
					shopProCateClassDao.saveOrUpdateObject(shopProCateClass);
				}
			}
			//修改商品基本信息
			product.setProductFullName(product.getProductFullName());
			frontProductInfoDao.saveOrUpdateObject(product);
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 修改商品图片
	 * @param productInfo商品信息
	 * @param listProductImage商品图片集合
	 * @param fileUrlConfig上传路径配置
	 * @param listProductUploadImgs上传路径集合
	 * @param listProductUploadImgsFileName上传名称集合
	 */
	public Boolean saveOrUpdateProdImg(ProductInfo productInfo,List<ProductImg> listProductImage){
		Boolean flag = true;
		try {
			productInfo = frontProductInfoDao.saveOrUpdateObject(productInfo);
			for(ProductImg productImg:listProductImage){
				if(productInfo!=null && productImg!=null){
					productImg.setProductId(productInfo.getProductId());
					productImgDao.saveOrUpdateObject(productImg);
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 解除该商品所在商品组的规格以及对应的规格值的关联关系
	 * @param productId  商品Id
	 * @actor lqs
	 */
	public boolean removeProductSpecificationValueGoodsGuanlian(Integer productId,String optionProductId) {
		//当前解除关联操作的规格组goodsId
		ProductInfo productInfo = baseDao.get(" where o.productId="+productId);
		Integer goods = productInfo.getGoods();
		//1、先获取商品表中的goods的最大值
		Integer maxGoods = (Integer) baseDao.getMaxDataSQL("select max(goods) from shop_productinfo");
		//2、将关联表中的goodId加一后进行修改
		String sql="update shop_product_specification_value set goodId = "+(maxGoods+1)+" where productId = "+productId;
		String sql_1="update shop_productinfo set isShow=1 where productId = "+productId;
		boolean isSuccess=baseDao.updateObject(sql);
		isSuccess=baseDao.updateObject(sql_1);
		//3、将商品表中的goods加一后进行修改
		sql="update shop_productinfo set goods = "+(maxGoods+1)+" where productId = "+productId;
		isSuccess=baseDao.updateObject(sql);
		
		//操作完商品的修改后 判断当前操作商品中是否还存在isShow=1的商品
		//根据goodsID查询当前isShow=1的商品
		int count = baseDao.getCount(" where o.goods="+goods+" and o.isShow=1");
		if (count == 0) {//等于0说明该goods中已经没有isShow等于1的商品了
			//将当前商品的isShow状态改为1
			String sql_2="update shop_productinfo set isShow=1 where productId = "+optionProductId;
			baseDao.updateObject(sql_2);
		}
		return isSuccess;
	}
	/**
	 * 修改规格
	 * @param 
	 * @throws Exception 
	 * @actor lqs
	 */
	public boolean updateFrontProductSpecification(String specifications,Integer oldProductId) throws Exception{
		boolean isSuccess=true;
		boolean isSaveFirst=true;
		int eachNum=0;//派生其他商品时sku循环执行次数
		if(specifications!=null && !"".equals(specifications)){
			boolean delFlag=true;
			String[] oneSplit = specifications.split(",");
			ProductInfo productInfo=null;
			List<ProductImg> piList=null;
			Integer productId=null;
			Integer goodId=null;
			for(String s : oneSplit){
				String[] isOldProductSplit=s.split("-");
				if(isOldProductSplit.length>=3){
					String[] secSplit = s.split("&");
					for(String ss : secSplit){
						String[] thirSplit = ss.split("=");
						Integer specificationValueId=Integer.parseInt(thirSplit[1]);
						String[] fourSplit = thirSplit[0].split("_");
						Integer specificationId=Integer.parseInt(fourSplit[1]);
						String[] fifthSplit =fourSplit[0].split("-");
						if(fifthSplit.length>1){
							if(goodId==null){
								goodId=Integer.parseInt(fifthSplit[0]);
							}
							productId=Integer.parseInt(fifthSplit[1]);
							productInfo=(ProductInfo) frontProductInfoDao.get(" where o.productId="+productId);
						}else{
							productId=productInfo.getProductId();
						}
						if(delFlag){
							delFlag=false;
							isSuccess=productSpecificationValueDao.deleteByParams(" where o.goodId="+goodId);
						}
						isSuccess=saveProductSpecificationValue(goodId,productId,specificationId,specificationValueId);
					}
					StringBuffer sb = new StringBuffer();
					sb=getProductFullName(productInfo,secSplit);
					productInfo.setProductFullName(sb.toString());
					productInfo=(ProductInfo) frontProductInfoDao.saveOrUpdateObject(productInfo);
				}else{
					eachNum++;//到此添加循环次数
					ProductInfo productInfoCopy=null;
					String[] secSplit = s.split("&");
					if(oldProductId!=null){
						productId = oldProductId;
					}
					if(productId!=null){
						//if(productInfo==null){
							productInfo=(ProductInfo) frontProductInfoDao.get(" where o.productId="+productId);
							goodId = productInfo.getGoods();
							int count =productSpecificationValueDao.getCount(" where o.goodId="+goodId+" and o.productId="+productInfo.getProductId());
							if(count==0&&isSaveFirst){//添加商品时没有添加规格
								StringBuffer sb = new StringBuffer();
								sb=getProductFullName(productInfo,secSplit);
								productInfo.setProductFullName(sb.toString());
								frontProductInfoDao.saveOrUpdateObject(productInfo);
								for(int i=0;i<secSplit.length;i++){
									String[] thirSplit = secSplit[i].split("=");
									Integer specificationValueId=Integer.parseInt(thirSplit[1]);
									String[] fourSplit = thirSplit[0].split("_");
									Integer specificationId=Integer.parseInt(fourSplit[1]);
									isSuccess=saveProductSpecificationValue(goodId,productId,specificationId,specificationValueId);
								}
								isSaveFirst = false;
							}else{
								StringBuffer sb = new StringBuffer();
								sb=getProductFullName(productInfo,secSplit);
								productInfoCopy = (ProductInfo) new CopyObject().copy(productInfo);
								productInfoCopy.setProductFullName(sb.toString());
								productInfoCopy.setProductId(null);
								productInfoCopy.setIsShow(0);
								productInfoCopy.setIsPass(2);
								//SKU
								//此处根据上边定义的eachNum进行循环遍历
								String sku = null;
								String[] selectColumns = {"sku","productId"};
								List<ProductInfo> list = frontProductInfoDao.findSome(selectColumns, 0, 1, " order by o.productId desc");
								if(list!=null&&list.size()>0){
									sku =util.other.SKUUtilityClass.getGeneratedSKU(list.get(0).getSku());
									if(eachNum>=2){
										for(int k=0;k<=eachNum-2;k++){
											sku =util.other.SKUUtilityClass.getGeneratedSKU(sku);//凡是超过两次循环的  均采用上边定义的sku进行计算
										}
									}
								}else{
									sku =util.other.SKUUtilityClass.getGeneratedSKU(null);
								}
								productInfoCopy.setSku(sku);
								productInfoCopy=(ProductInfo) frontProductInfoDao.saveOrUpdateObject(productInfoCopy);
								//操作商品绑定店铺内部分类
								ShopProCateClass oldShopProCateClass = (ShopProCateClass)shopProCateClassDao.get(" where o.productId='"+oldProductId+"'");
								ShopProCateClass shopProCateClass = new ShopProCateClass();
								shopProCateClass.setProductId(productInfoCopy.getProductId());
								shopProCateClass.setShopProCategoryId(oldShopProCateClass.getShopProCategoryId());
								ShopProCateClass saveOrUpdateObject = shopProCateClassDao.saveOrUpdateObject(shopProCateClass);
								//操作商品标签
								List<ShopProductTradeSituationTag> findObjects = shopProductTradeSituationTagDao.findObjects(" where o.productId="+oldProductId);
								if(findObjects!=null&&findObjects.size()>0){
									for(ShopProductTradeSituationTag sptt:findObjects){
										ShopProductTradeSituationTag st=new ShopProductTradeSituationTag();
										st.setProductId(saveOrUpdateObject.getProductId());
										st.setStId(sptt.getStId());
										st.setTtId(sptt.getTtId());
										shopProductTradeSituationTagDao.saveOrUpdateObject(st);
									}
								}
								//操作商品属性
								List<ProductAttrIndex> productAttrIndexList = productAttrIndexDao.findObjects("where o.productId="+productInfo.getProductId());
								for(ProductAttrIndex pai:productAttrIndexList){
									ProductAttrIndex productAttrIndexCopy = new ProductAttrIndex();
									productAttrIndexCopy.setAttrValueId(pai.getAttrValueId());
									productAttrIndexCopy.setProductAttrId(pai.getProductAttrId());
									productAttrIndexCopy.setProductTypeId(pai.getProductTypeId());
									productAttrIndexCopy.setProductId(productInfoCopy.getProductId());
									productAttrIndexDao.saveObject(productAttrIndexCopy);
								}
								if(productInfoCopy.getProductId()!=null){
									isSuccess=true;
								}else{
									isSuccess=false;
								}
								for(String ss : secSplit){
									String[] thirSplit = ss.split("=");
									Integer specificationValueId=Integer.parseInt(thirSplit[1]);
									String[] fourSplit = thirSplit[0].split("_");
									Integer specificationId=Integer.parseInt(fourSplit[1]);
									isSuccess=saveProductSpecificationValue(goodId,productInfoCopy.getProductId(),specificationId,specificationValueId);
								}
								//复制该组的商品图片到新添加规格对应生成的商品图片
								piList=(List<ProductImg>) productImgDao.findObjects(null," where o.productId="+productId);
								if(piList!=null&&piList.size()>0){
									for(ProductImg pi:piList){
										ProductImg productImg = new ProductImg();
										productImg.setLarge(pi.getLarge());
										productImg.setMedium(pi.getMedium());
										productImg.setOrders(pi.getOrders());
										productImg.setSource(pi.getSource());
										productImg.setThumbnail(pi.getThumbnail());
										productImg.setTitle(pi.getTitle());
										productImg.setProductId(productInfoCopy.getProductId());
										productImgDao.saveOrUpdateObject(productImg);
										if(pi.getProductImageId()!=null){
											isSuccess=true;
										}else{
											isSuccess=false;
										}
									}
								}
							}
						//}
					}
				}
			}
		}
		return isSuccess;
	}
	public boolean saveProductSpecificationValue(Integer goodId,Integer productId,Integer specificationId,Integer specificationValueId){
		//保存规格以及对应的规格值(完成)
		ProductSpecificationValue psv=new ProductSpecificationValue();
		psv.setGoodId(goodId);
		psv.setProductId(productId);
		psv.setSpecificationId(specificationId);
		psv.setSpecificationValueId(specificationValueId);
		productSpecificationValueDao.saveOrUpdateObject(psv);
		if(psv.getPsvId()!=null){
			return true;
		}else{
			return false;
		}
	}
	public StringBuffer getProductFullName(ProductInfo productInfo,String [] secSplit){
		StringBuffer sb = new StringBuffer();
		sb.append(productInfo.getProductName()+" [");
		for(String ss : secSplit){
			String[] thirSplit = ss.split("=");//把规格和规格值分开
			String[] fourSplit = thirSplit[0].split("_");//解析规格前缀，如：specification_1
			//规格值
			SpecificationValue sv = (SpecificationValue) specificationValueDao.get(" where o.specificationValueId='"+thirSplit[1]+"'");
			sb.append(sv.getName()+",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb;
	}
	public void setFrontProductInfoDao(IFrontProductInfoDao frontProductInfoDao) {
		this.baseDao = this.frontProductInfoDao = frontProductInfoDao;
	}
	public void setProductSpecificationDao(
			IProductSpecificationDao productSpecificationDao) {
		this.productSpecificationDao = productSpecificationDao;
	}
	public void setProductSpecificationValueDao(
			IProductSpecificationValueDao productSpecificationValueDao) {
		this.productSpecificationValueDao = productSpecificationValueDao;
	}
	public void setProductImgDao(IProductImgDao productImgDao) {
		this.productImgDao = productImgDao;
	}
	public void setShopProCateClassDao(IShopProCateClassDao shopProCateClassDao) {
		this.shopProCateClassDao = shopProCateClassDao;
	}
	public void setSpecificationValueDao(
			ISpecificationValueDao specificationValueDao) {
		this.specificationValueDao = specificationValueDao;
	}
	public void setShopProductTradeSituationTagDao(
			IShopProductTradeSituationTagDao shopProductTradeSituationTagDao) {
		this.shopProductTradeSituationTagDao = shopProductTradeSituationTagDao;
	}
	public void setProductAttrIndexDao(IProductAttrIndexDao productAttrIndexDao) {
		this.productAttrIndexDao = productAttrIndexDao;
	}
	public void setBrandDao(IBrandDao brandDao) {
		this.brandDao = brandDao;
	}
	
}
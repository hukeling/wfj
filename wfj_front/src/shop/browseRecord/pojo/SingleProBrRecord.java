package shop.browseRecord.pojo;
import java.util.Iterator;
import java.util.Vector;
/**
 * 商品浏览记录单例类对象
 * @author 张攀攀
 *
 */
public class SingleProBrRecord {
	//创建一个静态的最终的浏览商品记录对象
	private static final SingleProBrRecord singleProBrRecord = new SingleProBrRecord();
	//创建一个集合对象
	private Vector <ProBrRecord> v;
	//私有的构造方法
	private SingleProBrRecord(){
		//创建对象时，创建浏览商品记录集合
		v = new Vector<ProBrRecord>();
	}
	//调用此公开方法获得浏览商品记录单例类对象
	public static SingleProBrRecord getInstance(){
		return singleProBrRecord;
	}
	//添加商品浏览记录对象
	public void addProBrRecord(ProBrRecord proBrRecord){
		if(proBrRecord != null){
			v.add(proBrRecord);
		}
	}
	//移除商品浏览记录对象
	public void removeProBrRecord(ProBrRecord proBrRecord){
		if(proBrRecord != null){
			v.remove(proBrRecord);
		}
	}
	//移除所有商品浏览记录
	public void removeAllProBrRecord(){
			v.removeAllElements();
	}
	//获得商品浏览记录集合
	public Iterator<ProBrRecord> getSingleProBrRecord(){
		return v.iterator();
	}
	//获得商品浏览记录数量
	public int getProBrRecordCount(){
		return v.size();
	}
}

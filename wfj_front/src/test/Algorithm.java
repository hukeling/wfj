package test;
/**
 * 常见算法练习
 * @author hklpc
 *
 */
public class Algorithm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		selectSort();
	}
	
	/**
	 * 选择排序
	 */
	public static void selectSort(){
		int [] arr={1,-2,33,12,0,7};
		int []tempArr=new int[2];//存放最大值
		for(int i=0;i<arr.length-1;i++){
			tempArr[0]=i;
			tempArr[1]=arr[i];
			//获取数组中最大值及其下标
			for(int j=i+1;j<arr.length;j++){
			if(tempArr[1]<arr[j]){
				tempArr[0]=j;
				tempArr[1]=arr[j];
			}
		  }
			//当最大值下标变化时,将arr[i]的值与tempArr交换
			if(tempArr[0]!=i){
				int temp=arr[i];
				arr[i]=tempArr[1];
				arr[tempArr[0]]=temp;
				
			}
		}
		for(int k=0;k<arr.length;k++){
			System.out.println(arr[k]);
		}
		
	}
	/**
	 * 冒泡排序
	 */
	public static void bubbleSort(){
		int [] arr={1,-2,33,12,0,7};
		int len=arr.length;
//		for(int i=len-1;i>=0;i--){
		for(;--len>0;){
			for(int j=0;j<len;j++){
				if(arr[j]<arr[j+1]){
					int temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
		for(int k=0;k<arr.length;k++){
			System.out.println(arr[k]);
		}
	}
	
	
	
	
}

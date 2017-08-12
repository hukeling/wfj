package test;

public class TestRunnable implements Runnable{
	private int ticket=8;
	public void run() {
		for(int i=1;i<=5;i++){
			synchronized (this) {
				
				if(ticket>0){
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println(Thread.currentThread().getName()+",卖票：ticket="+ticket--);
				}
			}
			
		}
	}

	public static void main(String[] args) {
		TestRunnable TestRunnable1=new TestRunnable();
		new Thread(TestRunnable1).start();
		new Thread(TestRunnable1).start();
		new Thread(TestRunnable1).start();
		new Thread(TestRunnable1).start();
		
	}
	
	

}

package test;

public class TestThread extends Thread{

	private String name;
	private int ticket=5;
	public TestThread(String name){
		this.name=name;
	}
	@Override
	public void run() {
		for(int i=0;i<5;i++){
			if(ticket>0){
				System.out.println(name+"运行，i="+i+",ticket="+ticket--);
			}
		}
	}

	public static void main(String[] args) {
		new TestThread("线程A").start();
		new TestThread("线程B").start();

	}
	
	

}

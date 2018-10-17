package fps;



public class FpsMain {
	public static final int TARGET_FPS = 3;	
	
	 public static void main(String[] args) {
	       
	        
	        
	        double secsPerFrame = 1.0d / 50.0d;
	        double loopTime=System.nanoTime() / 1000_000_000.0;
	        double lastLoopTime=loopTime;
	        float loopSlot = 1f / TARGET_FPS;
	        
	        while (true) {
	        	
	                 
	                 
	                 System.out.println("Tik");
	                 double endTime = lastLoopTime + loopSlot;
	                 
	                 while (loopTime < endTime) {
	                	 loopTime = System.nanoTime() / 1000_000_000.0;
	                     try {
	                         Thread.sleep(1);
	                     } catch (InterruptedException ie) {
	                     }
	                 
	                 lastLoopTime=loopTime;
	            
	           
	        }
	        
	        
	    }

	 }
	 }

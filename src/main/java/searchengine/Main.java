package searchengine;

public class Main{	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EngineThreadFactory engine = new EngineThreadFactory(10);
		engine.run();
	}	
	
}

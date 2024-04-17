package test;

public abstract class BaseStudent {
	
	public void doFilter(String score) {
		doFilter(Integer.parseInt(score));	
	}
	
	public abstract void doFilter(int score);
	
}


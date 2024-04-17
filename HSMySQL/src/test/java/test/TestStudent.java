package test;

public class TestStudent {

	public static void main(String[] args) {
		BaseStudent studentA = new StudentA();
		BaseStudent studentB = new StudentB();
		studentA.doFilter(65);
		studentB.doFilter(85);
	}

}

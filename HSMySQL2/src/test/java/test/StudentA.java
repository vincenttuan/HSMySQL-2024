package test;

public class StudentA extends BaseStudent {

	@Override
	public void doFilter(int score) {
		if (score >= 60) {
			System.out.println("Student A passed the exam.");
		} else {
			System.out.println("Student A failed the exam.");
		}
	}

}

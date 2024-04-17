package test;

public class StudentB extends BaseStudent {
	@Override
	public void doFilter(int score) {
		if (score >= 90) {
			System.out.println("Student B passed the exam.");
		} else {
			System.out.println("Student B failed the exam.");
		}
	}
}

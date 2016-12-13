package kotlin;

import hr.neos.jvmlangs.Student;

public class KStudentJTest {

	public static void main(String[] args) {
		Student s = new Student("Miha", "Zagreb", null);
		System.out.println(s);
		s.setCity("Valpovo");
		System.out.println(s.getCity());
	}

}

package kotlin;

import hr.neos.jvmlangs.Student;

public class StudentJTest {

	public static void main(String[] args) {
		Student s = new Student("Miha", "Zagreb");
		s.setCity("Valpovo");
		
		System.out.println(s);
		System.out.println(s.getCity());
	}

}

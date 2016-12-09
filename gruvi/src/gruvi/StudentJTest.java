package gruvi;

import java.util.List;
import java.util.stream.Collectors;

public class StudentJTest {
	
	public static String getSomeCity() {
		return "Zagreb";
	}
	
	public static void main(String... args) {
		Student s = new Student("Groovy", "World");
		System.out.println(s.getName()); //ha, nisam moram definirati getter i setter
		List<Student> list = Student.generateList();
		
		{
			final String city = getSomeCity();
			System.out.println( list.stream().filter( a -> a.isFrom(city) ).collect(Collectors.toList()) );
		}
		
	}

}

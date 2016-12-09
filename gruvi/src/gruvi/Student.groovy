package gruvi

import groovy.json.*
import groovy.transform.TypeChecked

//@TypeChecked
class Student {
	String name //groovy automatically creates getter and setter
	String city 
	
	private Student() {
	}
	
	Student(name, city) {
		this.name = name
		this.city = city
	}
	
	boolean isFrom(city) { //no semi-colon
		city == this.city //last value is returned
	}
	
	@Override
	String toString() { //method is public by default
		"Student [name=" + name + ", city=" + city + "]"
	}
	
	void setCity(String city) { //setter and getter are called when working with fields, they are like wrappers
		this.city = 'City of ' + city
	}
	
	static List<Student> generateList() {
		return [
			new Student('Miha', 'Zagreb'), 
			new Student(name:'Ivo', city:'Zagreb'), //default constructor and then assigment of variables
			new Student('Bepo', 'Zagreb'), 
			new Student('Borko', 'Dubrovnik'), 
			new Student('Greta', 'Zagreb'), 
			]
	} 

	static void main(String... args) {
		List<Student> list = [
			new Student('Miha', 'Zagreb'), 
			new Student(name:'Ivo', city:'Zagreb'), //default constructor and then assigment of variables
			new Student('Bepo', 'Zagreb'), 
			new Student('Borko', 'Dubrovnik'), 
			new Student('Greta', 'Zagreb'), 
			]
		
		println('Number of students from Zagreb: ' + list.findAll {it.isFrom('Zagreb')}.size() )
		
		println "A beautiful list: ${list}" //parantheses are not needed but are handy in Eclipse for autocomplete
		println "Whole list has ${list.size()} elements" //power of string templates
		println "All names are ${ list*.name }"  //spread operator, apply it on each element		
		
		def student = new Student('Thor', null)
		Student lino = null
		assert student == new Student('Thor', null) // == calls equals and JAVA operator is a method "is"
		assert (lino != student) //remember how to do this in JAVA?
		//CHANGE ASSERT OPERATOR TO SEE BEAUTIFUL GROOVY INFO above
		assert student //true if not null, not empty, not zero
		assert !lino 
		
		println "I am avoiding null checks even in the template for student ${lino ?: 'lino'} because null is like false"
		Student splitStudent = list.find({ it.isFrom('Split') })
		println "A student from Split is named ${splitStudent?.name}"  //if object is null, return null, otherwise call method/property
		println "A student from Zagreb is named ${ list.find({ it.isFrom('Zagreb') }) ?.name}" 
		
		switch (student) { //powerful switch
			case new Student('Thor', null): 
				println '''
It is Thor.
Greet him!
''' //wau, multiline strings
				break
			case null: println 'No one important'; break
		}
		
		Student last = list[-1] //negative index counts from the end
		println "Last student is ${last}"
		last.city = 'Hum' //setter for city was redefined
		println "City was changed to '${last.city}'"
		
		def map = [tool: 'spoon', food: 'pancakes']
		println "map ${map} is of ${map.getClass()}"
		
		println "Generating some data with range and mapping function: ${ (1..3).collect {it*2} }"
		
		def mult = { int a, int b -> a + b }		
		def add3 = mult.curry(3) //currying
		def mult2 = { int n -> n * 2}
		def mult2AndAdd3 = add3 << mult2
		assert mult2AndAdd3(2) == 2*2+3 //function composition
		
		def jsonOut = JsonOutput.toJson(last)
		
		def ip = 'http://bot.whatismyipaddress.com/'.toURL().text
		println "My ip is ${ip}"
		def jsonIn = 'https://api.github.com/repos/mih0vil/JvmLangsTest1/branches'.toURL().text
		println jsonIn
		def branches = new JsonSlurper().parseText(jsonIn)
		println "First branch has a name ${branches[0].name}"
			
		def (prvi, drugi) = list //list decomposition
		(prvi, drugi) = [drugi, prvi]
		
		//Zrinkin primjer
		def city = 'Split'
		city = getSomeCity()
		println "Students from some city: ${ list.findAll { it.isFrom(city) } }"
		
		
//		assert prvi == list[0]
	}
	
	public static String getSomeCity() {
		return "Zagreb";
	}

	//Eclipse can generate hash code and equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		//if (this == obj)
		if (this.is(obj)) //beware of this when auto-generated
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}

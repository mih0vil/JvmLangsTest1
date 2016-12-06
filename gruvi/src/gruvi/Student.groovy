package gruvi

import groovy.transform.TypeChecked

@TypeChecked
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

	static void main(String... args) {
		List<Student> list = [
			new Student('Miha', 'Zagreb'), 
			new Student(name:'Ivo', city:'Zagreb'), //default constructor and then assigment of variables
			new Student('Bepo', 'Zagreb'), 
			new Student('Borko', 'Dubrovnik'), 
			new Student('Greta', 'Zagreb'), 
			]
		
		println(list.findAll {it.isFrom('Zagreb')}.size() )
		
		list.findAll { it.isFrom('Zagreb') }.size()
		
		println "A beautiful list: ${list}" //parantheses are not needed but are handy in Eclipse for autocomplete
		println "Whole list has ${list.size()} elements" //power of string templates		
		
		def student = new Student('Thor', null)
		Student lino = null
		assert student == new Student('Thor', null) // == calls equals and JAVA operator is a method "is"
		assert (lino != student) //remember how to do this in JAVA?
		//CHANGE ASSERT OPERATOR TO SEE BEAUTIFUL GROOVY INFO above
		assert student //true if not null, not empty, not zero
		assert !lino 
		
		println "I am avoiding null checks even in the template for student ${lino ?: 'lino'}"
		
		switch (student) {
			case new Student('Thor', null): 
				println '''
It is Thor.
Greet him!
''' //wau, multiline strings
				break
			case null: println 'No one important'; break
		}
		
	}

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

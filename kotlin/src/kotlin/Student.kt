package hr.neos.jvmlangs

data class Student (val name: String, private var _city: String, var artist: Boolean? = null) { //data implements hashcode, equals, toString	on parameters
	
	//when you want to create a custom setter, you have to create a property and hide constructor parameter with private
	var city: String = _city
			set(x) {
				_city = "City of " + x;
				field = _city
			}
	
	//short function
	fun isFrom(city: String) = _city == city 
	
}

fun generateList() = mutableListOf(
		Student("Miha", "Zagreb"), 
		Student(_city = "Zagreb", name = "Ivo"), //default constructor and then assigment of variables
		Student("Bepo", "Zagreb"), 
		Student("Borko", "Dubrovnik"), 
		Student("Greta", "Zagreb") 
)

//you can have function outside of class like this runnable function
fun main(args: Array<String>) {
    println("Hello, world!")
	
	//if it is not public property, you do not need to define type
	val list = generateList()
	println("Number of students from Zagreb: " + list.filter { it.isFrom("Zagreb") }.size )
	println("Whole list has ${list.size} elements") //power of string templates
	println(list[0] == Student("Miha", "Zagreb")) //it is equal because we added data to class and operator == is like equals in JAVA

//	val count = list.filter { it.isFrom("London") }.size
//	println(count)
	
	//Kotlin compiler forces different null handling
//	var student = Student("Thor", null) //city can not be null
	var student: Student = Student("Thor", "Valhalla", false) //student can not be null
	var lino: Student? = null //lino can be null
	println("equals on null object: ${lino == student}") //no null point exception although lino is null
	println("A student from Zagreb is named ${ list.find { it.isFrom("Zagreb") } ?.name ?: "<none>" }") //possible null point, compiler forces checking  
	
	when (student) { //powerfull switch, no break 
		in list -> println("Student is in the list") //contained in the list
		Student("Thor", "Valhalla", false) -> println("""
				|It is Thor
				|Great him!
				""".trimMargin("|")) //trims margin to the desired character, for multiline string
		is Student -> println("it is a student") //type of
		else -> println("when...else...")
	}
	
	var (a, b) = listOf(Student("miha", "zgb"), student) //list decompose
	var (name) = a //parameter decompose
	println ("Name of the first in the list is ${name}")
	
	val map = hashMapOf("tool" to "spoon", "food" to "pancakes") //defining map
	println ("map ${map} is of ${map.javaClass }")
	
	println("Generating some data with range and mapping function: ${ (1..3).toList().map { it*2 } }")

	//lambda argument does not need to be final
	var city = "Split"
	city = getSomeCity()
	println("Students from some city: ${ list.filter{ it.isFrom(city) } }")
	
	val obj: Any = city //Any is like an Object in JAVA
	if (obj is String) //smart cast, in the following block, obj is of type String
		println("Object is a string of length ${obj.length}")
	
	for ((i, value) in list.withIndex()) { //iterating list with indexes
		println("Index ${i}: ${value}")
	}
}

tailrec fun findFixPoint(x: Double = 1.0): Double //tail recursion which translate into a loop
        = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))


fun getSomeCity() = "Zagreb"
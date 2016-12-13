package hr.neos.jvmlangs

data class Student (val name: String, private var _city: String) { //data implements hashcode, equals, toString	on parameters
	
	//when you want to create a custom setter, you have to create a property and hide constructor parameter with private
	var city: String = _city
			set(x) {
				_city = "City of " + x;
				field = _city
			}
	
}


fun main(args: Array<String>) {
    println("Hello, world!")
}

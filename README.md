# Case study on JVM languages for JEE (Java Enterprise Edition)
I wanted to try out some JVM languages other than JAVA. On my working place I need to use **JAVA 6 and Eclipse**. What I am missing is the modern paradigm of functional languages not present in Java 6. Still, in the study I used JDK 1.8 and JEE 7 but I've chosen languages which work on JVM 6. I'll desribe what I found to be interesting and which problems did I come accross during development with the chosen tools. What I did is a simple JEE application including EAR project, JSF (JavaServer Faces), EJB (Enterprise Java Beans) with JPA (Java Persistence API) and I did try to implement the same thing using 3 different languages.

**JVM languages tested**:
* JAVA
* Groovy
* Kotlin

**Tools and requirments**:
* Eclipse Mars.2
* JDK 1.8
* Glassfish 1.4
  * Datasource for HR schema in Oracle database

## Groovy

This language has great eco system making it worth learning just because of that. Here are some interesting frameworks:
* Grails: full stack web app framework inspired by Ruby on Rails
* Gradle: Powerful build automation system
* Spock: testing and specification framework
* Geb: browser functional testing, integrating with Spock

Here is my opinion on the development after doing the project:
* **Groovy recognises correctly 90% of JAVA code** which means that as a JAVA developer you can immediately transition to Groovy. For an existing project, you can pick up Java file and just rename it to Groovy and most of the things will work just fine.
* Operator `==` is a call to method `equals` in Java. Java operator `==` is operator `is` in Groovy.
* I like static type checking in Java. You can add annotations for compiler to force **type checking** and **static compilation** to boost performance. For this you can use `@TypeChecked` and `@CompileStatic`.
* **Functional paradigm**. It's so cool to use *filter* and *map* on JVM 6. 
  * Groovy Closures here this simple use seem to be more powerful than Java Lambda. E.g. in JAVA whatever value you use in Lambda which is defined outside of Lambda block, this value needs to be effectively final. In Groovy Closure this is not required.
```groovy
		def city = 'Split'
		city = getSomeCity() //this is not final, this would not work in Java
		println "Students from some city: ${ list.findAll { it.isFrom(city) } }"
```
* **Getter and setter are generated automatically**. This is good for JSF Managed Beans which require properties with getters and setters. Looking at Java version this generated code (getters and setters) is mixed with manually written code and it is distracting.
* **Null** handling is much easier to use. 
``` GROOVY
		Student lino = null
		assert student == new Student('Thor', null) // == calls equals and JAVA operator is a method "is"
		assert (lino != student) //remember how to do this in JAVA?
    //....
    def country = it?.department?.location?.country?.countryName
    def display = country ? "${it.firstName} ${it.lastName} (${country})" 
      : "${it.firstName} ${it.lastName}"    
```
* String templates are also nice in Groovy.
* **Switch** statement is much more powerful. You can check for equality with any object, check type of the object, check if it is member of a collection, check if it matches an expression defined in a clojure or matched by regular expression.
* You need to add Groovy libraries to *library* directory of *EAR* project.

**Eclipse experience**:
* On Eclipse you need to add *groovy-eclipse* plugin. It will compile Groovy files into .class and this works fine.
* You can use Eclipse to generate *hashCode* and *equals*. In *equals* Java will use operator `==` which has equivalent to `is` in Groovy. You just need to change this line in the Eclipse generated code.
* *JPA does not validate* correctly POGO classes. What I usually do is generate JPA Java entities from the table so this is not a big deal as this is generated automatically.
* Changing the name of a method or a field by *refactoring* sometimes does not work well. This was mostly experience in Eclipse Neon, Eclipse Mars worked better.
* *Autocomplete* of methods does not always generate all available methods in expression even with static type checking enabled. So sometimes you need to go to documentation or you can try to assign the expression to a variable and check the autocomplete of methods for that variable.
* I could not always put static compilation annotation because groovy plugin would produce error and would not be able to compile the class. I am not sure in which cases it works and in which it does not.
* JSF XHTML document did not have autocomplete for managed bean implemented in groovy. It could be because I could not put static compilation annotation on it due to a bug in groovy plugin.


## Kotlin

* Small library size
* developed by JetBrains
* popular in Android community which also mostly uses Android Studio developed by JetBrains
* in their documentation one can see that motivation for a specific langauge design comes from the famouse book *Effective Java*. So the idea was to make Java a little bit **more effective** for a programmer
  * different null handling. One can define a variable which can not have null assigned and this is forced by the compiler. And if you want it to be null, it has nice null handling like in groovy.
  * motivates immutability and closed classes
  * **high-order functions**
    * extended collections with popular functional interfaces (like in Groovy and Java 8)
    * tail recursion functions
    * like in groovy, parameters of the lambda expression do not need to be effectively final (like in Java 8)
* **string templates**
* more powerful **switch** statement

Notes on developing in Kotlin:
* names of the parameters of class constructor are not visible from Java (they are named arg1, arg2, ...)
* dependency injection kind of a kills some of the goodies in Kotlin
  * one can not specify non-nullable field because this field is initialised with null and only by dependency injection it gets the required value. There is `lateinit` which did not work for me
  * managed beans need to be non final classes and their properties and methods need to be non final as well which requires extra keywords in Kotlin. This should be fixed in future versions of Kotlin somehow.

Notes for developing in Kotlin with Eclipse:
* one needs Kotlin plugin available from Eclipse Marketplaces
* refactoring is unavailable (change names of the properties and methods)
* auto-complete for annotations does not work until you import the annotation. I had to consult the documentation to know the right package.
* there is a tool for converting Java to Kotlin (but not the opposite)
* plugin did not help me to sucessfully deploy EAR project to JEE app server. It did not generate *.class* files but copied .kt (extension for Kotlin) to build directory
  * therefore I had to make a Gradle project to compile Kotlin files but this required a total build of JAVA, Groovy and Kotlin files as well

# Conclusion

Kotlin looks very promising but at the moment if you are using Eclipse, you need to set it well with Gradle to enjoy development. It also seems to me that it does not fit Java as good as Groovy does. It would be worth trying Spring Boot with Kotlin but I would then enjoy building it with Kotlin for the majority of the project. I guess that performance of Kotlin should be better then Groovy and maybe better then Java. And of course, it sure works well for Android development as there are many reports on the web which proove that. Groovy fits better for me on JEE and Eclipse. It sure makes code more readable and enjoyable to work on comparing to Java version. And if one does not know how to do something the groovy way, one can always write it in Java inside of the Groovy class making it easy to transit to for Java developers.

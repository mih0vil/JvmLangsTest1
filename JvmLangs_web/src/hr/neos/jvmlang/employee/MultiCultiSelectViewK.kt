package hr.neos.jvmlang.employee

import javax.inject.Named
import org.omnifaces.cdi.ViewScoped
import java.io.Serializable

@Named
@ViewScoped
open class MultiCultiSelectViewK : Serializable {

	val test = "Testing Kotlin"
		
	
}
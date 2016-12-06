package gruvi

class FirstGruvi {
	                                    
    static void main(String... args) {
		def f = new File('.\\src\\gruvi')
		println(f.listFiles())
		f.listFiles().each {it -> println(it.absolutePath + '\n*********************\n' + it.getText())}
		
		def list = [5, 6, 7, 8]
		
    }
}

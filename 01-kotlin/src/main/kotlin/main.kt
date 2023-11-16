abstract class Numeros(
    protected  val numUno: Int,
    protected val numDos: Int,
){
    init {
        this.numUno; this.numDos;
        numUno; numDos;
        println("Inicializando")
    }
}

class Suma( //Constructor primario suma
    uno: Int,
    dos: Int
): Numeros(uno, dos){ //LA CLASE SUMA TIENE A LA CLASE PADRE NUMEROS
    init{
        this.numUno; numUno;
        this.numDos; numDos;
    }

    constructor(
        uno: Int?,
        dos: Int
    ): this(
        if (uno==null)0 else uno, dos

    ){
        numeroUno;
    }



}

fun main(args: Array<String>) {
    println("Hello World!")


    //TIPOS DE VARIABLES
    //- INMUTABLES (No se reasignan "=") *constantes*
    val inmutable: String = "Adrian";
    //-MUTABLES (reasignan)
    var mutable: String ="Vicente";
    mutable ="Danna"

    //val>var
    //DUCK TYPING
    var ejemploVariable = "ADRIAN e"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()

    //VARIABLE PRIMITIVA
    //val nombreProfe

    //FUNCIONES
    //VOID -> UNIT (no obligatorio)
    fun imprimirNombre(nombre: String): Unit{
        //TEMPLATE STRINGS

        println("Nombre: ${nombre}")
    }

    fun calcularSueldo(
        sueldo: Double,
        tasa: Double = 12.00,
        bonoEspecial: Double? = null,

    ): Double{
        if(bonoEspecial == null){
            return  sueldo * (100/tasa)
        }else{
            bonoEspecial.dec()
            return sueldo* (100/tasa) +bonoEspecial
        }

    }


    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 15.00)
    calcularSueldo(10.00, 15.00)
    //PARAMETROS NOMBRADOS
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 14.00, sueldo = 5.00, tasa = 5.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)



}
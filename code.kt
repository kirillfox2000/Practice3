sealed class Result<T>
class Success<T>(val data : T) : Result<T>()
class Error<T>(val errorMessage  : String = "Error") : Result<T>()

enum class Operation{
    SORT_ASC 
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.sorted()
        }
    },
    
    SORT_DESC 
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.sortedDescending()
        }
    },
    
    SHUFFLE 
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.shuffled()
        }
    };
    
    abstract operator fun <T : Comparable<T>> invoke(list: List<T>): List<T>
}

fun <T : Comparable<T>> List<T>.operate(operation: Operation): Result<List<T>>
    {
        if (this.isEmpty()) return Error("Empty")
        
        when (operation)
        {
            Operation.SORT_ASC -> return Success(Operation.SORT_ASC(this))
            Operation.SORT_DESC -> return Success(Operation.SORT_DESC(this))
            Operation.SHUFFLE -> return Success(Operation.SHUFFLE(this))
    	}
    
}   

fun generateStrings(stringsLenght: Int, length : Int) : List<String>
{
    val availableSymbols = ('A'..'Z') + ('a'..'z')
    var readyString = mutableListOf<String>()
    for(i in 1..length) readyString.add((1..stringsLenght).map { availableSymbols.random() }.joinToString(""))
    return readyString
}


fun generateIntegers(length : Int) : List<Int>
{
    return (-100..100).shuffled().take(length)
}
    
fun <T : Comparable<T>> Result<List<T>>.print(): Unit
{
    if (this is Error) println(this.errorMessage)
    	else if (this is Success) println(this.data)
}

fun main() {   
    var listStrings = generateStrings(5,6)
    println("Start list: $listStrings")
    println("After sorting by ASC:")
    listStrings.operate(Operation.SORT_ASC).print()
    println("After sorting by DESC:")
    listStrings.operate(Operation.SORT_DESC).print()
    println("After SHUFFLE:")
    listStrings.operate(Operation.SHUFFLE).print()
    
    println("")
    
    var listIntegers = generateIntegers(6)
    println("Start list: $listIntegers")
    println("After sorting by ASC:")
    listIntegers.operate(Operation.SORT_ASC).print()
    println("After sorting by DESC:")
    listIntegers.operate(Operation.SORT_DESC).print()
    println("After SHUFFLE:")
    listIntegers.operate(Operation.SHUFFLE).print()
}

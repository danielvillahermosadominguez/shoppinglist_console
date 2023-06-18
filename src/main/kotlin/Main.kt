fun main(args: Array<String>) {
    val userInteraction = UserInteraction()

    val shoppingListConsole = ShoppingListConsole(userInteraction,  ShoppingListService(), PrinterForSystemOutput(System.out),)
    shoppingListConsole.start()
}

class UserInteraction : SystemConsoleInteraction {
    override fun readInput(): String {
        var input = readlnOrNull()
        if (input == "") {
            input = ""
        }
        return input!!
    }
}

fun main(args: Array<String>) {
    val userInteraction = UserInteraction()

    val shoppingListConsole = ShoppingListConsole(userInteraction,  ShoppingListService(), Printer(System.out),)
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

    fun cleanScreen() {
       /* try {
            if (System.getProperty("os.name").contains("Windows")) {
                ProcessBuilder("cmd", "/c", "cls").start().waitFor()
            } else {
                Runtime.getRuntime().exec("Clear")
            }
        } catch (ex:Exception) {
            System.out.println(ex.message)
        }*/
        System.out.print('\u000c');
    }
}

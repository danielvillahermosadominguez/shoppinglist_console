private const val OPTION_ADD = "1"
private const val QUIT = "q"
private const val YES = "y"


class ShoppingListConsole(
    private val userInteraction: SystemConsoleInteraction,
    private val shoppingListService: ShoppingListService,
    private val printer: Printer
) {
    fun start() {
        var errorMessage = ""
        do {
            val shoppingListItems = shoppingListService.getCurrentItems()
            printer.printItems(shoppingListItems, errorMessage)
            errorMessage = ""
            val input = userInteraction.readInput()
            if (input.lowercase().trim() == QUIT) {
                exit(input)
                return
            } else if (input.lowercase().trim() == OPTION_ADD) {
                if (!addItem()) return
            } else {
                errorMessage = "Error: \"$input\" is not a correct option"
            }
        } while (true)
    }


    private fun addItem(): Boolean {
        printer.requestDataToAddItemStep1Name()
        val name = userInteraction.readInput()
        printer.requesteDataToAddItemStep2Quantity(name)
        val quantityStr = userInteraction.readInput()
        val quantity = quantityStr.toInt()
        printer.requestDataToAddItemsStep3ContinueOrExit(quantity)
        val answer = userInteraction.readInput()
        if (answer.lowercase().trim() == QUIT) {
            exit(answer)
            return false
        }
        if (answer.lowercase().trim() == YES) {
            shoppingListService.add(ShoppingListItem(name, quantity))
        }
        return true
    }

    private fun exit(input: String) {
        printer.exitMessage(input)
    }

}

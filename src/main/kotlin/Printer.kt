import java.io.OutputStream
import java.io.PrintStream

open class Printer(open val outputStream: OutputStream) {

    fun printItems(shoppingListItems: List<ShoppingListItem>, errorMessage:String) {
        cleanScreen()
        printHeader()
        for (item in shoppingListItems) {
            PrintStream(outputStream).printf("| %-30s | %-36s |\n", item.name, item.quantity)
        }
        printMenu()
        printError(errorMessage)
        printRequestAction()
    }

    fun requestDataToAddItemStep1Name() {
        cleanScreen()
        print(PLEASE_INSERT_THE_NEW_ITEM_BY_INCLUDING_A_NAME_AND_A_QUANTITY_N)
        print(ITEM_NAME);
    }
    fun requesteDataToAddItemStep2Quantity(nameValue: String) {
        print("$nameValue\n$ITEM_QUANTITY");
    }

    fun requestDataToAddItemsStep3ContinueOrExit(quantityValue: Int) {
        print("$quantityValue\n$DO_YOU_WANT_INSERT_ITEM");
    }

    fun exitMessage(input: String) {
        print(" $input\n")
    }

    fun startAddItem() {
        cleanScreen()
        requestDataToAddItemStep1Name()
    }

    private fun print(text:String) {
        PrintStream(outputStream).print(text)
    }

    private fun printError(errorMessage:String) {
        if(!errorMessage.isNullOrBlank()) {
            print("$errorMessage\n")
        }
    }
    private fun printMenu() {
        print(SEPARATOR);
        print(ACTIONS_LEGEND)
        print(SEPARATOR)
    }
    private fun printHeader() {
        print(TITLE)
        print(TABLE_HEADERS);
    }

    private fun printRequestAction() {
        print(REQUEST_USER_ACTION_MESSAGE)
    }


    open fun cleanScreen() {

    }

    companion object {
        private const val SEPARATOR = "-------------------------------------------------------------------------\n"
        private const val ACTIONS_LEGEND = "Actions: (1) add (2) remove (3) update (4) delete (5) delete all (Q) Exit\n"
        private const val TITLE = "------------------------ Shopping list ----------------------------------\n"
        private const val TABLE_HEADERS = "|      Item                      |           Quantity                   |\n"
        private const val REQUEST_USER_ACTION_MESSAGE = "Please push your action:"
        private const val PLEASE_INSERT_THE_NEW_ITEM_BY_INCLUDING_A_NAME_AND_A_QUANTITY_N = "Please insert the new item by including a name and a quantity:\n"
        private const val ITEM_NAME = "name: "
        private const val ITEM_QUANTITY = "quantity: "
        private const val DO_YOU_WANT_INSERT_ITEM = "Do you want to insert this item? (Y = Yes, other key = No, Q = Exit):"
    }
}

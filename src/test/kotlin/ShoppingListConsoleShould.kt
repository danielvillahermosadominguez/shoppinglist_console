import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

class ShoppingListConsoleShould {
    private var waitForUserInput: SystemConsoleInteraction = mockk()
    private var shoppingListService: ShoppingListService = mockk(relaxed = true)
    private var outputStream = ByteArrayOutputStream()
    private lateinit var console: ShoppingListConsole

    @BeforeEach
    fun beforeEach() {
        clearMocks(waitForUserInput, shoppingListService)
        outputStream = ByteArrayOutputStream()
        val printer = PrinterForByteArrayOutput(outputStream)
        console = ShoppingListConsole(waitForUserInput, shoppingListService, printer)
    }

    @Test
    fun `show an empty list and a summary of actions when the console start`() {
        every { waitForUserInput.readInput() } returns "Q"
        every { shoppingListService.getCurrentItems() } returns listOf()

        console.start()

        assertThat(String(outputStream.toByteArray())).isEqualTo(
            """
        ------------------------ Shopping list ----------------------------------
        |      Item                      |           Quantity                   |
        -------------------------------------------------------------------------
        Actions: (1) add (2) remove (3) update (4) delete (5) delete all (Q) Exit
        -------------------------------------------------------------------------
        Please push your action: Q

        """.trimIndent()
        )
    }

    @Test
    fun `show a message of error when user choose a not expected input`() {
        every { waitForUserInput.readInput() } returnsMany (listOf("A", "Q"))
        every { shoppingListService.getCurrentItems() } returns listOf()

        console.start()

        assertThat(String(outputStream.toByteArray())).isEqualTo(
            """             
        ------------------------ Shopping list ----------------------------------
        |      Item                      |           Quantity                   |
        -------------------------------------------------------------------------
        Actions: (1) add (2) remove (3) update (4) delete (5) delete all (Q) Exit
        -------------------------------------------------------------------------
        Error: "A" is not a correct option
        Please push your action: Q
        
        """.trimIndent()
        )
    }

    @Test
    fun `show a list of items when this list is not empty`() {
        every { waitForUserInput.readInput() } returns "Q"
        every { shoppingListService.getCurrentItems() } returns listOf(
            ShoppingListItem("BREAD", 1),
            ShoppingListItem("MILK", 2)
        )

        console.start()

        assertThat(String(outputStream.toByteArray())).isEqualTo(
            """
        ------------------------ Shopping list ----------------------------------
        |      Item                      |           Quantity                   |
        | BREAD                          | 1                                    |
        | MILK                           | 2                                    |
        -------------------------------------------------------------------------
        Actions: (1) add (2) remove (3) update (4) delete (5) delete all (Q) Exit
        -------------------------------------------------------------------------
        Please push your action: Q
        
        """.trimIndent()
        )
    }

    @Test
    fun `ask the name and the quantity when the user add a new item`() {
        every { waitForUserInput.readInput() } returnsMany (listOf(
            "1",
            "CARROT",
            "3",
            "Q"
        ))
        every { shoppingListService.getCurrentItems() } returns listOf(
            ShoppingListItem("BREAD", 1),
            ShoppingListItem("MILK", 2)
        )

        console.start()

        assertThat(String(outputStream.toByteArray())).isEqualTo(
            """
                Please insert the new item by including a name and a quantity:
                name: CARROT
                quantity: 3
                Do you want to insert this item? (Y = Yes, other key = No, Q = Exit): Q
                
                """.trimIndent()
        )
    }

    @Test
    fun `add a new item when the user confirm to add the item`() {
        every { waitForUserInput.readInput() } returnsMany (listOf(
            "1",
            "CARROT",
            "3",
            "Y",
            "Q"
        ))

        every { shoppingListService.getCurrentItems() } returnsMany (listOf(
            listOf<ShoppingListItem>(
                ShoppingListItem("BREAD", 1),
                ShoppingListItem("MILK", 2)
            ),
            listOf<ShoppingListItem>(
                ShoppingListItem("BREAD", 1),
                ShoppingListItem("MILK", 2),
                ShoppingListItem("CARROT", 3)
            )
        ))

        console.start()

        assertThat(String(outputStream.toByteArray())).isEqualTo(
            """
        ------------------------ Shopping list ----------------------------------
        |      Item                      |           Quantity                   |
        | BREAD                          | 1                                    |
        | MILK                           | 2                                    |
        | CARROT                         | 3                                    |
        -------------------------------------------------------------------------
        Actions: (1) add (2) remove (3) update (4) delete (5) delete all (Q) Exit
        -------------------------------------------------------------------------
        Please push your action: Q
        
        """.trimIndent()
        )
    }
}


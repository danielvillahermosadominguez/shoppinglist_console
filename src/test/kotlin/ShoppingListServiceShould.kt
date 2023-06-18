import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShoppingListServiceShould {

    @Test
    fun `add an item`() {
        val shoppingListService = ShoppingListService()

        shoppingListService.add(ShoppingListItem("name", 1))

        val currentItems = shoppingListService.getCurrentItems()
        assertThat(currentItems.size).isEqualTo(1)
        assertThat(currentItems[0].name).isEqualTo("name")
        assertThat(currentItems[0].quantity).isEqualTo(1)
    }
}
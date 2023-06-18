class ShoppingListService {
    val list = ArrayList<ShoppingListItem>()
    fun getCurrentItems() : List<ShoppingListItem>{
        return list
    }

    fun add(shoppingListItem: ShoppingListItem) {
        list.add(shoppingListItem)
    }

}

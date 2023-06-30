package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item saveItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 20);

        //when
        Item saveItemA = itemRepository.save(itemA);
        Item saveItemB = itemRepository.save(itemB);

        //then
        List<Item> result = itemRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item saveItem = itemRepository.save(itemA);
        Long saveId = saveItem.getId();
        //when
        Item newItem = new Item("itemB", 20000, 20);
        itemRepository.update(saveId, newItem);
        //then
        Item findItem = itemRepository.findById(saveId);
        assertThat(findItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(newItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(newItem.getQuantity());
    }

}
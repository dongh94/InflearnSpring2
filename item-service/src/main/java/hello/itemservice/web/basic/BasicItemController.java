package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;
// ReqiredArgsConstructor로 대체
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * RequestParam 사용 - 파라미터로 올 때
     * @param itemName
     * @param price
     * @param quantity
     * @param model
     * @return
     */
//    @PostMapping("/add")
    public String saveV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }
    /**
     * ModelAttribute 사용 - 파라미터로 올 때
     * @ModelAttribute name 생략 가능 - 객체로 만들어준다.
     * model.addAttribute(item); 자동 추가, 생략 가능 - 객체로 뿌린다.
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
//    @PostMapping("/add")
    public String saveV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV3(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * Redirect사용해야 새로고침시 Post를 계속 안뿌린다.
     * @param item
     * @return
     */

//    @PostMapping("/add")
    public String saveV4(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * RedirectAttributes - 웹 주소창에 찍히는 값
     * @param item
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/add")
    public String saveV5(Item item,  RedirectAttributes redirectAttributes) {
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());
        // 이 true를 이용해 화면이나 모달을 띄울수도 있게된다.
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}

package com.example.springmvcwithitemservice.web.basic;

import com.example.springmvcwithitemservice.domain.item.Item;
import com.example.springmvcwithitemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        final List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        final Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addFrom() {
        return "basic/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/items";
    }

    //    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item,
//            @ModelAttribute 의 정체는 2가지 역할,
            Model model
    ) {
        itemRepository.save(item);

//        model.addAttribute("item", item); 알아서 담겨준다.

        return "basic/items";
    }

    //    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item,
//            @ModelAttribute 의 정체는 2가지 역할,
            Model model
    ) {
        itemRepository.save(item);

//        model.addAttribute("item", item); 알아서 담겨준다.

        return "basic/items";
    }

    /**
     * // model.addAttribute(item) 자동 추가
     *
     * @param item
     * @return
     */
//    @PostMapping("/add")
    public String addItemV4(Item item

    ) {
        itemRepository.save(item);
        return "basic/items";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        final Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        // query parameter 로 넘어간다.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemaA", 10000, 10));
        itemRepository.save(new Item("itemaB", 20000, 20));
    }

}

package com.blog.jpashop.item.presentation;

import com.blog.jpashop.item.application.ItemService;
import com.blog.jpashop.item.domain.Book;
import com.blog.jpashop.item.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 목록 페이지
     * @param model
     * @return
     */
    @GetMapping("/items")
    public String itemsView(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    /**
     * 상품등록 페이지
     * @return
     */
    @GetMapping("/items/new")
    public String createForm() {return "items/createItemForm";}

    /**
     * 상품 등록 요청
     * @param item
     * @return
     */
    @PostMapping("/items/new")
    public String create(Book item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }


    @GetMapping("/items/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        model.addAttribute("item",item);
        return "items/editForm";
    }

    @PostMapping("/items/{itemId}")
    public String edit(@ModelAttribute("item") Book item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }

}

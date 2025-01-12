package com.xpresso.cafe.controller;

import com.xpresso.cafe.model.Product;
import com.xpresso.cafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/add")
    public String addProduct(Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/product_list"; 
        }
        productService.addProduct(product);
        return "redirect:/product_list"; 
    }



    @PostMapping("/update")
    public String updateProduct(Product product) {
        productService.updateProduct(product);
        return "redirect:/product_list";
    }

    @RequestMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") String id, Model model) {
        Product p = productService.getProductById(id);
        model.addAttribute("product", p);
        return "productUpdate";
    }

    @GetMapping("/product_add")
    public String productAdd() {
        return "productAdd";
    }

    @GetMapping("/product_list")
    public ModelAndView getAllProduct(String keyword) {
        List<Product> list;
        if (keyword != null) {
            list = productService.getProductPartial(keyword);
            if (keyword.equals(""))
                list = productService.getAllProduct();
        }else
            list = productService.getAllProduct();
        return new ModelAndView("product", "productList", list);
    }

    @RequestMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/product_list";
    }
}

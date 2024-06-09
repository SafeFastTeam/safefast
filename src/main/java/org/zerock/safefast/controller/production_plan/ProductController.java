package org.zerock.safefast.controller.production_plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.safefast.entity.Product;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.service.production_plan.ProductService;

import java.util.List;

@Controller
@RequestMapping("/productionPlan")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/productionPlan")
    public String showProductionPlan(Model model) {
        List<Product> products = productService.getAllProducts();
        List<ProductionPlan> productionPlans = productService.getAllProductionPlans();
        model.addAttribute("products", products);
        model.addAttribute("productionPlans", productionPlans);
        model.addAttribute("productionPlan", new ProductionPlan());
        return "production_plan/production_plan";
    }

    @PostMapping("/save")
    public String saveProductionPlan(@RequestParam("productCode") String productCode, @ModelAttribute ProductionPlan productionPlan) {
        // 선택된 제품의 productCode를 기반으로 Product 엔티티를 찾습니다.
        Product product = productService.getProductByCode(productCode);

        // 찾은 Product 엔티티를 ProductionPlan에 설정합니다.
        if (product != null) {
            productionPlan.setProduct(product);
        }

        // ProductService를 통해 ProductionPlan을 저장합니다.
        productService.saveProductionPlan(productionPlan);

        return "redirect:/productionPlan/productionPlan";
    }
}

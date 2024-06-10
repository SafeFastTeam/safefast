package org.zerock.safefast.controller.production_plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.CoOpCompanyRepository;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.ProductRepository;
import org.zerock.safefast.service.production_plan.ProductService;
import org.zerock.safefast.service.procurement.ProductionPlanService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/productionPlan")
public class ProductController {

    private final ProductService productService;
    private final ItemRepository itemRepository;
    private final CoOpCompanyRepository coOpCompanyRepository;
    private final ProductionPlanService productionPlanService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, ItemRepository itemRepository,
                             CoOpCompanyRepository coOpCompanyRepository, ProductionPlanService productionPlanService,
                             ProductRepository productRepository) {
        this.productService = productService;
        this.itemRepository = itemRepository;
        this.coOpCompanyRepository = coOpCompanyRepository;
        this.productionPlanService = productionPlanService;
        this.productRepository = productRepository;
    }

    @GetMapping("/productionPlan")
    public String showProductionPlan(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<ProductionPlan> productionPlanPage = productService.findAll(PageRequest.of(page, size));
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("productionPlan", new ProductionPlan());
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("productionPlans", productionPlanPage.getContent());
        model.addAttribute("totalPages", productionPlanPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("coOpCompanies", coOpCompanyRepository.findAll());
        model.addAttribute("pageSize", size);

        return "production_plan/production_plan";
    }
    @PostMapping("/save")
    public String saveProductionPlan(@RequestParam("productCode") String productCode,
                                     @RequestParam("itemCode") String itemCode,
                                     @RequestParam("businessNumber") String businessNumber,
                                     @ModelAttribute ProductionPlan productionPlan) {
        System.out.println("Product Code: " + productCode);
        System.out.println("Item Code: " + itemCode);
        System.out.println("Business Number: " + businessNumber);

        // 선택된 제품의 productCode를 기반으로 Product 엔티티를 찾습니다.
        Product product = productService.getProductByCode(productCode);

        // 찾은 Product 엔티티를 ProductionPlan에 설정합니다.
        if (product != null) {
            productionPlan.setProduct(product);
        }

        // 선택된 itemCode와 businessNumber를 기반으로 Item과 CoOpCompany 엔티티를 찾습니다.
        Item item = itemRepository.findById(itemCode).orElseThrow(() -> new IllegalArgumentException("Invalid item code: " + itemCode));
        CoOpCompany coOpCompany = coOpCompanyRepository.findById(businessNumber).orElseThrow(() -> new IllegalArgumentException("Invalid business number: " + businessNumber));

        // ProductionPlan에 Item과 CoOpCompany를 설정합니다.
        productionPlan.setItem(item);
        productionPlan.setCoOpCompany(coOpCompany);

        // ProductionPlanItem 생성
        ProductionPlanItem planItem = new ProductionPlanItem();
        planItem.setItem(item);
        planItem.setCoOpCompany(coOpCompany);
        planItem.setProductionPlan(productionPlan);

        // ProductionPlan과 관련된 ProductionPlanItem을 함께 저장합니다.
        productService.saveProductionPlan(productionPlan, Collections.singletonList(planItem));

        return "redirect:/productionPlan/productionPlan";
    }
}

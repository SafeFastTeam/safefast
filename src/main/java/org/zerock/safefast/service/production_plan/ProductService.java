package org.zerock.safefast.service.production_plan;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.Product;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.entity.ProductionPlanItem;
import org.zerock.safefast.repository.*;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductionPlanRepository productionPlanRepository;
    private final ItemRepository itemRepository;
    private final CoOpCompanyRepository coOpCompanyRepository;
    private final ProductionPlanItemRepository productionPlanItemRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductionPlanRepository productionPlanRepository, ProductionPlanItemRepository productionPlanItemRepository, ItemRepository itemRepository, CoOpCompanyRepository coOpCompanyRepository) {
        this.productionPlanItemRepository = productionPlanItemRepository;
        this.itemRepository = itemRepository;
        this.coOpCompanyRepository = coOpCompanyRepository;
        this.productRepository = productRepository;
        this.productionPlanRepository = productionPlanRepository;
    }

    public void saveProductionPlan(ProductionPlan productionPlan, List<ProductionPlanItem> productionPlanItems) {
        generateProdPlanCode(productionPlan); // prodPlanCode 생성

        productionPlanRepository.save(productionPlan);
        for (ProductionPlanItem item : productionPlanItems) {
            item.setProductionPlan(productionPlan);
            productionPlanItemRepository.save(item);
        }
    }

    private void generateProdPlanCode(ProductionPlan productionPlan) {
        if (productionPlan.getProdPlanCode() == null) {
            String lastProdPlanCode = productionPlanRepository.findLastProdPlanCode();
            String newProdPlanCode = generateNewProdPlanCode(lastProdPlanCode);
            productionPlan.setProdPlanCode(newProdPlanCode);
        }
    }

    private String generateNewProdPlanCode(String lastProdPlanCode) {
        int lastNumber = Integer.parseInt(lastProdPlanCode.split("-")[1]);
        int newNumber = lastNumber + 1;
        return String.format("PROD-%03d", newNumber);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    public List<ProductionPlan> getAllProductionPlans() {
        return productionPlanRepository.findAll();
    }
}
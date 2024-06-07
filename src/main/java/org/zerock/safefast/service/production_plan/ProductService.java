package org.zerock.safefast.service.production_plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.Product;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.repository.ProductRepository;
import org.zerock.safefast.repository.ProductionPlanRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductionPlanRepository productionPlanRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductionPlanRepository productionPlanRepository) {
        this.productRepository = productRepository;
        this.productionPlanRepository = productionPlanRepository;
    }

    public void saveProductionPlan(ProductionPlan productionPlan) {
        generateProdPlanCode(productionPlan);

        Product product = productionPlan.getProduct();

        // 제품이 존재하지 않으면 저장
        if (product != null && productRepository.findByProductCode(product.getProductCode()) == null) {
            productRepository.save(product);
        }
        productionPlanRepository.save(productionPlan);
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
}
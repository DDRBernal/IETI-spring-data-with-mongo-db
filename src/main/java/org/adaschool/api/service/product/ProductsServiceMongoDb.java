package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;
import org.adaschool.api.repository.product.ProductMongoRepository;
import org.adaschool.api.repository.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceMongoDb implements ProductsService {

    private final ProductMongoRepository productMongoRepository;

    @Autowired
    public ProductsServiceMongoDb(ProductMongoRepository productMongoRepository) {
        this.productMongoRepository = productMongoRepository;
    }

    @Override
    public Product save(Product product) {
        try {
            productMongoRepository.insert(product);
            Optional<Product> product1 = productMongoRepository.findById(product.getId());
            return product1.orElse(product);
        } catch (DuplicateKeyException ex) {
            System.out.println("Product already added");
            return null;
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        return productMongoRepository.findById(id);
    }

    @Override
    public List<Product> all() {
        return productMongoRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        try{
            productMongoRepository.deleteById(id);
        }catch (IllegalArgumentException ex){

        }
    }

    @Override
    public Product update(Product product, String productId) {
        if (productMongoRepository.existsById(productId)) {
            return productMongoRepository.save(product);
        } else {
            System.out.println("The product doesn't exists");
            return null;
        }
    }
}

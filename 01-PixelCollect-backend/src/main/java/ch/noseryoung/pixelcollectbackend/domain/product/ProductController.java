package ch.noseryoung.pixelcollectbackend.domain.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired private ProductRepository productService;

    /*
    * getAllProducts (with filter system)
    * getProductById
    * createProduct
    * updateProduct
    * deleteProduct
    * buyProduct
    * sellProduct
    * getOwnedProducts (with filter system)
    */
}

//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) Rarity rarity
//            @RequestParam(required = false, defaultValue = "asc") String order,
//            @RequestParam(required = false) Integer minPrice,
//            @RequestParam(required = false) Integer maxPrice,
//            @RequestParam(required = false) Boolean available,
//            @RequestParam(required = false, defaultValue = "0") int page,
//            @RequestParam(required = false, defaultValue = "10") int size
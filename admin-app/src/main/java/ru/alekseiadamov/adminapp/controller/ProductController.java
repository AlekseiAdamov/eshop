package ru.alekseiadamov.adminapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.alekseiadamov.adminapp.service.BrandService;
import ru.alekseiadamov.adminapp.service.CategoryService;
import ru.alekseiadamov.adminapp.service.ProductService;
import ru.alekseiadamov.db.dto.ProductDTO;
import ru.alekseiadamov.db.dto.ProductListParamsDTO;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String PRODUCT_FORM_PAGE = "product_form";
    private static final String PRODUCT_LIST_PAGE = "product";
    private static final String REDIRECT_PAGE = "redirect:/product";
    private static final String PRODUCT_ATTRIBUTE = "product";
    private static final String CATEGORIES_ATTRIBUTE = "categories";
    private static final String BRANDS_ATTRIBUTE = "brands";
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String listPage(Model model, ProductListParamsDTO params) {
        logListPage(params);
        addListPageAttributes(model, params);
        return PRODUCT_LIST_PAGE;
    }

    private void logListPage(ProductListParamsDTO params) {
        final String logMessage = String.format("Product list page requested with parameters: " +
                        "name = %s, " +
                        "minPrice = %s, " +
                        "maxPrice = %s, " +
                        "category = %s," +
                        "brand = %s",
                params.getName(),
                params.getMinPrice(),
                params.getMaxPrice(),
                params.getCategory(),
                params.getBrand());
        log.info(logMessage);
    }

    private void addListPageAttributes(Model model, ProductListParamsDTO params) {
        final Page<ProductDTO> products = productService.findWithFilter(params);
        final String reverseSortOrder = "asc".equals(params.getSortOrder()) ? "desc" : "asc";
        model.addAttribute("products", products);
        model.addAttribute("reverseSortOrder", reverseSortOrder);
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        log.info("New product page requested");
        model.addAttribute(PRODUCT_ATTRIBUTE, new ProductDTO());
        model.addAttribute(CATEGORIES_ATTRIBUTE, categoryService.findAll());
        model.addAttribute(BRANDS_ATTRIBUTE, brandService.findAll());
        return PRODUCT_FORM_PAGE;
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        log.info("Edit product page requested");

        Optional<ProductDTO> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute(PRODUCT_ATTRIBUTE, productService.getById(id));
            model.addAttribute(CATEGORIES_ATTRIBUTE, categoryService.findAll());
            model.addAttribute(BRANDS_ATTRIBUTE, brandService.findAll());
        } else {
            throw new NotFoundException(String.format("Product with id %d not found", id));
        }
        return PRODUCT_FORM_PAGE;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        log.info("Deleting product with id {}", id);
        productService.deleteById(id);
        return REDIRECT_PAGE;
    }

    @GetMapping("/error")
    public String error(Model model) {
        log.info("Non existing page requested");
        model.addAttribute("message", "404 Page not found");
        return "not_found";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("product") ProductDTO product, BindingResult result, Model model) {
        checkIfCategorySpecified(product, result);
        checkIfBrandSpecified(product, result);
        if (result.hasErrors()) {
            model.addAttribute(CATEGORIES_ATTRIBUTE, categoryService.findAll());
            model.addAttribute(BRANDS_ATTRIBUTE, brandService.findAll());
            return PRODUCT_FORM_PAGE;
        }
        logUpdate(product);
        productService.save(product);
        return REDIRECT_PAGE;
    }

    private void logUpdate(ProductDTO product) {
        Long productId = product.getId();
        if (productId != null && productService.findById(productId).isPresent()) {
            log.info("Updating product");
        } else {
            log.info("Saving new product");
        }
    }

    private void checkIfCategorySpecified(ProductDTO product, BindingResult result) {
        if (product.getCategory() == null) {
            result.rejectValue("category", "", "Category must be specified!");
        }
    }

    private void checkIfBrandSpecified(ProductDTO product, BindingResult result) {
        if (product.getBrand() == null) {
            result.rejectValue("brand", "", "Brand must be specified!");
        }
    }

    @ExceptionHandler()
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}

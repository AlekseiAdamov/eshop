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
import ru.alekseiadamov.adminapp.service.CategoryService;
import ru.alekseiadamov.db.dto.CategoryDTO;
import ru.alekseiadamov.db.dto.CategoryParamsDTO;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final String CATEGORY_FORM_PAGE = "category_form";
    private static final String CATEGORY_LIST_PAGE = "category";
    private static final String CATEGORY_ATTRIBUTE = "category";
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String listPage(Model model, CategoryParamsDTO params) {

        final String logMessage = String.format("Category list page requested with parameters: productName = %s",
                params.getName());
        log.info(logMessage);

        final Page<CategoryDTO> categories = service.findWithFilter(params);

        model.addAttribute("reverseSortOrder", "asc".equals(params.getSortOrder()) ? "desc" : "asc");
        model.addAttribute("categories", categories);
        return CATEGORY_LIST_PAGE;
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        log.info("New category page requested");

        model.addAttribute(CATEGORY_ATTRIBUTE, new CategoryDTO());
        return CATEGORY_FORM_PAGE;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        log.info("Edit category page requested");

        Optional<CategoryDTO> product = service.findById(id);
        if (product.isPresent()) {
            model.addAttribute(CATEGORY_ATTRIBUTE, service.getById(id));
        } else {
            throw new NotFoundException(String.format("Category with id %d not found", id));
        }
        return CATEGORY_FORM_PAGE;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        log.info("Deleting category with id {}", id);
        service.deleteById(id);
        return "redirect:/category";
    }

    @GetMapping("/error")
    public String error(Model model) {
        log.info("Non existing page requested");

        model.addAttribute("message", "404 Page not found");
        return "not_found";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("category") CategoryDTO category, BindingResult result) {
        if (result.hasErrors()) {
            return CATEGORY_FORM_PAGE;
        }
        if (category.getId() != null && service.findById(category.getId()).isPresent()) {
            log.info("Updating category");
        } else {
            log.info("Saving new category");
        }
        service.save(category);
        return "redirect:/category";
    }

    @ExceptionHandler()
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}

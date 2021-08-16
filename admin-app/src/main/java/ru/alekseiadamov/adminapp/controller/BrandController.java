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
import ru.alekseiadamov.db.dto.BrandDTO;
import ru.alekseiadamov.db.dto.BrandParamsDTO;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/brand")
public class BrandController {

    private static final String BRAND_FORM_PAGE = "brand_form";
    private static final String BRAND_LIST_PAGE = "brand";
    private static final String BRAND_ATTRIBUTE = "brand";
    private final BrandService service;

    @Autowired
    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping
    public String listPage(Model model, BrandParamsDTO params) {

        final String logMessage = String.format("Brand list page requested with parameters: productName = %s",
                params.getName());
        log.info(logMessage);

        final Page<BrandDTO> brands = service.findWithFilter(params);

        model.addAttribute("reverseSortOrder", "asc".equals(params.getSortOrder()) ? "desc" : "asc");
        model.addAttribute("brands", brands);
        return BRAND_LIST_PAGE;
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        log.info("New brand page requested");

        model.addAttribute(BRAND_ATTRIBUTE, new BrandDTO());
        return BRAND_FORM_PAGE;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        log.info("Edit brand page requested");

        Optional<BrandDTO> brand = service.findById(id);
        if (brand.isPresent()) {
            model.addAttribute(BRAND_ATTRIBUTE, service.getById(id));
        } else {
            throw new NotFoundException(String.format("Brand with id %d not found", id));
        }
        return BRAND_FORM_PAGE;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        log.info("Deleting brand with id {}", id);
        service.deleteById(id);
        return "redirect:/brand";
    }

    @GetMapping("/error")
    public String error(Model model) {
        log.info("Non existing page requested");

        model.addAttribute("message", "404 Page not found");
        return "not_found";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("brand") BrandDTO brand, BindingResult result) {
        if (result.hasErrors()) {
            return BRAND_FORM_PAGE;
        }
        if (brand.getId() != null && service.findById(brand.getId()).isPresent()) {
            log.info("Updating brand");
        } else {
            log.info("Saving new brand");
        }
        service.save(brand);
        return "redirect:/brand";
    }

    @ExceptionHandler()
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}

package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.dto.CatalogDTO;
import com.ecommerce.catalog.exception.CatalogException;
import com.ecommerce.catalog.model.Catalog;
import com.ecommerce.catalog.model.Error;
import com.ecommerce.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping
    public ResponseEntity<?> addProductToCatalog(@RequestBody @Valid CatalogDTO catalogDTO, Errors errors) {
        if(errors.hasErrors()) {
            List<Error> errorList = new Error().getErrorDetails(errors.getAllErrors());
            return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
        }

        try {
            catalogService.addProductToCatalog(catalogDTO);
            return new ResponseEntity<>("Product has been added to catalog.", HttpStatus.OK);
        } catch(CatalogException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getProductListWithUsername(@PathVariable String username) {
        try {
            List<Catalog> catalogs = catalogService.getProductListWithUsername(username);
            return new ResponseEntity<>(catalogs, HttpStatus.OK);
        } catch (CatalogException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<?> getUserListWithProductName(@PathVariable String productName) {
        try {
            Map<String, Object> result = catalogService.getUserListWithProductName(productName);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (CatalogException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}

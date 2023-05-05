package com.uow.FYP_23_S1_11.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.service.ArticleService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/article", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/getAllEduMaterial")
    public ResponseEntity<Map<?, ?>> getAllEduMaterial(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(articleService.getAllEduMaterial(PageRequest.of(page, size)));
    }

    @GetMapping("/getEduMaterialById")
    public ResponseEntity<Map<?, ?>> getEduMaterialById(@NotNull @RequestParam Integer id) {
        return ResponseEntity.ok(articleService.getEduMaterialById(id));
    }

}

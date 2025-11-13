package com.suivilbcft.suivibackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.suivilbcft.suivibackend.model.TypeReponse;
import com.suivilbcft.suivibackend.service.TypeReponseService;

@RestController
@RequestMapping("/api/type-reponse")
public class TypeReponseController {

    @Autowired
    private TypeReponseService service;

    @GetMapping
    public List<TypeReponse> getAll() {
        return service.getAllTypeReponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeReponse> getById(@PathVariable String id) {
        return service.getTypeReponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TypeReponse create(@RequestBody TypeReponse typeReponse) {
        return service.createTypeReponse(typeReponse.getType());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeReponse> update(@PathVariable String id, @RequestBody TypeReponse typeReponse) {
        return service.getTypeReponseById(id)
                .map(existing -> {
                    return ResponseEntity.ok(service.updateTypeReponse(id, typeReponse.getType()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (service.getTypeReponseById(id).isPresent()) {
            service.deleteTypeReponse(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
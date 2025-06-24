package alta.imobiliaria.controller;

import alta.imobiliaria.domain.Property;
import alta.imobiliaria.repository.PropertyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyRepository repository;

    public PropertyController(PropertyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Property> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Property> create(@RequestBody Property property) {
        Property saved = repository.save(property);
        return ResponseEntity.created(URI.create("/properties/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> update(@PathVariable Long id, @RequestBody Property property) {
        return repository.findById(id)
                .map(existing -> {
                    property.setId(existing.getId());
                    Property updated = repository.save(property);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

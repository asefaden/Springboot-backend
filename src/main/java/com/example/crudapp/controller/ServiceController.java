package com.example.crudapp.controller;

import com.example.crudapp.model.CleaningService;
import com.example.crudapp.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull; // Crucial for resolving the type safety warning
import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public List<CleaningService> getAllServices() {
        return serviceRepository.findAll();
    }

    @PostMapping
    public CleaningService createService(@NonNull @RequestBody CleaningService service) {
        // Adding @NonNull directly addresses the Java compiler warning
        return serviceRepository.save(service);
    }

    // === አስተዳዳሪው እንዲያስተካክል (Edit) ===
    // === አስተዳዳሪው እንዲያስተካክል (Edit) ===
    @PutMapping("/{id}")
    public ResponseEntity<CleaningService> updateService(
            @org.springframework.lang.NonNull @PathVariable Long id, // እዚህ ጋ @NonNull ጨምረናል
            @org.springframework.lang.NonNull @RequestBody CleaningService details) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setServiceName(details.getServiceName());
                    service.setPrice(details.getPrice());
                    service.setDuration(details.getDuration());
                    service.setAvailable(details.isAvailable());
                    CleaningService updatedService = serviceRepository.save(service);
                    return ResponseEntity.ok(updatedService);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // === አስተዳዳሪው እንዲያጠፋ (Delete) ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (id != null && serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

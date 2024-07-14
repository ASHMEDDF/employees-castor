package com.nn.castor.controller;


import com.nn.castor.domain.Position;
import com.nn.castor.exception.PositionNotFoundException;
import com.nn.castor.service.PositionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/position")
@AllArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PositionNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(@Valid @RequestBody Position position) {
        Position createdPosition = positionService.createPosition(position);
        return ResponseEntity.ok(createdPosition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id,
                                                   @Valid @RequestBody Position positionDetails) {
        Position updatedPosition = positionService.updatePosition(id, positionDetails);
        return ResponseEntity.ok(updatedPosition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok().build();
    }
}

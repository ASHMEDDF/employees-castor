package com.nn.castor.service;

import com.nn.castor.domain.Position;
import com.nn.castor.exception.PositionAlreadyExistsException;
import com.nn.castor.exception.PositionNotFoundException;
import com.nn.castor.repository.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PositionService {

    private final PositionRepository positionRepository;

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }

    public Position createPosition(Position position) {
        if (positionRepository.existsByName(position.getName())) {
            throw new PositionAlreadyExistsException(position.getName());
        }
        return positionRepository.save(position);
    }

    public Position updatePosition(Long id, Position positionDetails) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));
        if (!position.getName().equals(positionDetails.getName()) && positionRepository.existsByName(positionDetails.getName())) {
            throw new PositionAlreadyExistsException(positionDetails.getName());
        }
        position.setName(positionDetails.getName());
        return positionRepository.save(position);
    }

    public void deletePosition(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));
        positionRepository.delete(position);
    }
}

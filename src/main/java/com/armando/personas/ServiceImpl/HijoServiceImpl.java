package com.armando.personas.ServiceImpl;

import com.armando.personas.Models.Hijo;
import com.armando.personas.Repository.HijoRepository;
import com.armando.personas.Service.HijoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HijoServiceImpl implements HijoService {

    @Autowired
    private HijoRepository hijoRepository;

    @Override
    public Optional<Hijo> findById(Long Id) {
        return hijoRepository.findById(Id);
    }

    @Override
    public Hijo saveHijo(Hijo hijo) {
        return hijoRepository.save(hijo);
    }

    @Override
    public void deleteHijo(Long Id) {
        hijoRepository.deleteById(Id);
    }
}

package com.armando.personas.Service;

import com.armando.personas.Models.Hijo;

import java.util.Optional;

public interface HijoService {

    Optional<Hijo> findById(Long Id);

    Hijo saveHijo (Hijo hijo);

    void deleteHijo (Long Id);
}

package com.seatecnologia.crudclientes.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class PageableService {
    public PageRequest getPageable(Integer page, Boolean asc, String sortBy){
        int pageNumber = (page != null) ? page : 0; // Página padrão é 0
        Sort.Direction direction = (asc != null && asc) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String sortField = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id"; // Ordenação por "id" por padrão

        return PageRequest.of(pageNumber, 10, Sort.by(direction, sortField));
    }
}

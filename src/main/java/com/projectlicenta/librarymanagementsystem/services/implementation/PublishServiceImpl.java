package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.model.entities.Editura;
import com.projectlicenta.librarymanagementsystem.repository.PublishRepository;
import com.projectlicenta.librarymanagementsystem.services.PublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublishServiceImpl implements PublishService {

    private final PublishRepository publishRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Editura> getPublishList(){
        return publishRepository.findAll();
    }
}

package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.responses.GenreResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.PublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.projectlicenta.librarymanagementsystem.services.mapper.PublishMapper.fromPublishList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publish")
public class PublishController {

    private final PublishService publishService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllPublish() {
        return fromPublishList(publishService.getPublishList());
    }
}

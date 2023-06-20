package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;
import com.projectlicenta.librarymanagementsystem.model.entities.Editura;
import com.projectlicenta.librarymanagementsystem.model.responses.AddressResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class PublishMapper {

    public static List<String> fromPublishList(List<Editura> publishList) {
        List<String> publishDtoList = new ArrayList<>();
        publishList.forEach(
                publish -> publishDtoList.add(publish.getPublishName()));
        return publishDtoList;
    }
}

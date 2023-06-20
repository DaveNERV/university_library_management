package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.JokeApiException;
import com.projectlicenta.librarymanagementsystem.model.responses.JokeDTO;
import com.projectlicenta.librarymanagementsystem.services.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {

    private final RestTemplate restTemplate;

    @Value("${jokes.api.url}")
    private String url;

    @Override
    public JokeDTO getJoke() {
        try{
            JokeDTO joke = restTemplate.getForObject(url, JokeDTO.class);
            if (Objects.nonNull(joke) && ObjectUtils.isNotEmpty(joke.getJoke())){
                return joke;
            }
        } catch (ResourceAccessException e){
            throw  new JokeApiException("Joke api is not available.");
        }
        throw new JokeApiException("Joke api returned incorrectly formatted body.");
    }
}

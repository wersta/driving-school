package com.example.demo.service.mapper;

import com.example.demo.service.dto.RatingDto;
import com.example.demo.model.Rating;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingDtoMapper {

    public RatingDto toDto(Rating rating)
    {
        return new RatingDto(rating.getId(), rating.getDate(),rating.getDescription(), rating.getStarsNumber(), rating.getInstructor().getId());
    }

    public List<RatingDto> toDtoRatingList(List<Rating>ratingsList )
    {
        List<RatingDto> ratingsListDto = new ArrayList<>();
        ratingsList.forEach(rating -> ratingsListDto.add(this.toDto(rating)));
        return ratingsListDto;
    }

}

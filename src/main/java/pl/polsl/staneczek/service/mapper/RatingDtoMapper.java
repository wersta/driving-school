package pl.polsl.staneczek.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.Instructor;
import pl.polsl.staneczek.model.Rating;
import pl.polsl.staneczek.service.InstructorService;
import pl.polsl.staneczek.service.dto.RatingDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingDtoMapper {
    @Autowired
    private InstructorService instructorService;

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
    public Rating toEntity(RatingDto ratingDto)
    {
        Rating rating=new Rating();
        rating.setDescription(ratingDto.getDescription());
        rating.setId(ratingDto.getId());
        rating.setDate(ratingDto.getDate());
        rating.setStarsNumber(ratingDto.getStarsNumber());
        Instructor instructor=instructorService.findById(ratingDto.getInstructorId());
        rating.setInstructor(instructor);

        return rating;
    }
}

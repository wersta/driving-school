package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.Instructor;
import pl.polsl.staneczek.model.Rating;
import pl.polsl.staneczek.repository.RatingRepository;
import pl.polsl.staneczek.service.dto.RatingDto;
import pl.polsl.staneczek.service.mapper.RatingDtoMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private RatingDtoMapper ratingMapper;


    public Rating findById(Integer ratingId) {
        return ratingRepository.findById(ratingId).isPresent() ? ratingRepository.findById(ratingId).get() : null;
    }

    public Rating createRating(RatingDto ratingDto)
    {
        Rating rating=new Rating();
        rating.setDescription(ratingDto.getDescription());
        rating.setId(ratingDto.getId());
        rating.setDate(ratingDto.getDate());
        rating.setStarsNumber(ratingDto.getStarsNumber());
        Instructor instructor = instructorService.findById(ratingDto.getInstructorId());
        rating.setInstructor(instructor);
        //Rating rating = ratingMapper.toEntity(ratingDto);
        ratingRepository.save(rating);

        return rating;
    }

    public boolean deleteRating(Integer ratingId) {

        Rating rating=findById(ratingId);
        if(rating != null) {
            ratingRepository.delete(rating);
            return true;
        }
        return false;
    }
    public List<RatingDto> getRatingDtoList() {
        List<Rating> ratingList = ratingRepository.findAll();

        if(ratingList != null) {
            List<RatingDto> ratingDtoList = new ArrayList<>();

            for (Rating e : ratingList) {
                ratingDtoList.add(ratingMapper.toDto(e));
            }
            return ratingDtoList;
        }return null;

    }

}

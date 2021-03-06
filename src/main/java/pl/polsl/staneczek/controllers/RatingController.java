package pl.polsl.staneczek.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.staneczek.model.Rating;
import pl.polsl.staneczek.service.RatingService;
import pl.polsl.staneczek.service.dto.RatingDto;

import java.util.List;

@RestController
@RequestMapping("/rating")
@ResponseBody
@CrossOrigin("http://localhost:8080")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/all")
    public List<RatingDto> getRatings() {
        return ratingService.getRatingDtoList();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRating(@RequestBody RatingDto ratingDto) {
        Rating newRating=ratingService.createRating(ratingDto);
        if (newRating != null) {
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRating(@PathVariable Integer id) {
        if (ratingService.deleteRating(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}

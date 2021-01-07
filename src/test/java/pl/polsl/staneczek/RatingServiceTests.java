package pl.polsl.staneczek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.polsl.staneczek.model.Rating;
import pl.polsl.staneczek.repository.RatingRepository;
import pl.polsl.staneczek.service.RatingService;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RatingServiceTests {
    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private RatingService ratingService;


    @Test
    void shouldfindRatingSuccessfully(){
        Rating rating = new Rating(1,LocalDate.now(),"Bardzo dobrze poprowadzone zajęcia. Brakowało tylko czasu.",3,null);

        Mockito.when(ratingRepository.findById(1)).thenReturn(java.util.Optional.of(rating));
        Rating retrivedRating = ratingService.findById(1);
        assertEquals(rating, retrivedRating);
    }


}

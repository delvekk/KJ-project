package com.dawid.services;

import com.dawid.commands.ReviewCommand;
import com.dawid.converters.ReviewCommandToReview;
import com.dawid.converters.ReviewToReviewCommand;
import com.dawid.domain.Product;
import com.dawid.domain.Review;
import com.dawid.repositories.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    ReviewServiceImpl reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    ReviewToReviewCommand reviewToReviewCommand;

    @Mock
    ReviewCommandToReview reviewCommandToReview;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reviewService = new ReviewServiceImpl(reviewRepository,reviewToReviewCommand, reviewCommandToReview);



    }

    @Test
    public void getReviewById() {
        Review review = new Review();
        review.setId(1L);

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));


        Review found = reviewService.getReviewById(anyLong());

        assertEquals(review.getId(), found.getId());
        verify(reviewRepository, times(1)).findById(anyLong());


    }

    @Test
    public void getReviewCommandById() {
        Review review = new Review();
        review.setId(1L);


        Optional<Review> optionalReview = Optional.of(review);
        when(reviewRepository.findById(any())).thenReturn(optionalReview);



        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setId(1L);

        when(reviewToReviewCommand.convert(any())).thenReturn(reviewCommand);

        ReviewCommand returned = reviewService.getReviewCommandById(anyLong());

        assertEquals(review.getId(), returned.getId());
        verify(reviewToReviewCommand, times(1)).convert(any());

;

    }

    @Test
    public void getReviewsByProductId() {
        Review review = new Review();
        review.setDate(new Date());
        Set<Review> reviews = new HashSet<>();
        reviews.add(review);

        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setDate(review.getDate());


        when(reviewToReviewCommand.convert(any())).thenReturn(reviewCommand);
        when(reviewRepository.findByProduct(any())).thenReturn(reviews);


        Set<ReviewCommand> returned = reviewService.getReviewsByProductId(any());


        assertEquals(1, returned.size());
        verify(reviewToReviewCommand, times(1)).convert(any());
        verify(reviewRepository, times(1)).findByProduct(any());

    }

    @Test
    public void saveReview() {
        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setId(1L);

        Product product = new Product();
        product.setId(1L);


        Review review = new Review();
        review.setId(1L);

        when(reviewCommandToReview.convert(any())).thenReturn(review);

        reviewService.saveReview(reviewCommand, product);

        verify(reviewRepository, times(1)).save(any());
        verify(reviewCommandToReview, times(1)).convert(any());

    }

    @Test
    public void deleteReview() {
        Long l = 1L;

        reviewService.deleteReview(l);

        verify(reviewRepository, times(1)).deleteById(anyLong());

    }
}
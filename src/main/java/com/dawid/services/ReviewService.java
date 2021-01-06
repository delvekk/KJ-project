package com.dawid.services;

import com.dawid.commands.ReviewCommand;
import com.dawid.domain.Product;
import com.dawid.domain.Review;

import java.util.Set;

public interface ReviewService {

    Review getReviewById(Long l);
    ReviewCommand getReviewCommandById(Long l);
    Set<ReviewCommand> getReviewsByProductId(Long l);
    void saveReview(ReviewCommand reviewCommand, Product product);
    void deleteReview(Long l);


}

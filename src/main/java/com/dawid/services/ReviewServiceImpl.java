package com.dawid.services;

import com.dawid.commands.ReviewCommand;
import com.dawid.converters.ReviewCommandToReview;
import com.dawid.converters.ReviewToReviewCommand;
import com.dawid.domain.Product;
import com.dawid.domain.Review;
import com.dawid.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewToReviewCommand reviewToReviewCommand;
    private final ReviewCommandToReview reviewCommandToReview;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewToReviewCommand reviewToReviewCommand, ReviewCommandToReview reviewCommandToReview) {
        this.reviewRepository = reviewRepository;
        this.reviewToReviewCommand = reviewToReviewCommand;
        this.reviewCommandToReview = reviewCommandToReview;
    }

    @Override
    public Review getReviewById(Long l) {
        Review review = reviewRepository.findById(l).get();
        return review;
    }

    @Override
    public ReviewCommand getReviewCommandById(Long l) {
        Review review = getReviewById(l);
        return reviewToReviewCommand.convert(review);
    }

    @Override
    public Set<ReviewCommand> getReviewsByProductId(Long l) {
        Product product = new Product();
        product.setId(l);
        Set<ReviewCommand> reviewCommands = new TreeSet<>(Comparator.comparing(ReviewCommand::getDate));
        reviewRepository.findByProduct(product).forEach(review -> reviewCommands.add(reviewToReviewCommand.convert(review)));

        return reviewCommands;

    }

    @Override
    public void saveReview(ReviewCommand reviewCommand, Product product) {
        Review review = reviewCommandToReview.convert(reviewCommand);
        review.setProduct(product);
        review.setDate(new Date());
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long l) {
        reviewRepository.deleteById(l);
    }
}

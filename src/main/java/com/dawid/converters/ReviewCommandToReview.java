package com.dawid.converters;

import com.dawid.commands.ReviewCommand;
import com.dawid.domain.Review;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ReviewCommandToReview implements Converter<ReviewCommand, Review> {


    private final UserCommandToUser userCommandToUser;

    public ReviewCommandToReview(UserCommandToUser userCommandToUser) {

        this.userCommandToUser = userCommandToUser;
    }

    @Nullable
    @Synchronized
    @Override
    public Review convert(ReviewCommand source) {
        if (source == null){
            return null;
        }

        Review review = new Review();
        review.setId(source.getId());
        review.setProductId(source.getProductId());
        review.setReview(source.getReview());
        review.setUser(userCommandToUser.convert(source.getUser()));
        review.setDate(source.getDate());

        return review;

    }
}

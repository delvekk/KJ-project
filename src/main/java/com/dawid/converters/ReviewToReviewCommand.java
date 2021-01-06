package com.dawid.converters;

import com.dawid.commands.ReviewCommand;
import com.dawid.domain.Review;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ReviewToReviewCommand implements Converter<Review, ReviewCommand> {


    private final UserToUserCommand userCommandToUser;

    public ReviewToReviewCommand(UserToUserCommand userCommandToUser) {

        this.userCommandToUser = userCommandToUser;
    }

    @Nullable
    @Synchronized
    @Override
    public ReviewCommand convert(Review source) {
        if (source == null){
            return null;
        }

        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setId(source.getId());
        reviewCommand.setProductId(source.getProductId());
        reviewCommand.setReview(source.getReview());
        reviewCommand.setUser(userCommandToUser.convert(source.getUser()));
        reviewCommand.setDate(source.getDate());


        return reviewCommand;

    }
}

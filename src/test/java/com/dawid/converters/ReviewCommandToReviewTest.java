package com.dawid.converters;

import com.dawid.commands.ReviewCommand;
import com.dawid.commands.UserCommand;
import com.dawid.domain.Review;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReviewCommandToReviewTest {
    public static final Long ID = 1L;
    public static final Long PRODUCT_ID = 1L;
    public static final String REVIEW = "review";
    public static final Long USER_ID = 1L;
    public static final Date DATE = new Date();


    ReviewCommandToReview converter;

    @Before
    public void setUp() throws Exception {
        converter = new ReviewCommandToReview(new UserCommandToUser(new RoleCommandToRole()));
    }

    @Test
    public void convert() {
        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setId(ID);
        reviewCommand.setProductId(PRODUCT_ID);
        reviewCommand.setReview(REVIEW);
        reviewCommand.setDate(DATE);

        UserCommand userCommand = new UserCommand();
        userCommand.setId(USER_ID);

        reviewCommand.setUser(userCommand);

        Review review = converter.convert(reviewCommand);

        assertEquals(ID, review.getId());
        assertEquals(PRODUCT_ID, review.getProductId());
        assertEquals(REVIEW,  review.getReview());
        assertEquals(DATE, review.getDate());
        assertEquals(USER_ID, review.getUser().getId());
    }
}
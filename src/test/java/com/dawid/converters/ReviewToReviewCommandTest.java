package com.dawid.converters;

import com.dawid.commands.ReviewCommand;
import com.dawid.domain.Review;
import com.dawid.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReviewToReviewCommandTest {

    public static final Long ID = 1L;
    public static final Long PRODUCT_ID = 1L;
    public static final String REVIEW = "review";
    public static final Long USER_ID = 1L;
    public static final Date DATE = new Date();


    ReviewToReviewCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new ReviewToReviewCommand(new UserToUserCommand(new RoleToRoleCommand()));
    }

    @Test
    public void convert() {
        Review review = new Review();
        review.setId(ID);
        review.setProductId(PRODUCT_ID);
        review.setReview(REVIEW);
        review.setDate(DATE);

        User user = new User();
        user.setId(USER_ID);

        review.setUser(user);

        ReviewCommand reviewCommand = converter.convert(review);

        assertEquals(ID, reviewCommand.getId());
        assertEquals(PRODUCT_ID, reviewCommand.getProductId());
        assertEquals(REVIEW,  reviewCommand.getReview());
        assertEquals(DATE, reviewCommand.getDate());
        assertEquals(USER_ID, reviewCommand.getUser().getId());
    }

}
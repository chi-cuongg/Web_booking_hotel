package org.codewithcuong.hamora.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.Review;
import org.codewithcuong.hamora.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getTop5PublicPositiveReviews() {
        return reviewRepository.getTop5PublicPositiveReviews();
    }
}

package com.example.finalfront.controller;

import com.example.finalfront.models.Review;
import com.example.finalfront.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String getReviews(Model model) {
        model.addAttribute("reviews", reviewService.getReviews());
        return "reviews";
    }

    @GetMapping("/{id}")
    public String getReview(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReview(id));
        return "reviews/details";
    }

    @GetMapping("/{id}/update")
    public String updateReviewForm(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReview(id));
        return "reviews/update";
    }

    @GetMapping("/create")
    public String createReviewForm() {
        return "reviews/create";
    }

    @PostMapping
    public String createReview(Review review) {
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    @PostMapping("/{id}/update")
    public String updateReview(@PathVariable Long id, Review review) {
        reviewService.updateReview(id, review);
        return "redirect:/reviews";
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}

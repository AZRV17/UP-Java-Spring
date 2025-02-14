package com.example.finalfront.controller;

import com.example.finalfront.models.Publisher;
import com.example.finalfront.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherService.getPublishers());
        return "publishers/list";
    }

    @GetMapping("/{id}")
    public String getPublisher(@PathVariable Long id, Model model) {
        model.addAttribute("publisher", publisherService.getPublisher(id));
        return "publishers/details";
    }

    @GetMapping("/{id}/update")
    public String updatePublisherForm(Model model, @PathVariable Long id) {
        model.addAttribute("publisher", publisherService.getPublisher(id));
        return "publishers/update";
    }

    @GetMapping("/create")
    public String createPublisherForm(Model model) {
        return "publishers/create";
    }

    @PostMapping
    public String createPublisher(Publisher publisher) {
        publisherService.createPublisher(publisher);
        return "redirect:/publishers";
    }

    @PostMapping("/{id}/update")
    public String updatePublisher(@PathVariable Long id, Publisher publisher) {
        publisherService.updatePublisher(id, publisher);
        return "redirect:/publishers";
    }

    @PostMapping("/{id}/delete")
    public String deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return "redirect:/publishers";
    }
}

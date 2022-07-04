package com.amr.project.converter;

import com.amr.project.model.dto.FeedbackDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Feedback;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.FeedbackService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class FeedbackMapper {

    @Autowired
    FeedbackService feedbackService;

    @Mappings({
        @Mapping(target = "username", expression = "java(getUsernameByFeedback(feedbackDto))"),
        @Mapping(target = "shop", expression = "java(getShopByFeedback(feedbackDto))"),
        @Mapping(target = "user", expression = "java(getUserByFeedback(feedbackDto))")
    })
    public abstract Feedback toModel (FeedbackDto feedbackDto);

    @Mappings({
        @Mapping(target = "userId", expression = "java(feedback.getUser().getId())")
    })
    public abstract FeedbackDto toDto (Feedback feedback);

    protected String getUsernameByFeedback (FeedbackDto feedbackDto) {
        if (feedbackDto.getId() != null) {
            return feedbackService.findById(feedbackDto.getId()).getUsername();
        }
        return null;
    }

    protected Shop getShopByFeedback (FeedbackDto feedbackDto) {
        if (feedbackDto.getId() != null) {
            return feedbackService.findById(feedbackDto.getId()).getShop();
        }
        return null;
    }

    protected User getUserByFeedback (FeedbackDto feedbackDto) {
        if (feedbackDto.getId() != null) {
            return feedbackService.findById(feedbackDto.getId()).getUser();
        }
        return null;
    }

}

package com.shop.service;

import com.shop.dto.RatingDto;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.OrderItem;
import com.shop.entity.Rating;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    public Long addRating(RatingDto ratingDto, String email) {
        Member member = memberRepository.findByEmail(email);
        Rating findRating = ratingRepository.findByMemberIdAndOrderItemId(member.getId(), ratingDto.getOrderItemId());
        if (findRating == null) {
            OrderItem orderItem = orderItemRepository.findById(ratingDto.getOrderItemId())
                    .orElseThrow(EntityNotFoundException::new);
            Rating newRating = Rating.createRating(member, orderItem, ratingDto.getGrade());
            ratingRepository.save(newRating);
            return newRating.getId();
        } else {
            findRating.updateRating(ratingDto);
            return findRating.getId();
        }
    }

    public void deleteRating(Long ratingId) {
//        Rating rating = ratingRepository.findById(ratingId);


    }
}
package com.health.care.Repository;

import com.health.care.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.ListResourceBundle;

public interface ReviewRepository extends JpaRepository<Review ,String > {

    List<Review> findTop10ByOrderByDateDesc();

    List<Review> findByRatingOrderByDateDesc(Integer rating);

    List<Review> findByServiceOrderByDateDesc(String service);

    List<Review> findByVerifiedTrueOrderByDateDesc();

    Page<Review> findAllByOrderByDateDesc(Pageable pageable);

}

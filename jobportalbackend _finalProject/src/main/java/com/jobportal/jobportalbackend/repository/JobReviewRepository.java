package com.jobportal.jobportalbackend.repository;

import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobReviewRepository extends JpaRepository<JobReview, Long> {
    Page<JobReview> findByJob(Job job, Pageable pageable);


    @Query("SELECT jr FROM JobReview jr WHERE " +
            "jr.job = :job AND " +
            "(:minRating IS NULL OR jr.rating >= :minRating)")
    Page<JobReview> findByJobAndMinRating(
            @Param("job") Job job,
            @Param("minRating") Integer minRating,
            Pageable pageable
    );

}

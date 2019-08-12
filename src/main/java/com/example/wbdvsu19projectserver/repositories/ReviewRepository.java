package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.Review;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer> {


  //should be review.user.id. Not review.user_id.
  @Query("SELECT review FROM Review review WHERE review.user.id = :uid AND review.product.id = :pid")
  public Review getReviewsByUserForProduct(@Param("uid") Integer uid, @Param("pid") Integer pid);



}

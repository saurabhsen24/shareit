package com.shareit.repository;

import com.shareit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Boolean existsByUserNameOrEmail(String userName,String email);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET works_at=:worksAt, college=:college, home_town=:homeTown, current_city=:currentCity ," +
            "country_name=:countryName, gender=:gender WHERE user_name=:userName", nativeQuery = true)
    void updateUserDetails(@Param("worksAt") String worksAt, @Param("college") String college,
                           @Param("homeTown") String homeTown, @Param("currentCity") String currentCity,
                           @Param("countryName") String countryName, @Param("gender") String gender,
                           @Param("userName") String userName);

}

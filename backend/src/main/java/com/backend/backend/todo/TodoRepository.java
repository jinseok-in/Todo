package com.backend.backend.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Todo t SET t.isChecked = :isChecked WHERE t.todoId = :id")
    void updateIsChecked(@Param("id") Long id, @Param("isChecked") Boolean isChecked);

    List<Todo> findAllByOrderByDeadlineAsc();
}


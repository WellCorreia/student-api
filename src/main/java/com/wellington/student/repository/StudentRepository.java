package com.wellington.student.repository;

import com.wellington.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Long> {

    Optional<Student> findByCPFOrRegistry(String cpf, String registry);
}

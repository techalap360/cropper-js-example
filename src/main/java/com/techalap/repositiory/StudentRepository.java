package com.techalap.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techalap.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}

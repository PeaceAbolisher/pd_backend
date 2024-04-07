package api.repository;

import api.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEntityRepository extends JpaRepository<StudentEntity, Long> {
    // custom query methods
}

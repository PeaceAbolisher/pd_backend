package api.repository;

import api.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    // custom query methods
    @Query("SELECT p FROM Professor p " +
            "LEFT JOIN FETCH p.proposals " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(p.proposals) ASC")
    List<Professor> findAllOrderByProposalsSizeAsc();
}

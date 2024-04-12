package api.repository;

import api.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    // custom query methods
    List<Proposal> findAllByStudentNumberIsNull();
}

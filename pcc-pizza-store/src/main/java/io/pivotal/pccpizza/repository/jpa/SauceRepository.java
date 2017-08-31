package io.pivotal.pccpizza.repository.jpa;

import io.pivotal.pccpizza.model.Sauce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepository extends JpaRepository<Sauce, Long> {
}

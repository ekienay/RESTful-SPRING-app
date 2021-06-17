package com.unnurnment.repository;

import com.unnurnment.model.Massage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassageRepo extends CrudRepository<Massage,Long> {
}

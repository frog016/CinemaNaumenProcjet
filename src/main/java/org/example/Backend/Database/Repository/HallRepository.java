package org.example.Backend.Database.Repository;

import org.example.Backend.Database.Model.Hall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "halls")
public interface HallRepository extends CrudRepository<Hall, Long> {
}

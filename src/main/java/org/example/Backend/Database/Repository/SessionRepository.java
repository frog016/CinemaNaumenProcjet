package org.example.Backend.Database.Repository;

import org.example.Backend.Database.Model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "sessions")
public interface SessionRepository extends CrudRepository<Session, Long> {
}

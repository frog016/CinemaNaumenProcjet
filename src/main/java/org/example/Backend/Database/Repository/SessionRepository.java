package org.example.Backend.Database.Repository;

import org.example.Backend.Database.Model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "sessions")
public interface SessionRepository extends CrudRepository<Session, Long> {
    public List<Session> getSessionsByMovie_Id(Long movieId);
}

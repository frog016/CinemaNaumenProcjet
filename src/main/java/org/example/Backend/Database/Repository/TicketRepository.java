package org.example.Backend.Database.Repository;

import org.example.Backend.Database.Model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "tickets")
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}

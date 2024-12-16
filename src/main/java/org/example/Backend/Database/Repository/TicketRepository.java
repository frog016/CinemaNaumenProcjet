package org.example.Backend.Database.Repository;

import org.example.Backend.Database.Model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "tickets")
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAllBySession_Id(Long sessionId);
    Ticket findTicketBySession_IdAndSeatNumber(Long sessionId, int seatNumber);
}

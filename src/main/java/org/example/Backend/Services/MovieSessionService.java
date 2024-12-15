package org.example.Backend.Services;

import org.example.Backend.Database.Model.Session;
import org.example.Backend.Database.Repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSessionService {
    private final SessionRepository sessionRepository;

    public MovieSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllSessions() {
        List<Session> list = new ArrayList<>();
        sessionRepository.findAll().forEach(list::add);

        return list;
    }

    public List<Session> getSessionsForMovie(Long movieId) {
        return sessionRepository.getSessionsByMovie_Id(movieId);
    }

    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    public void addSession(Session session) {
        sessionRepository.save(session);
    }

    public void updateSession(Session session) {
        sessionRepository.save(session);
    }

    public void removeSession(Long id) {
        sessionRepository.deleteById(id);
    }
}

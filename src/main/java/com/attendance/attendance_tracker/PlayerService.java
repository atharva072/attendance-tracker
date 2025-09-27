package com.attendance.attendance_tracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player addPlayer(Player player) {
        return playerRepository.save(player); // Hibernate auto-assigns ID
    }

    public Player searchPlayer(String username) {
        return playerRepository.findByUsername(username);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public void removePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}

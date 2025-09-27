package com.attendance.attendance_tracker;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public ApiResponse<Attendance> markAttendance(LocalDate date, boolean present, String username) {
        ApiResponse response = new ApiResponse("", "", null);
        
        Optional<Attendance> existing = attendanceRepository.findByPlayerUsernameAndDate(username, date);
        if (existing.isPresent()) {
            String status = "warning";
            String message = "Attendance already marked for this player on this date!";
            response = new ApiResponse<>(status, message, existing.get());
            return response;
        }

        Player player = playerRepository.findByUsername(username);
        if (player != null) {
            Attendance attendance = new Attendance();
            attendance.setDate(date);
            attendance.setPresent(present);
            attendance.setPlayer(player);
            attendance.setUsername(username);

            response.setStatus("success");
            response.setMessage("Attendance marked successfully");
            response.setData(attendanceRepository.save(attendance));
            return response;
        } else {
            response.setStatus("failed");
            response.setMessage("This player does not exists");
            response.setData(null);
            return response;
        }
    }

    public ApiResponse<List<Attendance>> getAttendanceForPlayer(String username) {
        ApiResponse<List<Attendance>> response = new ApiResponse<>("", "", null);
        Player player = playerRepository.findByUsername(username);
        if (player != null) {
            List<Attendance> playerAttendance = player.getAttendances();
            response.setData(playerAttendance);
            response.setStatus("success");
            if (playerAttendance != null && !playerAttendance.isEmpty()) {
                response.setMessage("Successfully found attendance for the player");
            } else {
                response.setMessage("Looks like there is no attendance for the selected player");
            }
        } else {
            response.setStatus("error");
            response.setMessage("Player not found");
        }
        return response;
    }

    public ApiResponse<Attendance> getAttendanceByUsernameAndDate(String username, LocalDate date) {
        ApiResponse response = new ApiResponse<>(null, null, attendanceRepository);
        Optional<Attendance> playerAttendance = attendanceRepository.findByPlayerUsernameAndDate(username, date);
        response.setData(playerAttendance);
        response.setStatus("success");
        if (playerAttendance != null && !playerAttendance.isEmpty()) {
            response.setMessage("");
        } else {
            response.setMessage("Looks like the player was absent on this date");
        }
        return response;
    }

    public ApiResponse<Attendance> getAllAttendance() {
        ApiResponse response = new ApiResponse<>(null, null, attendanceRepository);
        List<Attendance> allAttendance = attendanceRepository.findAll();
        if (allAttendance != null && !allAttendance.isEmpty()) {
            response.setMessage("");
            response.setStatus("success");
            response.setData(allAttendance);
        } else {
            response.setMessage("No Attendances found.");
            response.setStatus("success");
            response.setData(allAttendance);
        }
        return response;
    }

    @Transactional
    public ApiResponse<Attendance> removeAttendance(String username, LocalDate date) {
        ApiResponse response = new ApiResponse<>(null, null, attendanceRepository);
        Optional<Attendance> removedAttendance = attendanceRepository.deleteByPlayerUsernameAndDate(username, date);
        if (removedAttendance != null && !removedAttendance.isEmpty()) {
            response.setMessage("Attendance removed for " + username + " on date " + date);
            response.setStatus("success");
            response.setData(removedAttendance);
        } else {
            response.setMessage("No Attendances found for removal");
            response.setStatus("success");
            response.setData(removedAttendance);
        }
        return response;
    }
}

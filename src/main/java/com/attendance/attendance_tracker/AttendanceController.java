package com.attendance.attendance_tracker;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/mark")
    public ApiResponse<Attendance> markAttendance(@RequestBody Attendance request) {
        return attendanceService.markAttendance(request.getDate(), request.isPresent(), request.getPlayer().getUsername());
    }

    @GetMapping("/player/{username}")
    public ApiResponse<List<Attendance>> getPlayerAttendance(@PathVariable String username) {
        return attendanceService.getAttendanceForPlayer(username);
    }

    @GetMapping("/{username}/{date}")
    public ApiResponse<Attendance> getAttendanceByUsernameAndDate(@PathVariable String username, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceService.getAttendanceByUsernameAndDate(username, localDate);
    }

    /*
    @GetMapping("/all")
    public ApiResponse<Attendance> getAllAttendanceOnDate() {
        return attendanceService.getAllAttendanceOnDate();
    }
    */

    @GetMapping("/all")
    public ApiResponse<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @DeleteMapping("/remove")
    public ApiResponse<Attendance> removeAttendance(@RequestParam String username, @RequestParam String date) {
        return attendanceService.removeAttendance(username, LocalDate.parse(date));
    }
}

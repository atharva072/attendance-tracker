package com.attendance.attendance_tracker;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByPlayerUsernameAndDate(String username, LocalDate date);
    Optional<Attendance> deleteByPlayerUsernameAndDate(String username, LocalDate date);
}

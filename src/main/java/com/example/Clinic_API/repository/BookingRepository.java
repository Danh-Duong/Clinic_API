package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Booking;
import com.example.Clinic_API.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    public List<Booking> findByStatusAndDoctor(String status, User doctor, Pageable pageable);

    @Query(nativeQuery = true, value = "select b.doctor_id " +
            "from booking as b join User as u on u.id=b.doctor_id "+
            "group by b.doctor_id "+
            "order by count(*) desc "+
            "limit 3")
    public List<Long> getBestDoctor();

    @Query(nativeQuery = true, value = "select count(*) from booking where doctor_id= :doctorId")
    public Long countBookingDoctor(Long doctorId);
}

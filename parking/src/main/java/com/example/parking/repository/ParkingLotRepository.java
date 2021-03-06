package com.example.parking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.model.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    public Page<ParkingLot> findByAddrContainingOrParkingNameContaining(String addkeyword, String namekeyword,
            Pageable pageable);

    public List<ParkingLot> findByUsername(String username);
}

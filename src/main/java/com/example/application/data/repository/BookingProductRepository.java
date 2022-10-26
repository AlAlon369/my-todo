package com.example.application.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.BookingProduct;

public interface BookingProductRepository extends JpaRepository<BookingProduct, Integer> {
  List<BookingProduct> findByBooking(Booking booking);
}
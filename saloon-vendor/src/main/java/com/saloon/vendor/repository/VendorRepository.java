package com.saloon.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.saloon.vendor.datasource.entities.TblVendor;


@Repository
public interface VendorRepository extends JpaRepository<TblVendor, Integer>{

	
}

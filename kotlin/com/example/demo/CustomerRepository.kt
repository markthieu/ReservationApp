package com.example.demo

import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Long>
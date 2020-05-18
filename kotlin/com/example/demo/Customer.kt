package com.example.demo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Customer(
    val fullName: String,
    val phoneNo: String,
    val email: String,
    val date: String,
    @Id @GeneratedValue
    var customerNo: Long? = 0)
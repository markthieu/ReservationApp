package com.example.demo

import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.cloud.gcp.pubsub.PubSubAdmin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter

@RestController
class Controller(val pubSubTemplate: PubSubTemplate,val pubSubAdmin: PubSubAdmin ,val customerRepository: CustomerRepository) {
  
  // The Pub/Sub topic name created earlier.
  val REGISTRATION_TOPIC = "registrations"


//Post a reservation
  @PostMapping("/registerPerson")
  fun registerPerson(
    @RequestParam("fullName") fullName: String,
    @RequestParam("phoneNo") phoneNo: String,
    @RequestParam("email") email: String,
    @RequestParam("date") date: String): RedirectView {

    pubSubTemplate.publish(
        REGISTRATION_TOPIC,
        Customer(fullName, phoneNo, email, date))
    return RedirectView("/")
  }
 // Return all the reservations
  @GetMapping("/reservations")
  fun getRegistrants(): ModelAndView {
    val personsList = customerRepository.findAll().toList()
    return ModelAndView("reservations", mapOf("personsList" to personsList))
  }

// Find a reservation by ID
  @RequestMapping(value = ["/findIt"], method = [RequestMethod.GET])
   fun findReservation(@RequestParam customerNo: Long): String {
       val customerFound = customerRepository.findById(customerNo).toString()
        return customerFound
   }

// Delete a reservation by ID
  @RequestMapping(value = ["/delete"], method = [RequestMethod.GET])
  @ResponseBody
   fun deleteCustomerById(@RequestParam customerNo: Long): RedirectView{
    customerRepository.deleteById(customerNo)
    return RedirectView("/deletereservations")
    }

// Delete all reservations
  @RequestMapping(value = ["/deleteall"], method = [RequestMethod.GET])
  @ResponseBody
   fun deleteCustomerByDate(@RequestParam date: String): RedirectView{
    customerRepository.deleteAll()
    return RedirectView("/deletereservations")
    }
}
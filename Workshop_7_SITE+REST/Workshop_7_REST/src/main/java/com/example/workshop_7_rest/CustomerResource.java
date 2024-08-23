package com.example.workshop_7_rest;

import Model.Customer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.logging.Logger;

// ************* NOTE: YOU MAY NEED TO CHANGE THE persistence.xml TO HAVE THE CORRECT DB LOGIN INFO
@Path("/customer")
public class CustomerResource {

    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
//    http://localhost:8080/Workshop_7_REST-1.0-SNAPSHOT/api/<method name>
    @GET
    @Path("/getallcustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCustomers() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager em = factory.createEntityManager();
        Query query = em.createQuery("select a from Customer a");
        List<Customer> list= query.getResultList();
        Gson gson = new Gson();

        return gson.toJson(list);
    }

    @GET
    @Path("getcustomer/{ customerid }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomer(@PathParam("customerid") int customerId)  {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager em = factory.createEntityManager();
        Customer customer = em.find(Customer.class, customerId);
        Gson gson = new Gson();

        return gson.toJson(customer);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/putCustomer")
    public String putCustomer(String jsonString) {
        String message = null;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager em = factory.createEntityManager();
        Gson gson = new Gson();
        Customer customer = gson.fromJson(jsonString, Customer.class);
        logger.info(customer.toString());
        em.getTransaction().begin();
        em.persist(customer);
        if(em.contains(customer)) {

            em.getTransaction().commit();
            message = "{'message' : 'Customer Account Created successfully' }";
        } else {

            em.getTransaction().rollback();
            message = "{ 'message' : 'Customer Account Creation failed' }";
        }
        em.close();
        return message;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/postagent")
    public String postCustomer(String jsonString) {
        String message = null;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager em = factory.createEntityManager();
        Gson gson = new Gson();
        Customer customer = gson.fromJson(jsonString, Customer.class);
        em.getTransaction().begin();
        Customer mergerdCustomer = em.merge(customer);
        if(mergerdCustomer != null) {

            em.getTransaction().commit();
            message = "{'message' : 'Customer posted successfully' }";
            logger.info(message);
        } else {

            em.getTransaction().rollback();
            message = "{ 'message' : 'Customer update failed' }";
            logger.info(message);
        }
        em.close();
        return message;
    }
}
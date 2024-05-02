package com.example.workshop_7_rest;

import Model.Customer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.lang.reflect.Type;
import java.sql.ClientInfoStatus;
import java.util.List;
// ************* NOTE: YOU MAY NEED TO CHANGE THE persistence.xml TO HAVE THE CORRECT DB LOGIN INFO
@Path("/customer")
public class CustomerResource {
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
    @Path("/postagent") // ignoring this lol
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
        } else {

            em.getTransaction().rollback();
            message = "{ 'message' : 'Customer update failed' }";
        }
        em.close();
        return message;
    }
}
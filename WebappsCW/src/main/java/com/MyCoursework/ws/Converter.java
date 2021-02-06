package com.MyCoursework.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/conversion")
public class Converter {
    
    @GET
    @Path("/{currency1}/{currency2}/{amount}")
    public Response convertCurrency(@PathParam("currency1") String currency1, 
            @PathParam("currency2") String currency2, @PathParam("amount") Double amount) {
        
        if (!isValidCurrency(currency1) || !isValidCurrency(currency2))
            return Response.status(400).entity("Currency not valid").build();
        
        double amountConverted = amount * factorConversion(currency1, currency2);
        return Response.status(200).entity(amountConverted).build();
    }
    
    @GET
    @Path("/{currency1}/{currency2}/")
    public Response verifyCurrency(@PathParam("currency1") String currency1, 
            @PathParam("currency2") String currency2) {
        if (!isValidCurrency(currency1) || !isValidCurrency(currency2))
            return Response.status(400).entity("Currency not valid").build();
        
        return Response.status(200).entity("Valid currency").build();
    }
    
    private boolean isValidCurrency(String currency){
        if(currency.equals("GBP") || currency.equals("EUR") || currency.equals("USD")){
            return true;
        }
        
        return false;
    }
    
    //The conversion rates
    private double factorConversion(String currency1, String currency2){
        if(currency1.equals(currency2)){
            return 1.0;
        }
        
        if(currency1.equals("USD") && currency2.equals("EUR")){
            return 0.92;
        }
        
        if(currency1.equals("USD") && currency2.equals("GBP")){
            return 0.8;
        }
        
        if(currency1.equals("EUR") && currency2.equals("USD")){
            return 1.08;
        }
        
        if(currency1.equals("EUR") && currency2.equals("GBP")){
            return 0.87;
        }
        
        if(currency1.equals("GBP") && currency2.equals("USD")){
            return 1.24;
        }
        
        if(currency1.equals("GBP") && currency2.equals("EUR")){
            return 1.15;
        }
        
        return 0.0;
    }
}

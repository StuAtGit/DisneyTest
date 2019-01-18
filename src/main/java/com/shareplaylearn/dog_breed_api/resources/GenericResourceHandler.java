package com.shareplaylearn.dog_breed_api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class GenericResourceHandler {
    private static final Gson GSON = new Gson();
    public interface ResourceCallback<T> {
        T callback();
    }
    /**
     * Factors out 404/Not Found, 400 & 500 Errors into one convienent place,
     * while still allowing nicely informative message, logs,
     * and avoids using exceptions for non-exceptional stuff, like Not Found.
     * AND most importantly, makes for nice, clean unit tests of resources, because you can
     * now just check return values for 404, etc, instead of expecting exceptions.
     *
     * As well generically converting anything to JSON.
     *
     * @param resourceCallback - callback to generate
     * @param log - we pass in the logger, so the issue will be tracked to the appropriate resource.
     */
    public static <T> ResponseEntity<String> handleResource(
        ResourceCallback<T> resourceCallback,
        Logger log,
        String notFoundMessage
    ) {
        try {
            T t = resourceCallback.callback();
            if(t == null) {
                log.debug(notFoundMessage);
                return new ResponseEntity<>(GSON.toJson(notFoundMessage), HttpStatus.NOT_FOUND);
            }
            if(t instanceof Collection && ((Collection) t).size() == 0) {
                log.debug(notFoundMessage);
                return new ResponseEntity<>(GSON.toJson(notFoundMessage), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(GSON.toJson(t), HttpStatus.OK);
        //IllegalArgumentException makes a nice translation to 400/Error, not just
        //in terms of meaning, but also stuff like NumberFormatException that we often
        //run into when processing user input that comes as a string are children of
        //it
        } catch(IllegalArgumentException e) {
            log.info("Invalid use input: " + e.getMessage());
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Throwable t) {
            log.error("Server error ", t);
            return new ResponseEntity<>(GSON.toJson("Server Error: " + t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

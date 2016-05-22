package rest;


import domain.Movie;
import service.MoviesService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
public class MoviesResources {
    private MoviesService moviesDB = new MoviesService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getAll() {
        return moviesDB.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Movie movie) {
        moviesDB.add(movie);
        return Response.ok(movie.getId()).build();
    }

}

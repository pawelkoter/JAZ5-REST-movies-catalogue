package rest;


import domain.Comment;
import domain.Movie;
import service.MoviesService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/movies")
public class MoviesResources {
    private MoviesService moviesDB = new MoviesService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getAll() {
        return moviesDB.getAll();
    }

    @GET
    @Path( "/{id}" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam( "id" ) int id){
        Movie result =  moviesDB.get( id );

        if (result != Movie.NULL ) {
            return Response.ok( result ).build();
        }
        else {
            return Response.status( Response.Status.NOT_FOUND ).build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Movie movie) {
        moviesDB.add(movie);
        return Response.ok(movie.getId()).build();
    }

    @PUT
    @Path( "/{id}" )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam( "id" ) int id, Movie movie) {
        movie.setId( id );
        try {
            moviesDB.update( movie );
            return Response.ok().build();
        } catch ( RuntimeException e ) {
            return Response.status( Response.Status.NOT_FOUND )
                    .entity( e.getMessage() )
                    .build();
        }
    }

    @DELETE
    @Path( "/{id}" )
    public Response delete(@PathParam( "id" ) int id) {
        if (moviesDB.delete( id )) {
            return Response.ok().build();
        }
        else {
            return Response.status( Response.Status.NOT_FOUND ).build();
        }
    }

    @GET
    @Path( "/{movieId}/comments" )
    @Produces(MediaType.APPLICATION_JSON)
    public List< Comment > getComments( @PathParam( "movieId" ) int movieId){
        return moviesDB.get( movieId ).getComments();
    }

    @POST
    @Path( "/{movieId}/comments" )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment( @PathParam( "movieId" ) int movieId, Comment comment) {
        Movie movie = moviesDB.get( movieId );
        if ( movie != Movie.NULL ) {
            movie.getComments().add( comment );
            return Response.ok().build();
        }
        else {
            return Response.status( Response.Status.NOT_FOUND ).build();
        }
    }

    @GET
    @Path( "/{movieId}/rating" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRating(@PathParam( "movieId" ) int id) {
        Movie movie = moviesDB.get( id );
        if ( movie != Movie.NULL ) {
            BigDecimal rating = movie.getRating();
            return Response.ok(rating).build();
        }
        else {
            return Response.status( Response.Status.NOT_FOUND ).build();
        }
    }

    @POST
    @Path( "/{movieId}/rating/{rating}" )
    public Response addRating( @PathParam( "movieId" ) int movieId, @PathParam( "rating" ) int rating) {
        Movie movie = moviesDB.get( movieId );

        try {
            if ( movie != Movie.NULL ) {
                movie.addRating( rating );
                return Response.ok().build();
            }
            else {
                return Response.status( Response.Status.NOT_FOUND ).build();
            }
        } catch ( RuntimeException e ) {
            return Response.status( Response.Status.NOT_FOUND ).entity( e.getMessage() ).build();
        }
    }

}

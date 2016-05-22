package service;


import domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoviesService {
    private static List<Movie> movieList = new ArrayList<>();
    private static int currentId;

    public List<Movie> getAll() {
        return movieList;
    }

    public Movie get(int id) {
        Optional<Movie> optional = movieList.stream()
                .filter( m -> m.getId() == id )
                .findFirst();

        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            return Movie.NULL;
        }
    }

    public void add(Movie movie){
        movie.setId(currentId);
        movieList.add(movie);
        currentId++;
    }

    public void update(Movie movie) {
        Movie old = get( movie.getId() );
        if ( old != Movie.NULL ) {
            movieList.remove( old );
            movieList.add( movie );
        }
        else {
            throw new RuntimeException( String.format( "Could not update movie with id = %d : movie not found", movie.getId() ) );
        }
    }

    public boolean delete(int id) {
        return movieList.removeIf( m -> m.getId() == id );
    }
}

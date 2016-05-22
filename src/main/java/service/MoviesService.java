package service;


import domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesService {
    private static List<Movie> movieList = new ArrayList<>();
    private static int currentId;

    public List<Movie> getAll() {
        return movieList;
    }

    public void add(Movie movie){
        movie.setId(currentId);
        movieList.add(movie);
        currentId++;
    }
}

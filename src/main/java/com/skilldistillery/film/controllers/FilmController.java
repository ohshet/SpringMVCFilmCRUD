package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.database.DatabaseAccessor;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {

	@Autowired
	private DatabaseAccessor filmDAO;

	@RequestMapping(path = "GetFilmById.do", params = "filmId", method = RequestMethod.GET)
	public ModelAndView getFilmById(@RequestParam("filmId") int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = null;
		String result = null;
		try {
			film = filmDAO.findFilmById(filmId);
		} catch (SQLException e) {
			result = "No films found";
		}
		mv.addObject("film", film);
		mv.addObject("result", result);
		mv.setViewName("/WEB-INF/filmPage.jsp");
		return mv;
	}
	
	@RequestMapping(path = "GetFilmByKeyword.do", params = "keyword", method = RequestMethod.GET)
	public ModelAndView getFilmByKeyword(String keyword) {
		ModelAndView mv = new ModelAndView();
		String result = null;
		List<Film> filmList = new ArrayList<Film>();
		try {
			filmList = filmDAO.findFilmByKeyword(keyword);
		} catch (SQLException e) {
			result = "No films found";
		}
		mv.addObject("filmList", filmList);
		mv.addObject("result", result);
		mv.setViewName("/WEB-INF/filmPage.jsp");
		return mv;
	}
	
	@RequestMapping(path = "DeleteFilmById.do", params = "filmId", method = RequestMethod.POST)
	public ModelAndView deleteFilmById(int filmId) {
		ModelAndView mv = new ModelAndView();
		String result = null;
		try {
			Film filmToDelete = filmDAO.findFilmById(filmId);
			result = filmDAO.deleteFilm(filmToDelete);
		} catch (SQLException e) {
			result = "Error deleting film";
		}
		mv.addObject("result", result);
		mv.setViewName("/WEB-INF/filmPage.jsp");
		return mv;
	}
	@RequestMapping(path = "ModifyFilm.do", params = {"filmId", "title", "description", "releaseYear", "languageId", "length", "rating"}, method = RequestMethod.POST)
	public ModelAndView modifyFilm(int filmId, String title, String description, int releaseYear, int languageId, int length, String rating) {
		ModelAndView mv = new ModelAndView();
		String result = null;
		Film newFilm = null;
		try {
		newFilm = new Film(0, title, description, releaseYear, languageId, length, rating);
		Film oldFilm = filmDAO.findFilmById(filmId);
		result = filmDAO.modifyFilm(oldFilm, newFilm);
		}
		catch(Exception e) {
			result = "Error trying to modify film";
		}
		mv.addObject("newFilm", newFilm);
		mv.addObject("result", result);
		mv.setViewName("/WEB-INF/filmPage.jsp");
		return mv;
	}
}

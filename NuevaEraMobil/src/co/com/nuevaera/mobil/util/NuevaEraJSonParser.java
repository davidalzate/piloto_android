package co.com.nuevaera.mobil.util;

import java.util.ArrayList;
import java.util.List;

import co.com.nuevaera.mobil.model.AnuncioDto;
import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.RestauranteDto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NuevaEraJSonParser {

	public static ArrayList<RestauranteDto> getRestaurantsFromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<RestauranteDto>>(){}.getType());
	}

	public static ArrayList<CategoriaDto> getCategoriesFromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<CategoriaDto>>(){}.getType());
	}
	
	public static ArrayList<ElementoDto> getElementsFromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<ElementoDto>>(){}.getType());
	}

	public static ArrayList<AnuncioDto> getAddsFromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<AnuncioDto>>(){}.getType());
	}
}

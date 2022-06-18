package pruebas;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SelectorNombre implements Selector {

	private SortedSet<String> nombres;

	public SelectorNombre(Set<String> set) {
		if (set == null)
			throw new AppException("Conjunto nulo");

		nombres = new TreeSet<>();

		for (String nombre : set) {
			nombres.add(nombre.toUpperCase());
		}
	}

	public SortedSet<String> getNombres() {
		return nombres;
	}

	@Override
	public boolean esSeleccionable(Set<Practica> practicas) {
		return practicas.isEmpty() ? false : nombres.contains(practicas.iterator().next().getNombre().toUpperCase());
	}

}

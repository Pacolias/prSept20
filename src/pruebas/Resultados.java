package pruebas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Resultados {

	private SortedSet<Practica> practicas;
	private SortedMap<String, SortedSet<Practica>> alumnos;

	public Resultados(Set<Practica> set) {
		alumnos = new TreeMap<>();
		practicas = new TreeSet<Practica>(set);
	}

	private Practica buscar(SortedSet<Practica> pract, String descripcion) {
		boolean encontrado = false;
		Iterator<Practica> it = pract.iterator();
		Practica p = null;
		while (!encontrado && it.hasNext()) {
			p = it.next();
			encontrado = descripcion.equalsIgnoreCase(p.getDescripcion());
		}

		return encontrado ? p : null;
	}

	public void anyadirPractica(Practica p) {
		if (buscar(practicas, p.getDescripcion()) == null)
			throw new AppException("La practica no se encuentra en el conjunto de practicas");
		else
			p.setCntRealizadas(buscar(practicas, p.getDescripcion()).getCntRealizadas());

		SortedSet<Practica> set = alumnos.getOrDefault(p.getNombre(), new TreeSet<>());

		set.remove(p);
		set.add(p);

		alumnos.putIfAbsent(p.getNombre(), set);
	}

	public Resultados(Selector s) {
		Resultados r = new Resultados(practicas);

		for (SortedSet<Practica> set : alumnos.values()) {
			for (Practica p : set) {
				if (s.esSeleccionable(set))
					r.anyadirPractica(p);
			}
		}
	}

	@Override
	public String toString() {
		StringJoiner sj1 = new StringJoiner(";", "[", "]");

		for (Practica p : practicas) {
			sj1.add(p.toString());
		}

		StringJoiner sj2 = new StringJoiner(";", "[", "]");

		for (SortedSet<Practica> set : alumnos.values()) {
			for (Practica p : set) {
				sj2.add(p.toString());
			}
		}

		StringJoiner sj = new StringJoiner(",", "{", "}");
		sj.add(sj1.toString());
		sj.add(sj2.toString());

		return sj.toString();
	}

	public void guardarEnFichero(String file) throws IOException {
		PrintWriter pw = new PrintWriter(file);
		guardarEnFichero(pw);
		pw.close();
	}

	private void guardarEnFichero(PrintWriter pw) {
		for (SortedSet<Practica> set : alumnos.values()) {
			for (Practica p : set) {
				pw.println(p.toString());
			}
		}
	}

	public void cargarDeFichero(String file) throws IOException {
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\s*[;]\\s*");
		sc.useLocale(Locale.ENGLISH);
		cargarDeFichero(sc);
		sc.close();
	}

	private void cargarDeFichero(Scanner sc) {
		try {
			while (sc.hasNextLine()) {
				anyadirPractica(new Practica(sc.next(), sc.next(), sc.nextInt(), 0));
			}
		} catch (AppException | NoSuchElementException e) {
			// ignore
		}
	}

	public void completarPracticas() {
		SortedSet<Practica> nuevasPracticas = new TreeSet<>();

		for (Map.Entry<String, SortedSet<Practica>> e : alumnos.entrySet()) {
			if (e.getValue().size() != practicas.size()) {
				for (Practica p : practicas) {
					if (buscar(e.getValue(), p.getDescripcion()) == null)
						nuevasPracticas.add(p);

				}
			}
		}

		for (Practica p : nuevasPracticas) {
			anyadirPractica(p);
		}
	}
}

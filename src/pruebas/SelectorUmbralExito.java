package pruebas;

import java.util.Set;

public class SelectorUmbralExito implements Selector {

	private int umbralExito;

	public SelectorUmbralExito(int umbral) {
		if (umbral < 0)
			throw new AppException("Umbral de exito negativo: " + umbral);
		umbralExito = umbral;
	}

	public int getPorcMinimo() {
		return umbralExito;
	}

	@Override
	public boolean esSeleccionable(Set<Practica> set) {
		int sumaExito = 0;
		int sumaRealizadas = 0;

		for (Practica p : set) {
			sumaExito += p.getCntExito();
			sumaRealizadas += p.getCntRealizadas();
		}

		int porcExito = 100 * (sumaExito / sumaRealizadas);

		return porcExito >= umbralExito ? true : false;
	}
}

package org.contrum.Veterinaria.utils;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class ConsolePaginator {
    /**
     * Pide al usuario que navegue por los registros de la tabla asociada al
     * repositorio, mostrando una p  gina de registros a la vez.
     *
     * @param repository  El repositorio que proporciona los registros.
     * @param formatter   Un formateador que se aplica a cada registro para mostrarlo.
     *                    Debe devolver una lista de cadenas.
     * @param title       El t  tulo que se muestra encima de cada p  gina.
     * @param pageSize    El tama o de la p  gina (es decir, el n mero de registros que se
     *                    muestran en cada p  gina).
     */
    public <T> void paginate(JpaRepository<T, ?> repository, Function<T, List<String>> formatter, String title, int pageSize) {
        this.paginate(repository, null, formatter, title, pageSize);
    }

    /**
     * Pide al usuario que navegue por los registros de la tabla asociada al repositorio,
     * mostrando una página de registros a la vez.
     *
     * @param repository  El repositorio que proporciona los registros.
     * @param predicate   Un predicado que se aplica a cada registro antes de mostrarlo.
     *                    Si el predicado devuelve <code>false</code>, el registro se omite.
     *                    Puede ser <code>null</code>.
     * @param formatter   Un formateador que se aplica a cada registro para mostrarlo.
     *                    Debe devolver una lista de cadenas.
     * @param title       El título que se muestra encima de cada página.
     * @param pageSize    El tamaño de la página (es decir, el número de registros que se
     *                    muestran en cada página).
     */
    public <T> void paginate(JpaRepository<T, ?> repository, @Nullable Predicate<T> predicate, Function<T, List<String>> formatter, String title, int pageSize) {
        int currentPage = 0;

        while (true) {
            Page<T> page = repository.findAll(PageRequest.of(currentPage, pageSize));
            List<T> items = page.getContent();

            if (items.isEmpty()) {
                Printer.print("\nNo hay más registros.");
                return;
            }

            if (predicate != null) {
                items = items.stream().filter(predicate).toList();
            }

            Printer.print("\n" + title + " - Página " + (currentPage + 1) + " de " + page.getTotalPages());
            items.forEach(item -> Printer.print(formatter.apply(item)));
            Printer.print("<border>");

            Printer.print("\n(N) Siguiente página | (P) Página anterior | (X) Salir");
            String input = Printer.read().toLowerCase();

            switch (input) {
                case "n":
                    if (!page.hasNext()) {
                        Printer.print("No hay más páginas disponibles!");
                        continue;
                    }
                    currentPage++;
                case "p":
                    if (!page.hasPrevious()) {
                        Printer.print("No existe una página posterior!!");
                        continue;
                    }
                    currentPage--;
                case "x":
                    return;
                default:
                    Printer.print("Opción no válida.");
            }
        }
    }

    /**
     * Muestra una lista de elementos dividida en p  ginas.
     *
     * @param items    La lista de elementos que se van a mostrar.
     * @param formatter Un formateador que se aplica a cada elemento para mostrarlo.
     *                 Debe devolver una lista de cadenas.
     * @param title    El t  tulo que se muestra encima de cada p  gina.
     * @param pageSize El tama o de la p  gina (es decir, el n mero de elementos que se
     *                 muestran en cada p  gina).
     */
    public <T> void paginate(List<T> items, Function<T, List<String>> formatter, String title, int pageSize) {
        int currentPage = 0;

        int totalPages = (int) Math.ceil((double) items.size() / pageSize);

        if (items.isEmpty()) {
            Printer.print("\nNo hay registros para mostrar.");
            return;
        }

        while (true) {
            int fromIndex = currentPage * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, items.size());
            List<T> pageItems = items.subList(fromIndex, toIndex);

            Printer.print("\n" + title + " - Página " + (currentPage + 1) + " de " + totalPages);
            pageItems.forEach(item -> Printer.print(formatter.apply(item)));
            Printer.print("<border>");

            Printer.print("\n(N) Siguiente página | (P) Página anterior | (X) Salir");
            String input = Printer.read().toLowerCase();

            switch (input) {
                case "n":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        Printer.print("No hay más páginas disponibles!");
                    }
                    break;
                case "p":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        Printer.print("No existe una página anterior!!");
                    }
                    break;
                case "x":
                    return;
                default:
                    Printer.print("Opción no válida.");
            }
        }
    }
}
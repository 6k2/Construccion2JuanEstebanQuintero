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
    public <T> void paginate(JpaRepository<T, ?> repository, Function<T, List<String>> formatter, String title, int pageSize) {
        this.paginate(repository, null, formatter, title, pageSize);
    }

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
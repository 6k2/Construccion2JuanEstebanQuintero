package org.contrum.Veterinaria.adapters.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.models.Pet;
import org.contrum.Veterinaria.domain.services.SellerService;
import org.contrum.Veterinaria.ports.ClinicalRecordPort;
import org.contrum.Veterinaria.ports.InvoicePort;
import org.contrum.Veterinaria.ports.OrderPort;
import org.contrum.Veterinaria.utils.ConsolePaginator;
import org.contrum.Veterinaria.utils.Printer;
import org.contrum.Veterinaria.utils.validators.InvoiceValidator;
import org.contrum.Veterinaria.utils.validators.PersonValidator;
import org.contrum.Veterinaria.utils.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Component
public class SellerInput extends BaseStoreManagerInput {
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private SellerService service;

    @Autowired
    private ClinicalRecordPort clinicalRecordPort;
    @Autowired
    private InvoicePort invoicePort;
    @Autowired
    private OrderPort orderPort;

    @Autowired
    private InvoiceValidator invoiceValidator;

    @Autowired
    private ConsolePaginator consolePaginator;

    public void menu() {
        Printer.print(
                "<border>",
                "<center>Menu Vendedor",
                "<center>Elige una opción",
                "<border>",
                "1. Consultar ordenes medicas de mascota",
                "2. Consultar ordenes medicas pendientes",
                "3. Vender medicamento",
                "4. Vender producto",
                "5. Consultar facturas",
                "",
                "X. Cerrar sesion"
        );

        String choice = Printer.read().toLowerCase();
        try {
            switch (choice) {
                case "1": {
                    super.listOrders();
                    break;
                }
                case "2": {
                    this.listActiveOrders();
                    break;
                }
                case "3": {
                    this.sellMedicament();
                    Printer.print("\nVenta del medicamento registrada con exito!");
                    break;
                }
                case "4": {
                    this.sellProduct();
                    Printer.print("\nVenta del producto registrada con exito!");
                    break;
                }
                case "5": {
                    this.listInvoices();
                    break;
                }
                case "x": {
                    return;
                }
                default:
                    System.out.println("opcion no valida");
            }
        } catch (Exception e) {
            Printer.print(e.getMessage());
        }
        menu();
    }

    private void listActiveOrders() throws Exception {
        Pet pet = super.findPet();

        List<Order> orders = orderPort.findByPetId(pet.getId());
        if (orders.isEmpty()) {
            throw new Exception("No hay ordenes pendientes para esa mascota!");
        }

        orders.removeIf(order -> order == null || order.isCancelled());

        consolePaginator.paginate(
                orders,
                order -> List.of(
                        "<border>",
                        " ID: " + order.getId(),
                        " ID del Registro Clínico: " + order.getRecordId(),
                        " ID de la Mascota: " + order.getPetId(),
                        " ID del Dueño: " + order.getPetOwnerId(),
                        " ID del Veterinario: " + order.getVeterinarianId(),
                        " Medicamento: " + order.getMedicament(),
                        " Cancelada: " + order.isCancelled(),
                        " Fecha: " + order.getFormattedTimeStampText()
                ),
                "Listado de órdenes", 10
        );
    }

    private void sellMedicament() throws Exception {
        Pet pet = super.findPet();

        List<Order> orders = orderPort.findByPetId(pet.getId());
        if (orders.isEmpty()) {
            throw new Exception("No hay ordenes pendientes para esa mascota! No se pueden vender medicamentos sin una orden.");
        }

        Order order = super.choseOrder(orders, true);

        Printer.print("Ingrese la cantidad de medicamentos a vender");
        int quantity = invoiceValidator.productQuantityValidator(Printer.read());

        Printer.print("Ingrese el precio del medicamento");
        long price = invoiceValidator.productPriceValidator(Printer.read());

        Invoice invoice = this.createInvoice(pet, order, price, quantity);
        service.registerInvoice(invoice);
    }

    private void sellProduct() throws Exception {
        Pet pet = super.findPet();

        Printer.print("Ingrese el nombre del producto a vender");
        String productName = invoiceValidator.productNameValidator(Printer.read());

        Printer.print("Ingrese la cantidad de productos a vender");
        int quantity = invoiceValidator.productQuantityValidator(Printer.read());

        Printer.print("Ingrese el precio del producto");
        long price = invoiceValidator.productPriceValidator(Printer.read());

        Invoice invoice = this.createInvoice(pet, productName, price, quantity);
        service.registerInvoice(invoice);
    }

    private void listInvoices() throws Exception {
        Pet pet = super.findPet();

        List<Invoice> invoices = invoicePort.findByPetId(pet.getId());
        consolePaginator.paginate(
                invoices,
                invoice -> List.of(
                        "<border>",
                        " ID: " + invoice.getId(),
                        " ID de la Mascota: " + invoice.getPetId(),
                        " ID del Dueño: " + invoice.getPetOwnerId(),
                        " Producto: " + invoice.getProductName(),
                        " Precio: " + invoice.getPrice(),
                        " Cantidad: " + invoice.getQuantity(),
                        " Fecha: " + invoice.getFormattedTimeStampText(),
                        ""
                ),
                "Lista de facturas",
                10
        );
    }


    private Invoice createInvoice(Pet pet, String productName, long price, int quantity) {
        Invoice invoice = new Invoice();

        invoice.setPetId(pet.getId());
        invoice.setPetOwnerId(pet.getOwnerId());
        invoice.setProductName(productName);
        invoice.setPrice(price);
        invoice.setQuantity(quantity);

        invoice.setTimestamp(System.currentTimeMillis());

        return invoice;
    }

    private Invoice createInvoice(Pet pet, Order order, long price, int quantity) {
        Invoice invoice = new Invoice();

        invoice.setPetId(pet.getId());
        invoice.setPetOwnerId(pet.getOwnerId());
        invoice.setOrderId(order.getId());
        invoice.setProductName(order.getMedicament());
        invoice.setPrice(price);
        invoice.setQuantity(quantity);

        invoice.setTimestamp(System.currentTimeMillis());

        return invoice;
    }
}
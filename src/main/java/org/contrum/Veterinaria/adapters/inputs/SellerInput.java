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

    /**
     * Show the seller menu.
     * <p>
     * This method displays the seller menu, which allows the user to list medical
     * orders for a pet, list active medical orders, sell a medicament, sell a
     * product, list all invoices, or to exit the application.
     * <p>
     * If the user chooses to list medical orders for a pet, the
     * {@link #listOrders()} method is called and the user is asked for the
     * pet's ID. If the user chooses to list active medical orders, the
     * {@link #listActiveOrders()} method is called. If the user chooses to sell a
     * medicament, the {@link #sellMedicament()} method is called and the user
     * is asked for the required information. If the user chooses to sell a
     * product, the {@link #sellProduct()} method is called and the user is asked
     * for the required information. If the user chooses to list all invoices,
     * the {@link #listInvoices()} method is called.
     * <p>
     * If an exception occurs while executing any of the methods, the error
     * message is printed and the menu is displayed again.
     */
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


/**
 * Lists all active medical orders for a specific pet.
 *
 * <p>This method retrieves all orders associated with a pet's ID and filters
 * out any that are cancelled. The remaining active orders are displayed to the
 * user in a paginated format.
 *
 * @throws Exception if there are no orders associated with the pet
 */
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

    /**
     * Allows the user to sell a medicament for a pet.
     * <p>
     * This method first finds the pet associated with the user's input ID,
     * then retrieves all active orders associated with that pet. The user is
     * asked to select one of the orders, and then to enter the quantity and
     * price of the medicament to be sold. Finally, the method creates a new
     * invoice based on the user's input and registers it with the invoice
     * service.
     * <p>
     * If there are no active orders associated with the pet, an exception is
     * thrown.
     *
     * @throws Exception if there are no active orders associated with the pet
     */
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

    /**
     * Allows the user to sell a product for a pet.
     * <p>
     * This method first finds the pet associated with the user's input ID,
     * then asks the user to enter the name, quantity and price of the product
     * to be sold. Finally, the method creates a new invoice based on the
     * user's input and registers it with the invoice service.
     * <p>
     * If there are no active orders associated with the pet, an exception is
     * thrown.
     *
     * @throws Exception if there are no active orders associated with the pet
     */
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

    /**
     * Shows a list of invoices associated with a pet.
     * <p>
     * This method first finds the pet associated with the user's input ID,
     * then asks the user to enter the number of records to show. Finally, the
     * method paginates the list of invoices associated with the pet and
     * displays the results.
     * <p>
     * If there are no invoices associated with the pet, an exception is thrown.
     *
     * @throws Exception if there are no invoices associated with the pet
     */
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


    /**
     * Creates a new invoice for a product sold to a pet owner.
     *
     * <p>This method constructs an invoice for a product sold
     * to the owner of the specified pet, using the given product
     * name, price, and quantity. The invoice is timestamped with
     * the current system time.
     *
     * @param pet the pet associated with the invoice
     * @param productName the name of the product being sold
     * @param price the price of the product
     * @param quantity the quantity of the product being sold
     * @return the created invoice
     */
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

    /**
     * Creates a new invoice for a medicament sold to a pet owner.
     *
     * <p>This method constructs an invoice for a medicament sold
     * to the owner of the specified pet, using the given order,
     * price, and quantity. The invoice is timestamped with the
     * current system time.
     *
     * @param pet the pet associated with the invoice
     * @param order the order associated with the medicament being sold
     * @param price the price of the medicament
     * @param quantity the quantity of the medicament being sold
     * @return the created invoice
     */
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
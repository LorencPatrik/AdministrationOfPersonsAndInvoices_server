package cz.itnetwork.service;

import cz.itnetwork.dto.InvoicesPageDTO;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {

    /**
     * <p>Fetches one page of invoices. Uses the settings according to the data in the invoiceFilter.</p>
     *
     * @param invoiceFilter Data for filtering, sorting, limit for number of invoices, number of requested page
     * @return One sorted page of invoices and information about the available number of invoices in the database
     * and the limit of the number of invoices per page
     */
    InvoicesPageDTO getPage(InvoiceFilter invoiceFilter);

    /**
     * Creates a new invoice.
     *
     * @param invoiceDTO Invoice to create
     * @return New invoice
     */
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    /**
     * <p>Returns a list of a person's invoices with the matching {IdentificationNumber}, where the person is listed
     * as the seller.</p>
     *
     * @param personIdentificationNumber Person to find
     * @return List of invoices, where the person is listed as the seller
     */
    List<InvoiceDTO> getAllIssuedInvoices(String personIdentificationNumber);

    /**
     * <p>Returns a list of a person's invoices with the matching {IdentificationNumber}, where the person is listed
     * as the buyer.</p>
     *
     * @param personIdentificationNumber Person to find
     * @return List of invoices, where the person is listed as the buyer
     */
    List<InvoiceDTO> getAllAcceptedInvoices(String personIdentificationNumber);

    /**
     * <p>Returns one invoice with the matching [id].</p>
     * <p>In case an invoice with the passed [id] isn't found, the method <b>throws an exception 404.</b></p>
     *
     * @param id Invoice to find
     * @return One Invoice
     */
    InvoiceDTO getInvoice(long id);

    /**
     * <p>Updates the data of the existing invoice in the database with the matching [invoiceId].</p>
     * <p>In case an invoice with the passed [invoiceId] isn't found, the method <b>throws an exception 404.</b></p>
     *
     * @param invoiceId  Invoice to edit
     * @param invoiceDTO Entered data to edit invoice
     * @return Data of the modified invoice
     */
    InvoiceDTO editInvoice(long invoiceId, InvoiceDTO invoiceDTO);

    /**
     * Delete invoice with the matching [invoiceId].
     *
     * @param invoiceId Invoice to delete
     */
    void deleteInvoice(long invoiceId);

    /**
     * Returns statistic of all invoices for the last year and all time.
     *
     * @return One summary statistic
     */
    InvoiceStatisticDTO invoiceStatistic();
}

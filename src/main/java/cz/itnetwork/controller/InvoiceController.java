package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoicesPageDTO;
import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public InvoicesPageDTO getInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getPage(invoiceFilter);
    }

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody @Valid InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping("/identification/{personIdentificationNumber}/sales")
    public List<InvoiceDTO> getAllIssuedInvoices(@PathVariable String personIdentificationNumber) {
        return invoiceService.getAllIssuedInvoices(personIdentificationNumber);
    }

    @GetMapping("/identification/{personIdentificationNumber}/purchases")
    public List<InvoiceDTO> getAllAcceptedInvoices(@PathVariable String personIdentificationNumber) {
        return invoiceService.getAllAcceptedInvoices(personIdentificationNumber);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDTO getInvoice(@PathVariable Long invoiceId) {
        return invoiceService.getInvoice(invoiceId);
    }

    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO editInvoice(@PathVariable Long invoiceId, @RequestBody @Valid InvoiceDTO invoiceDTO) {
        return invoiceService.editInvoice(invoiceId, invoiceDTO);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }

    @GetMapping("/invoices/statistics")
    public InvoiceStatisticDTO getInvoiceStatistic() {
        return invoiceService.invoiceStatistic();
    }

    @GetMapping("/invoices/page")
    public InvoicesPageDTO getPage(InvoiceFilter invoiceFilter) {
        return invoiceService.getPage(invoiceFilter);
    }

    @GetMapping("/invoices/sort")
    public InvoicesPageDTO changeSorting(InvoiceFilter invoiceFilter) {
        return invoiceService.getPage(invoiceFilter);
    }
}

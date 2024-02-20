package cz.itnetwork.service;

import cz.itnetwork.dto.InvoicesPageDTO;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    private final PersonRepository personRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              InvoiceMapper invoiceMapper,
                              PersonRepository personRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.personRepository = personRepository;
    }

    @Override
    public InvoicesPageDTO getPage(InvoiceFilter invoiceFilter) {
        final Page<InvoiceEntity> pageEntities = getPageEntities(invoiceFilter);
        return invoiceMapper.addPaginationInfoToDTO(pageEntities, invoiceFilter.getLimit());
    }

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        final InvoiceEntity savedNewInvoice = invoiceRepository.save(invoiceMapper.toEntity(invoiceDTO));
        final PersonEntity buyer = fetchReferenceById(invoiceDTO.getBuyer().getId());
        savedNewInvoice.setBuyer(buyer);
        final PersonEntity seller = fetchReferenceById(invoiceDTO.getSeller().getId());
        savedNewInvoice.setSeller(seller);
        return invoiceMapper.toDTO(savedNewInvoice);
    }

    @Override
    public List<InvoiceDTO> getAllIssuedInvoices(String personIdentificationNumber) {
        final List<PersonEntity> persons = personRepository.findAllByIdentificationNumber(personIdentificationNumber);
        final List<InvoiceEntity> invoiceEntities = invoiceRepository.findAllBySellerIn(persons);
        return invoiceMapper.toDTO(invoiceEntities);
    }

    @Override
    public List<InvoiceDTO> getAllAcceptedInvoices(String personIdentificationNumber) {
        final List<PersonEntity> persons = personRepository.findAllByIdentificationNumber(personIdentificationNumber);
        final List<InvoiceEntity> invoiceEntities = invoiceRepository.findAllByBuyerIn(persons);
        return invoiceMapper.toDTO(invoiceEntities);
    }

    @Override
    public InvoiceDTO getInvoice(long invoiceId) {
        return invoiceMapper.toDTO(fetchInvoiceById(invoiceId));
    }

    @Override
    public InvoiceDTO editInvoice(long invoiceId, InvoiceDTO invoiceDTO) {
        InvoiceEntity fetchEntity = fetchInvoiceById(invoiceId);
        invoiceMapper.updateInvoiceEntity(invoiceDTO, fetchEntity);
        fetchEntity.setBuyer(fetchReferenceById(invoiceDTO.getBuyer().getId()));
        fetchEntity.setSeller(fetchReferenceById(invoiceDTO.getSeller().getId()));
        return invoiceMapper.toDTO(invoiceRepository.save(fetchEntity));
    }

    @Override
    public void deleteInvoice(long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    @Override
    public InvoiceStatisticDTO invoiceStatistic() {
        return invoiceRepository.lastYearStatistic();
    }

    // region: Private methods

    /**
     * <p>Attempt to fetch an invoice.</p>
     * <p>In case an invoice with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Invoice to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case an invoice with the passed [id] isn't found
     */
    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice id: " + id + " wasn't found in the database."));
    }

    /**
     * <p>Attempt to fetch person's reference.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchReferenceById(long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException("Person id: " + id + " wasn't found in the database.");
        }
        return personRepository.getReferenceById(id);
    }

    /**
     * <p>Sets sorting and checks that the entity page request is in the correct range, otherwise returns the first
     * or last page.</p>
     *
     * @param invoiceFilter Data for filtering, sorting, limit for number of invoices, number of requested page
     * @return One sorted page of invoices
     */
    private Page<InvoiceEntity> getPageEntities(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);
//        System.out.println(invoiceFilter.getNameOfAttribute());
        Sort sortBy = Sort.by(invoiceFilter.getNameOfAttribute().toString());
        Sort sortSet = (invoiceFilter.isAscending()) ? sortBy.ascending() : sortBy.descending();
        if (invoiceFilter.getPageNumber() < 0)
            invoiceFilter.setPageNumber(0);
        Pageable pageableSet = PageRequest.of(invoiceFilter.getPageNumber(), invoiceFilter.getLimit(), sortSet);
        final Page<InvoiceEntity> pageEntities = invoiceRepository.findAll(invoiceSpecification, pageableSet);
        final Page<InvoiceEntity> controlledPageEntities;
        if (pageEntities.getTotalPages() < (invoiceFilter.getPageNumber() + 1) && pageEntities.getTotalPages() > 0) {
            invoiceFilter.setPageNumber((pageEntities.getTotalPages() - 1));
            Pageable pageableSetLast = PageRequest.of(invoiceFilter.getPageNumber(), invoiceFilter.getLimit(), sortSet);
            controlledPageEntities = invoiceRepository.findAll(invoiceSpecification, pageableSetLast);
        } else
            controlledPageEntities = pageEntities;
        return controlledPageEntities;
    }
    // end region
}

package com.marekk.microlending.domain.loan.query;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
public class LoanFinderFacade {

    LoanFinderRepository loanFinderRepository;
    LoanConverter loanConverter;

    @Transactional(readOnly = true)
    public LoanSnapshot retrieve(String loanId, String customerId) {
        log.info("retrieving loan={} for customer={}", loanId, customerId);
            return loanConverter
                .convert(loanFinderRepository.findByLoanIdAndCustomerIdentityNo(loanId, customerId));

    }

    @Transactional(readOnly = true)
    public List<LoanSnapshot> retrieveAll(String customerId) {
        log.info("retrieving loans for customer={}", customerId);
        return Lists.newArrayList();
//        return loanFinderRepository
//                .findAllByCustomerIdentityNo(customerId);
    }

}

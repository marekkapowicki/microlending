package com.marekk.microlending.api.loan;

import com.marekk.microlending.domain.customer.CustomerFacade;
import com.marekk.microlending.domain.loan.query.LoanFinderFacade;
import com.marekk.microlending.domain.loan.query.LoanSnapshot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE;
import static com.marekk.microlending.api.Specification.CUSTOMER_HEADER_NAME;
import static com.marekk.microlending.api.Specification.ROOT;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@RequestMapping(value = ROOT + "/loans")
@Api(value = "Loans")
class LoanController {
    CustomerFacade customerFacade;
    LoanFinderFacade loanFinderFacade;

    @GetMapping(value = "/{loanId}", produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Lists loan by id for given customer", produces = API_CONTENT_TYPE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = CUSTOMER_HEADER_NAME, paramType = "header", required = true)
    })
    LoanSnapshot retrieve(
            @PathVariable("loanId") @NotNull String loanId,
            @RequestHeader(name = CUSTOMER_HEADER_NAME) @NotNull String customerId) {
        log.info("retrieving loanId={} for customer={}", loanId, customerId);
        customerExists(customerId);
        return loanFinderFacade.retrieve(loanId, customerId);
    }

    @GetMapping(produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Lists all loans for given customer", produces = API_CONTENT_TYPE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = CUSTOMER_HEADER_NAME, paramType = "header", required = true)
    })
    public List<LoanSnapshot> retrieveAll(@RequestHeader(name = CUSTOMER_HEADER_NAME) @NotNull String customerId) {
        log.info("retrieving all loans for customer={}", customerId);
        customerExists(customerId);
        return loanFinderFacade.retrieveAll(customerId);
    }

    private void customerExists(@RequestHeader(name = CUSTOMER_HEADER_NAME) @NotNull String customerId) {
        customerFacade.retrieve(customerId);
    }
}

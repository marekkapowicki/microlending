package com.marekk.microlending.api.loan;

import com.marekk.microlending.domain.loan.command.extension.ExtensionFacade;
import com.marekk.microlending.domain.loan.query.LoanFinderFacade;
import com.marekk.microlending.domain.loan.query.LoanSnapshot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE;
import static com.marekk.microlending.api.Specification.CUSTOMER_HEADER_NAME;
import static com.marekk.microlending.api.Specification.ROOT;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@RequestMapping(value = ROOT + "/loans")
@Api(value = "Loan extensions")
class ExtensionController {

    LoanFinderFacade loanFinderFacade;
    ExtensionFacade extensionFacade;

    @PostMapping(value = "/{loanId}/extension", consumes = API_CONTENT_TYPE)
    @ApiOperation("Extends given loan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = CUSTOMER_HEADER_NAME, paramType = "header", required = true)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = Map.class),
            @ApiResponse(code = 404, message = "loan does not exists")})
    void extendLoan(@PathVariable("loanId") String loanId,
                    @RequestHeader(name = CUSTOMER_HEADER_NAME) @NotNull String customerId) {
        LoanSnapshot loan = loanFinderFacade.retrieve(loanId, customerId);
        extensionFacade.extend(loan);
    }
}

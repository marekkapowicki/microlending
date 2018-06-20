package com.marekk.microlending.api.application;

import com.marekk.microlending.domain.application.LoanApplicationCommand;
import com.marekk.microlending.domain.application.LoanApplicationFacade;
import com.marekk.microlending.domain.application.validation.IpValidationService;
import com.marekk.microlending.domain.customer.CustomerFacade;
import com.marekk.microlending.infrastructure.IpAddressProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javaslang.control.Either;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;

import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE;
import static com.marekk.microlending.api.Specification.CUSTOMER_HEADER_NAME;
import static com.marekk.microlending.api.Specification.ROOT;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@RequestMapping(value = ROOT + "/loans")
@Api(value = "Loan applications")
class LoanApplicationController {
    CustomerFacade customerFacade;
    LoanApplicationFacade loanApplicationFacade;
    IpValidationService ipValidationService;


    @PostMapping(consumes = API_CONTENT_TYPE, produces = API_CONTENT_TYPE, value = "applications")
    @ApiOperation(value = "Sends a single loan application",
            notes = "This operation will either return loan ID or rejection reason")
    @ApiImplicitParams({
            @ApiImplicitParam(name = CUSTOMER_HEADER_NAME, paramType = "header", required = true)})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Application processed", response = Map.class),
            @ApiResponse(code = 412, message = "Application rejected")})
    public ResponseEntity<Map<String, Object>> sendLoanApplication(
            @RequestBody @Valid @NotNull LoanApplicationCreateRequest request,
            ServletRequest servletRequest,
            @RequestHeader(name = CUSTOMER_HEADER_NAME) @NotNull String customerId) {
        log.info("new loan application is processing={} for customerId={}", request, customerId);
        final InetAddress ipAddress = rememberIpHit(servletRequest);
        LoanApplicationCommand applicationCommand = request
                .toCommand(customerFacade.retrieve(customerId), ipAddress);
        Either<String, String> result = loanApplicationFacade.process(applicationCommand);
        return result.fold(
                failedValidation ->
                        response(PRECONDITION_FAILED, "reason", failedValidation),
                loan -> response(CREATED, "id", loan)
        );
    }

    private InetAddress rememberIpHit(ServletRequest servletRequest) {
        final InetAddress ipAddress = IpAddressProvider.provide(servletRequest);
        ipValidationService.rememberIpHit(ipAddress);
        return ipAddress;
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, String key, Object value) {
        final Map<String, Object> jsonResponse = Collections.singletonMap(key, value);
        return new ResponseEntity<>(jsonResponse, status);
    }

}

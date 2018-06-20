package com.marekk.microlending.api.customer;

import com.marekk.microlending.api.IdResponse;
import com.marekk.microlending.domain.customer.CustomerFacade;
import com.marekk.microlending.domain.customer.CustomerSnapshot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE;
import static com.marekk.microlending.api.Specification.ROOT;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@RequestMapping(value = ROOT + "/customers")
@Api(value = "Customers")
class CustomerController {
    CustomerFacade customerFacade;

    @PostMapping(consumes = API_CONTENT_TYPE, produces = API_CONTENT_TYPE)
    @ApiOperation(value = "register a new customer", produces = API_CONTENT_TYPE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success", response = IdResponse.class),
            @ApiResponse(code = 409, message = "Customer already exists")})
    public IdResponse register(@RequestBody @Valid @NotNull CustomerRegisterRequest registerRequest) {
        log.info("new request arrived {}", registerRequest);
        return IdResponse.of(customerFacade.register(registerRequest.toCommand()));
    }

    @GetMapping(value = "/{id}", produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Lists customer by id", produces = API_CONTENT_TYPE)
    public CustomerSnapshot retrieve(@PathVariable("id") String id) {
        log.info("retrieve customer by id={}", id);
        return customerFacade.retrieve(id);
    }

    @GetMapping(produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Lists all customers", produces = API_CONTENT_TYPE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. "
                            + "Multiple sort criteria are supported.")
    })
    public Page<CustomerSnapshot> retrieveAll(@NotNull Pageable pageable) {
        log.info("retrieving all customers");
        return customerFacade.retrieve(pageable);
    }
}
package com.marekk.microlending.domain.loan.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface LoanFinderRepository extends JpaRepository<LoanViewRow, Long> {
    List<LoanViewRow> findByLoanIdAndCustomerIdentityNo(String id, String identityNo);

    List<LoanViewRow> findAllByCustomerIdentityNo(String identityNo);
}

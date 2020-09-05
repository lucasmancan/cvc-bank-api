package br.com.cvcbank.repositories;

import br.com.cvcbank.entities.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Optional<Account> findByNumber(String number);

    Optional<Account> findByDocument(String document);
}

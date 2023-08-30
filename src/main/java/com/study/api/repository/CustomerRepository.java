package com.study.api.repository;

import com.study.api.entity.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {
  private static final List<Customer> repository = new ArrayList<>();

  @PostConstruct
  public void init(){
    String password = new BCryptPasswordEncoder().encode("test");
    long i = 1;
    repository.add(Customer.builder().loginId("test_"+i).id(i++).name("오범수").password(password).joinedAt(ZonedDateTime.now()).build());
    repository.add(Customer.builder().loginId("test_"+i).id(i++).name("김뿅뿅").password(password).joinedAt(ZonedDateTime.now()).build());
    repository.add(Customer.builder().loginId("test_"+i).id(i++).name("이나라").password(password).joinedAt(ZonedDateTime.now()).build());
    repository.add(Customer.builder().loginId("test_"+i).id(i++).name("손은희").password(password).joinedAt(ZonedDateTime.now()).build());
    repository.add(Customer.builder().loginId("test_"+i).id(i++).name("오유하").password(password).joinedAt(ZonedDateTime.now()).build());
    repository.add(Customer.builder().loginId("test_"+i).id(i++).name("오오오오").password(password).joinedAt(ZonedDateTime.now()).build());
    repository.add(Customer.builder().loginId("test_"+i).id(i).name("대애박").password(password).joinedAt(ZonedDateTime.now()).build());
  }

  public List<Customer> findAll(){
    return repository;
  }

  public Optional<Customer> findByLoginId(String loginId){
    return repository.stream().filter(customer -> customer.getLoginId().equals(loginId)).findFirst();
  }
}

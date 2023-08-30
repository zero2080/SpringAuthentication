package com.study.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String loginId;
    private String name;
    private String password;
    private ZonedDateTime joinedAt;

    public static CustomerBuilder builder(){
        return new CustomerBuilder();
    }

    public static class CustomerBuilder{
        private Long id;
        private String loginId;
        private String name;
        private String password;
        private ZonedDateTime joinedAt;

        public CustomerBuilder loginId(String loginId) {
            this.loginId=loginId;
            return this;
        }
        public CustomerBuilder id(long id){
            this.id=id;
            return this;
        }

        public CustomerBuilder name(String name){
            this.name=name;
            return this;
        }

        public CustomerBuilder password(String password){
            this.password=password;
            return this;
        }

        public CustomerBuilder joinedAt(ZonedDateTime joinedAt){
            this.joinedAt=joinedAt;
            return this;
        }

        public Customer build(){
            return new Customer(id, loginId, name, password, joinedAt);
        }
    }
}

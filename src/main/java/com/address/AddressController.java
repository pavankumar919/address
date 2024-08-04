package com.address;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/{id}")
    public Mono<Address> getAddressById(@PathVariable int id){
        return addressService.getAddress(id);
    }

    @GetMapping
    public Flux<Address> getAddress(){
        return addressService.getAllAddress();
    }

}

@Service
class AddressService {

    @Autowired
    InMemoryDB inMemoryDB;
    public Mono<Address> getAddress(int id){
        return Mono.just(inMemoryDB.list.get(id));
    }

    public Flux<Address> getAllAddress(){
        return Flux.fromIterable(inMemoryDB.list);
    }
}

@Component
class InMemoryDB {
    List<Address> list;
    @PostConstruct
    public void construct(){
        Address address1 = new Address(1,"area1");
        Address address2 = new Address(2,"area2");
        Address address3 = new Address(3,"area3");
        Address address4 = new Address(4,"are4");
        Address address5 = new Address(5,"area5");
        list = Arrays.asList(address1, address2, address3, address4, address5);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Address {
    private int id;
    private String address;
}
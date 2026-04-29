package com.betacom.pr.services.implementations;

import com.betacom.pr.dto.inputs.AddressReq;
import com.betacom.pr.dto.outputs.AddressDTO;
import com.betacom.pr.models.Address;
import com.betacom.pr.repositories.IAddressRepository;
import com.betacom.pr.services.interfaces.IAddressServices;
import com.betacom.pr.services.interfaces.IUserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressImpl implements IAddressServices {

    private final IAddressRepository addressRepository;
    private final IUserServices userServices;

    @Override
    public Integer create(AddressReq req) throws Exception {
        log.debug("create {}", req);

        Address address = new Address();
        address.setCountry(req.getCountry().toUpperCase());
        address.setCity(req.getCity().toUpperCase());
        address.setPostalCode(req.getPostalCode());
        address.setStreet(req.getStreet().toUpperCase());
        address.setNumber(req.getNumber());

        addressRepository.save(address);

        return address.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createAndAssign(AddressReq req, String userName) throws Exception {
        Integer addId = create(req);
        userServices.addAddress(userName, addId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(AddressReq req) throws Exception {
        log.debug("update {}", req);
        Address address = addressRepository.findById(req.getId())
                .orElseThrow(() -> new Exception("Address not found"));

        if(req.getCountry() != null)
            address.setCountry(req.getCountry().toUpperCase());
        if(req.getCity() != null)
            address.setCity(req.getCity().toUpperCase());
        if(req.getPostalCode() != null)
            address.setPostalCode(req.getPostalCode());
        if(req.getStreet() != null)
            address.setStreet(req.getStreet().toUpperCase());
        if(req.getNumber() != null)
            address.setNumber(req.getNumber());

        addressRepository.save(address);
    }

    @Override
    public void delete(Integer id) throws Exception {
        log.debug("delete {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new Exception("Address not found"));

        addressRepository.delete(address);

    }

    @Override
    public List<AddressDTO> list() throws Exception {
        log.debug("list");
        List<Address> addressList = addressRepository.findAll();
        return addressList.stream()
                .map(a -> AddressDTO.builder()
                        .id(a.getId())
                        .country(a.getCountry())
                        .city(a.getCity())
                        .postalCode(a.getPostalCode())
                        .street(a.getStreet())
                        .number(a.getNumber())
                        .build()
                ).collect(Collectors.toList());
    }

@Override
    public AddressDTO findById(Integer id) throws Exception {
        log.debug("findById {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new Exception("Address not found"));

        return AddressDTO.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .number(address.getNumber())
                .build();
    }

    @Override
    public List<AddressDTO> findByUserName(String userName) throws Exception {
        log.debug("findByUserName {}", userName);
        List<Address> addressList = addressRepository.findByUsersUserName(userName);
        return addressList.stream()
                .map(a -> AddressDTO.builder()
                        .id(a.getId())
                        .country(a.getCountry())
                        .city(a.getCity())
                        .postalCode(a.getPostalCode())
                        .street(a.getStreet())
                        .number(a.getNumber())
                        .build()
                ).collect(Collectors.toList());
    }
}

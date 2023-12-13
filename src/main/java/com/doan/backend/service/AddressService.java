package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Address;
import com.doan.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

//    private Collection<Address> getAddressByInfoId(Long id) {
//        Collection<Address> addressCollection = addressRepository.getAllAddressByInfoId(id);
//        return  addressCollection;
//    }

    public Address createAddress(Address address){
        address.setAddress(address.getAddress());
        return addressRepository.save(address);
    }
    /***
     * Delete address by id
     * @param id address id
     */
    public void deleteAddress(Long id) {
        // find address by id
        Optional<Address> addressOpt = addressRepository.findById(id);
        if(!addressOpt.isPresent()) { // check if address is found
            throw new ResourceException("Không tìm thấy thông tin địa chỉ");
        } else {
            // get address information
            Address address = addressOpt.get();
            addressRepository.delete(address);
        }
    }

    public List<Address> listAllAddress() {
        List<Address> addressList = addressRepository.listAll();
        return addressList;
    }
    public Address getAddress(Long id) {
        Optional<Address> address0 = addressRepository.findById(id);
        if(!address0.isPresent()) {
            throw new ResourceException("Không tìm thấy thông tin");
        } else {
            Address address = address0.get();
            return address;
        }
    }


    public Address updateAddress( Address address) {
        Address addr = getAddress(address.getId());

        addr.setAddress(address.getAddress().trim());
        addr.setCity(address.getCity());
        addr.setDistrict(address.getDistrict());
        addr.setWards(address.getWards());

        return addressRepository.save(addr);




    }
}

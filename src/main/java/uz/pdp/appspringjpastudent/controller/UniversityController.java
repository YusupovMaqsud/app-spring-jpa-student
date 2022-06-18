package uz.pdp.appspringjpastudent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringjpaonetomany.entity.Address;
import uz.pdp.appspringjpaonetomany.entity.University;
import uz.pdp.appspringjpaonetomany.payload.UniversityDto;
import uz.pdp.appspringjpaonetomany.repository.AddressRepository;
import uz.pdp.appspringjpaonetomany.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;

    //RED
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }


    //CREAT
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        //YANGI ADDRESS YASAB OLDIK
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());

        //YASAB OLGAN ADDRESS OBJECTINI DB SAQLADIK VA U BIZGA SAQLANGAN ADDRESS BERDI
        Address savedAddress = addressRepository.save(address);

        //YANGI UNIVERSITY YASAB OLDIK
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);
        return "University added";
    }


    //GET id
    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversity(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if(optionalUniversity.isPresent()){
            University university = optionalUniversity.get();
            return university;
        }else {
            return null;
        }

    }


    //Delete
    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        universityRepository.deleteById(id);
        return "University deleted";
    }


    //Update
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University edituniversity = optionalUniversity.get();
            edituniversity.setName(universityDto.getName());
            Address address = edituniversity.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            addressRepository.save(address);

            universityRepository.save(edituniversity);
            return "edited";
        }
        return "University not found";
    }

}

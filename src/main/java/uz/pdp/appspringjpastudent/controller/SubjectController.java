package uz.pdp.appspringjpastudent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringjpastudent.entity.Subject;
import uz.pdp.appspringjpastudent.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    //Creat
    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if(existsByName){
            return "This subject already exist";
        }
        subjectRepository.save(subject);
        return "added";

    }

    //Read
    @GetMapping
    public List<Subject> getSubject(){
        List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    //Delete
    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Integer id){
       subjectRepository.deleteById(id);
       return "Subject deleted";
    }

    //update
    //@PutMapping(value = "/{id}")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public String editSubject(@PathVariable Integer id,@RequestBody Subject subject){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isPresent()){
            Subject editsubject = optionalSubject.get();
            editsubject.setName(subject.getName());
            subjectRepository.save(editsubject);
            return "Subject edited";
        }else {
            return "Subject not found";
        }

    }
}

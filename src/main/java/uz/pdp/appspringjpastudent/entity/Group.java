package uz.pdp.appspringjpastudent.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    public  Faculty faculty;

//    @OneToMany    //bitta group ga ko'plab studentlari bo'ladi
//    private List<Student> students;
}

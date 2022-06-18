package uz.pdp.appspringjpastudent.payload;

import lombok.Data;

@Data
public class UniversityDto {  //Ma'lumotlarni tashish uchun hizmat qiladigan class
    private String name;
    private String city;
    private String district;
    private String street;
}

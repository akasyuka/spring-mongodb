package com.example.demo;

import com.example.demo.dao.Address;
import com.example.demo.dao.Gender;
import com.example.demo.dao.Student;
import com.example.demo.dao.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address("Russia",
					"332241",
					"Mos");
			String email = "alea@ma.com";
			Student student = new Student("Alex",
					"Bigge",
					email,
					Gender.MALE,
					address,
					List.of("Computer Science", "Atomic"),
					BigDecimal.TEN,
					LocalDateTime.now());

//			usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);
			repository.findStudentByEmail(email).ifPresentOrElse(s -> {
				System.out.println("Already exist");
			}, ()-> {
				repository.insert(student);
			} );

		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() == 0) {
			repository.insert(student);
		}
	}
}

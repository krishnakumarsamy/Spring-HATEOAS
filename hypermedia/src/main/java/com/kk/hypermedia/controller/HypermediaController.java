package com.kk.hypermedia.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kk.hypermedia.dao.StudentDAO;
import com.kk.hypermedia.exception.HypermediaException;
import com.kk.hypermedia.model.Student;

/**
 * This is the default controller used to returl all the available links
 * 
 * @author krishnakumar
 * 
 */
@Controller
public class HypermediaController {

	private transient Student student;

	@Autowired
	private transient StudentDAO studentdao;

	/**
	 * This method is used to get all the links The new user will not know all
	 * the end points and using HATEOAS we are generating links
	 * 
	 * @return ResponseEntity<Student>
	 * @throws HypermediaException
	 */
	@RequestMapping(value = "/")
	public ResponseEntity<Student> getLinks() throws HypermediaException {
		student = new Student();
		for (Student student1 : studentdao.getStudentList()) {
			student.add(linkTo(methodOn(StudentController.class).getStudent(student1.getStudentId())).withRel("Get_Student" + student1.getStudentId()));
		}
		student.add(linkTo(methodOn(StudentController.class).getStudentDetails()).withRel("Get_All_Students"));
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

}

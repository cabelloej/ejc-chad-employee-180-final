                    PROJECT ejc-chad-employee

What’s used here:

Spring MVC
Spring Web
Spring Boot
Properties File
Configuration File

Spring Data JPA
Spring Data REST
Spring Hibernate Bean Validation
Spring Security
Dev Tools

Lombok
MySql 
createDatabaseIfNotExist=true (Create Data Base)
DDL-Auto: update ( updates database tables & fields)
javax.persistence.schema-generation.scripts.action=create (creates "cabelloej_database_create.sql", but blocks DDL-Auto...) 

Bean validation: NotNull, Size, Min, Max, PastorPresent, Future, etc
Thymeleaf Form
Thymeleaf Form Validation Error Messeges (red danger color not implemented yet)
Thymeleaf DropDown List from DB table
Thymeleaf RadioButtons

@OneToMany Employee/Relative     ---   @ManyToOne Relative/Employee

@ManyToOne Employee/Department   ---   @OneToMany Department/Employee

@ManyToMany: All @ManyToMany Relationships were implemente acording to Baeldung (https://www.baeldung.com/jpa-many-to-many)
				
@ManyToMany Simple:
	NO "Employee_Sport" Class or Entity, onlya DB Table
	No additional properties in "employee_sport" DB table, 
	Simple JoinTable "employee_sport" DB table with no PrimaryKeys...?)
	Add new Entity: Sport (id, name, List<Employee> likedByEmployees)
	Employee: List<Sport> likedSports


@ManyToMany:
	Add Entity: SpokenLanguage 
	Employee: List<SpokenLanguage>  spokenLanguages
	Add JoinTable: EmployeeSpokenLanguage 
		with a Property: "proficiency" (Basic, Intermediate, Advanced, Native)
		with a property: Years of experience
					
					

PENDING:
					
@ManyToMany NewEntity: (Relation can be repeated, an employee can take the same course more than once)
					Add Course Entity
					Add EmployeeCourse Entity (id, employyeId, CourseId, Date, Grade, Comments)
					Employee: List<EmployyeCourse> employeeCourses 

Bootstrap CSS
Bootstrap Forms

Understand Objects, fields, variables in froms ($, *, #, etc)
Date Handling
Red error messages

data.sql not starting
Better Thymeleaf page flow
Environment configuration: Development/Production/ 

PROBANDO






 

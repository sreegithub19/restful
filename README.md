# Spring Boot; Building RESTful web services
Banuprakash C

Full Stack Architect,

Co-founder Lucida Technologies Pvt Ltd.,

Corporate Trainer,

Email: banu@lucidatechnologies.com; banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/restful
=============================================

Softwares Required:
1) Java 8+
2) Eclipse for Enterprise edition  / IntelliJ for Enterprise
3) MySQL / h2 memory database
4) Docker for Desktop [ Redis, Promethus] ==> docker-compose will be good
=============================================

Spring Framework
	==> Lightweight framework for building enterprise applications
	==> Core Modules of Spring Framework supports Dependecy Injection [ Inversion Of Control] and Life cycle management

	SOLID Design Principles ==> D ==> Dependency Injection

	What is DI?
======================================================

	Metadata XML:

	public interface ProductDao{
		void addProduct(Product p);
	}

	public class ProductDaoJpaImpl implements ProductDao {
		public void addProduct(Product p) {
				JPA code
		}	
	}

	public class ProductDaoMongoDbImpl implements ProductDao {
		public void addProduct(Product p) {
				db.collections.insert(p); ...
		}	
	}

	public class AppService {
		private ProductDao productDao;

		public void setProductDao(ProductDao pd) {
			this.productDao = pd;
		}
		..
	}

	app.xml
	<bean id="pjpa" class = "pkg.ProductDaoJpaImpl" />
	<bean id="pmongo" class = "pkg.ProductDaoMongoDbImpl" />
	<bean id="service" class = "pkg.AppService">
		<property name="productDao" ref="pmongo" />
	</bean>
=========================================
	MetaData: Annotations

	public interface ProductDao{
		void addProduct(Product p);
	}
	@Repository
	public class ProductDaoJpaImpl implements ProductDao { // productDaoJpaImpl
		public void addProduct(Product p) {
				JPA code
		}	
	}

	@Service 
	public class AppService { // appService
		@Autowired
		private ProductDao productDao;
	}

	Spring instantiates objects of classes which has one of these annoations:
	1) @Component
	2) @Repository
	3) @Service
	4) @Configuration
	5) @Controller
	6) @RestController


	Depenency Injection with annoations will be done using @Autowired or @Inject [ JEE Specification javax.inject libraries]
	@Autowired uses ==> Byte Code Instrumentation libraries like CGLIB or JavaAssist 
================================================================================================
Spring Boot?
	==> Framework ==> a layer on Spring Framework
	==> Simplifies Development 

	Highly opniated and configures lots of classes out of the box

	For Example ==> JPA == > ORM
		==> configures Hikari dataSource [ pool of database connection]
		==> configures Hibernate as ORM provider
		==> ans instantiates most of the required stuff for ORM

	For Web based ==> DispatcherServlet ==> Tomcat / Netty

	==> Cloudier
==================================================================

Help ==> Eclipse Market place ==> Search for STS [ Spring tools suite 4.x] and install

Restart the eclipse
===========================================================

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
================================
Dockerfile

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


===================
For Spring FrameworK: mvn clean compile package tomcat:run
========================


@SpringBootApplication
	==> @ComponentScan
		scans for classes with sterotype annotations on classes from "com.example.demo" package onwards
		and creates instances
	==> @EnableAutoConfiguration
		scans for "jar" files in build path and creates instances of classes managed by spring container
		like Connection Pool; DispatcherServlet; HibernateJPaVendor; ...
	==> @Configuration

SpringApplication.run ==> creates ApplicationContext [ Spring Container]
======================================================================
Run As ==> run Configuration
Program arguments
--debug
======================================================================
to resolve "Field employeeDao in com.example.demo.service.AppService required a single bean, but 2 were found"

@Repository
@Primary
public class EmployeeDaoMongoImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("mongo store !!!");
	}

}
==========

Solution 2: remove @Primary and use @Qualifier
@Autowired
	@Qualifier("employeeDaoMongoImpl")
	private EmployeeDao employeeDao;
==========================================

application.properties
spring.profiles.active=prod

or

Command line arguments:
--spring.profiles.active=dev

@Repository
@Profile("prod")
public class EmployeeDaoJpaImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("stored in MySQL!!!");
	}

}

@Repository 
@Profile("dev")
public class EmployeeDaoMongoImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("mongo store !!!");
	}

}

===============
Single object within a container:
@Scope("singleton")

for every DI different object:
@Scope("prototype")

When client makes a request this object is created and destroyed on response being commited
@Scope("request")
@ConditionalOnBean("hikariConnectionCP")

request.setAttribute("employeeDaoMongoImpl", new EmployeeDaoMongoImpl());

@Scope("session")
===========================================

@Autowired
@Qualifier("mongo")
private EmployeeDao employeeDaoMongo;

@Autowired
@Qualifier("mysql")
private EmployeeDao employeeDaoMySQL;

public void insert() {
	if(employeeDaoMySQL == null) {
		employeeDaoMySQL.addEmployee();
	} else {
	employeeDaoMongo.addEmployee();
	}
}
	}
====================================================



@Configuration
public class DBUtil {

	@Bean
	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("dbuser");                                  
		cpds.setPassword("dbpassword"); 

		return cpds;
	}
}
=================

sample.properties
url=192.168.12.34
userName=root
port=1234

@Configuration
@ConfigurationProperties("sample")
public class SampleConfiguration {
	
	@Value(${url})
	private String url;

	@Value(${userName})
	private String userName;
	
	@Value(${port})
	private int port;


}
===============

suppose we want different configurations based on profiles

--spring.profiles.active=prod

application.properties


application_prod.properties
url=

application_dev.properties
url=


Command Line ==> System properties ==> application.properties ==> application_profilename.properties

=====================================================================




RESTful and integrate with JPA Hibernate 

2:00 Post Lunch
======================================================

ORM ==> Object Relational Mappping

class <---> relational database table
Mapping in the form of XML or annotation
ORM performs DDL and DML operations

class mapped to table @Table
fields mapped to columns using @Column

ORM Frameworks ==> Hibernate, OpenJPA, EclipseLink, KODO, TopLink
JPA ==> Specification for ORM

Application ==> JPA ==> ORM ---> JDBC integration API ---> Database
---------------------------

ORM ==> 
	1) DataSource
		pool of database connection
	2) EntityManager
		wrapper for database connection
	3) EntityManagerFactory
	4) PeristenceContext
		environment where entities are managed


	@Configuration
	public class AppConfig {

		@Bean
		public DataSource ds() {
			.setDriverClassName(..)
			.setUrl(...)
			.setUserName(...)
		}

		@Bean
		public EntityManagerFactoryBean getEmf() {
			LocalContainerEntityManagerFactoryBean emf = ..

			emf.setJPAVendor(new HibernateJPAVendor());
			emf.setPackagesToScan("com.adobe.prj.entity");
			emf.setDataSource(ds());
		}


	}

	@Repository
	public class ProductDaoJpaImpl implements ProductDao {

		@PersistenceContext 
		EntityManager em;


		void save(Product p) {
			em.save(p);
		}

	}
====================================================================================

spring data jpa
	simplifies using ORM

	public interface EmployeeDao extends JpaRepository<Employee, String> {
		CRUD
	}

	Spring Data JPA creates implmentation @Repository class for the interface
===================================================================================




@SequenceGenerator(name = "mySeqGen", sequenceName = "mySeq", initialValue = 5, allocationSize = 100)


@GeneratedValue(strategy = GenerationType.AUTO)

select max(id) + 1

FETCH TYPE ==> LAZY 
n + 1 hits to the DB:

select * from orders; [ 1, 2, 3, 4]

select * from lineitems where order_fk = ?


If connection is lost we can't get child elements
Can't create MAster - child UI

EAGER:
use join and get order and items with 1 hit to the DB
==============================

DDL 
1) 
spring.jpa.hibernate.ddl-auto=update

1) map classes with existing tables
2) if tables are not present create table using mapping done xml / annoation
3) if required alter existing table

2)
spring.jpa.hibernate.ddl-auto=create

drop table and re-create for every of application
good for testing

3)
spring.jpa.hibernate.ddl-auto=validate

map class to existing table;
won't create tables
=======================================
 
 @SpringBootApplication
 	@EnableTransactionManagment

 Spring provides PlatformTransactionManager abstraction over varous transactions like JDBCTx, JPATransaction , JTATraantions


 	PlatformTransactionManager ==> Declaritive and Distrubuted [ 2 phase commit protocol]

 	JDBC:
 	public void transferFunds(Account first, Account two, double amt) {
 		Connection con = ...

 		try{
 			con.setAutoCommit(false);

 			...

 			con.commit();
 		} catch(SQLException ex) {
 			con.rollback();
 	}
 	}

 	Hibernate:

 	public void transferFunds(Account first, Account two, double amt) {
 		Session ses = SessionFactory.getSession();

 		try{
 			Transaction tx = ses.beginTransaction();

 			...

 			tx.commit();
 		} catch(SQLException ex) {
 			tx.rollback();
 	}
 	}
===============

with PlatformTransactionManager and @EnableTransactionManagment
	
	Atomic unit

	@Transactional
	public void transferFunds(Account first, Account two, double amt) {
 			update first
 			update second
 			insert into sometransaction table
 	}

Internally @Transactional is an Around Advice [ AOP ]



	@Transactional(rollbackFor=InsufficientBalanceException.class, noRollBackFor=IndexOutOfBoundsException.class)
	public void transferFunds(Account first, Account two, double amt) {
 			update first
 			update second
 			insert into sometransaction table
 	}


 	Optiona<Product> prd =  productDao.findById(id);

 		return prd.elseIf(new Product())
=========

PESSIMISTIC LOCK
@Transactional(isolation = Isolation.SERIALIZABLE )



@Table(name="customers")
@Entity
public class Customer {
		...

		@Version
		private int ver;


ver column in database
11

	update customer set first_name = 'a', ver = ver + 1 where email='a@adobe.com' and ver = 10;
	
First Commit wins
Default: last commit wins
=================================================


@Table(name="products")
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
	private int id;
	
	private String name;
	
	private double price;
	 
	@Column(name="qty")
	private int quantity;

class ProductDTO {
	name
	price
}

interface IProductDTO {
	String getName();
	double getPrice();
}

public interface ProductDao extends JpaRepository<Product, Integer> {
	List<Product> findByName(String name);

	List<Product> findByPriceAndQuantity(int price, int qty); // select * from products where price = ? and qty = ?
	List<Product> findByPriceOrQuantity(int price, int qty); // select * from products where price = ? or qty = ?

	// JPQL uses class and fields instead of table and columns
	@Query("select name, price from Product")
	List<Object[]> getNameAndPrice();

	@Query("select new pkg.ProductDTO(name, price) from Product")
	List<ProductDTO> getNameAndPrice();

	@Query("select  name, price  from Product")
	List<IProductDTO> getNameAndPrice();
}

public class ReportDTO {
	...
}

public interface OrderDao extends JpaRepository<Order, Integer> {
	
	@Query("select o.orderDate, c.firstName, c.email from Order o inner join customer c")
	List<ReportDTO> getReport();
}
===========================================================

NativeQuery

@Query("select name, price from products", nativeQuery=true)
List<Object[]> getNameAndPrice();
===================================================

http://localhost:8080/h2console
<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
==============================
@NotBlank(message = "{name.required}")
	private String name;

	@Min(value=10, message="Price ${validatedValue} should be more than {value}")
	private double price;
	
	
	@Min(value = 0, message="Quantity ${validatedValue} should be more than {value}")
	@Column(name="qty")
	private int quantity;
=================================================

1) Pagination
===============
2) NamedQuery
@NamedQueries({
        @NamedQuery(name = "Company.companyWithDepartmentsNamedQuery",
                query = "SELECT DISTINCT c " +
                        "FROM Company c " +
                        "LEFT JOIN FETCH c.departments " +
                        "WHERE c.id = :id"),
        @NamedQuery(name = "Company.companyWithDepartmentsAndEmployeesNamedQuery",
                query = "SELECT DISTINCT c " +
                        "FROM Company c " +
                        "LEFT JOIN FETCH c.departments as d " +
                        "LEFT JOIN FETCH d.employees " +
                        "WHERE c.id = :id"),
        @NamedQuery(name = "Company.companyWithDepartmentsAndEmployeesAndOfficesNamedQuery",
                query = "SELECT DISTINCT c " +
                        "FROM Company c " +
                        "LEFT JOIN FETCH c.departments as d " +
                        "LEFT JOIN FETCH d.employees " +
                        "LEFT JOIN FETCH d.offices " +
                        "WHERE c.id = :id"),
        @NamedQuery(name = "Company.companyWithCarsNamedQuery",
                query = "SELECT DISTINCT c " +
                        "FROM Company c " +
                        "LEFT JOIN FETCH c.cars " +
                        "WHERE c.id = :id"),
})

public class Company {


public interface CompanyDao extends JpaRepository<Company, Long> {
	Company companyWithDepartmentsAndEmployeesAndOfficesNamedQuery(Long id);
}

==================

3) EntityGraph
4) Specification
5) AOP
6) RESTful 
7) Envers
8) Flux
docker pull redis
 
## step2 - Running the container
docker run -d -p 6379:6379 --name my-redis redis


UI with NodeJS for Redis:
$ npm install -g redis-commander
$ redis-commander

9) cacheExample
Caching implementation using Redis:
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-pool2</artifactId>
</dependency>

application.properties:
spring.redis.database=0
spring.redis.port=6379
spring.redis.host=127.0.0.1
spring.redis.lettuce.pool.min-idle=5
spring.redis.lettuce.pool.max-idle=10
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-wait=1ms
spring.redis.lettuce.shutdown-timeout=100ms


@CacheConfig(cacheNames = "books")
public class SimpleBookRepository implements BookRepository {
	static List<Book> books = Arrays.asList(new Book("isbn-1234", "First Book"), new Book("isbn-4567", "Second Book"), new Book("isbn-4567", "Second Book") );
	
	@Override
	@Cacheable( key="#isbn") // books::isbn-1234: returned book will be value


==========

redis> keys *
redis> get books::isbn-4567
===================================================

Day 2
=====
	Spring annotaions at class level for lifec cycle management
	@Autowired; @Bean
	@PersistenceContext ==> Spring Data JPA


	--spring.config.location=file://./users/sample.properties 

	findBy

	@Query

	CRUD operations available in JpaRepository
==============================================================


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {
	 
	 @Modifying
	 @Query("update Customer set firstName = :fn where email = :em")
	 int updateCustomer(@Param("fn") String firstName, @Param("em") String email);
}	

"from Product"

"select p from Product p"
========

scalar values : "select name,price from Product"

Polymorphic:

"from Object"
 
"from Product"; ==> product.java ==> "products";  Mobile extends Product ==> "mobiles"; Tv extends Product ==> "tv"
=========================================================
 JpaRepository<Product, Integer> extends  PagingAndSortingRepository<T, ID>
 Page<T> findAll(Pageable pageable);


 	@Autowired
 	ProductDao productDao;

 	// Pagination with Spring DATA JPA
 	PageRequest pageable = PageRequest.of(0, 10);

 	Page<Product> productPage = productDao.findAll(pageable);

 	productPage.getPage(); 	
 	productPage.getTotalPage();

 	List<Product> products  = productPage.getContent(); 

 	====================

 		// Pagination with Spring DATA JPA
 	PageRequest pageable = PageRequest.of(0, 10, Sort.by("price").ascending());
 	Page<Product> productPage = productDao.findAll(pageable);
====================================
Entity Graph:
File --> import --> existing maven projects 

Default behaviour of ORM is
	ManyToOne --> EAGER fetching
	OneToMany --> Lazy fetching

=========

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="order_fk")
	private List<Item> items = new ArrayList<>();
====================

public class Company {
	 
	    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
	    private Set<Department> departments = new HashSet<>();

	    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
	    private Set<Car> cars = new HashSet<>();

@Entity
public class Department {
	 

	    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
	    private Set<Employee> employees = new HashSet<>();

	    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	    private Set<Office> offices = new HashSet<>();


@Entity
public class Employee {
	 
	    @OneToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "address_id")
	    private Address address;



	    findById(Company.class, 1);
	    	Company + Department + Cars + Employees + Offices + Address
===============
 select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company
        _id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    where
        company0_.id=?

    @NamedEntityGraph(name = "companyWithDepartmentsGraph",
                attributeNodes = {@NamedAttributeNode("departments")})
=========
   select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company_id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_,
        employees2_.department_id as departme5_4_3_,
        employees2_.id as id1_4_3_,
        employees2_.id as id1_4_4_,
        employees2_.address_id as address_4_4_4_,
        employees2_.department_id as departme5_4_4_,
        employees2_.name as name2_4_4_,
        employees2_.surname as surname3_4_4_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    left outer join
        employee employees2_ 
            on department1_.id=employees2_.department_id 
    where
        company0_.id=?
 @NamedEntityGraph(name = "companyWithDepartmentsAndEmployeesGraph",
                attributeNodes = {
                		@NamedAttributeNode(value = "departments", subgraph = "departmentsWithEmployees")},
                subgraphs = @NamedSubgraph(
                        name = "departmentsWithEmployees",
                        attributeNodes = @NamedAttributeNode("employees")))
=============

	  @NamedEntityGraph(name = "companyWithDepartmentsAndEmployeesAndOfficesGraph",
                attributeNodes = {@NamedAttributeNode(value = "departments", 
                subgraph = "departmentsWithEmployeesAndOffices")},
                subgraphs = @NamedSubgraph(
                        name = "departmentsWithEmployeesAndOffices",
                        attributeNodes = {@NamedAttributeNode("employees"), @NamedAttributeNode("offices")}))


                 builder.equal(root.get("birthday"), today);
===========================================

After TEA Break RESTful Web Services
===========

install Postman
====================

CriteriaAPI ==> Programmatic way of forming queries [ without SQL or jpql]

JPA ==> Specification and queries which accept Specication
===========================================================================

RESTful Web Services
====================

Representational State Transfer
A resource on server [ database, files, printers] its representation can be served to various clients in various formats [xml, json, csv, ..]

1) client-server
2) uniform uri
3) scalable
4) cachable [ Swiggy ==> caching info ==> expires]
5) stateless

REST is based on HTTP
==> URI are plural nouns ==> identify a resource [ customers, orders, products]
==> HTTP VERBS for actions [ GET, POST, PUT, PATCH, DELETE ]

1) 
GET
http://localhost:8080/api/products

to fetch all products

2) 
GET
http://localhost:8080/api/products/5

get product by id ==> 5

GET
http://localhost:8080/api/customers/me@gmail.com/orders

3) 
GET
http://localhost:8080/api/products?category=mobile
http://localhost:8080/api/products?page=2&size=10

use Request or Query Parameter for filtered data

4)
POST
http://localhost:8080/api/products

payload from the client contains product representation which needs to be added to "products"

5)
PUT
http://localhost:8080/api/products/4

payload from the client contains product representation which needs to be update product with id 4 
in "products"

6)
DELETE
http://localhost:8080/api/products/4


CRUD ==> POST GET PUT/PATCH DELETE
======================================

HTTP Header:
Accept: application/json
sent by client to server asking for json represention

content-type:text/xml
payload sent by client to server is in XML format
==================================================

Spring Boot web module provides for RESTful Web services?
	Dependency: spring-boot-starter-web
	Embedded Tomcat server ==> 200 concurrent requests
	DispatcherServlet; HandlerMapping

	By default it enables handling JSON data
	ContentNegotiationHandler Java <--> Represention
	for JSON:
		Jettison
		Jackson [ default]
		GSON
		Moxy
=====================================================

	Traditional Web Application ==> application contains server + client logic [ presentation] ==>html
	@Controller ==> View [ html / theamleaf / jsp]

	Restful Web Services ==> Represention
	@RestController

	api/products

	api/orders

	payload is order:

	{
		"total": 80130.00,
		"customer": {"email": "a@adobe.com"},
		"items": [
			{"product": {"id": 2}, "qty": 10, "amount": 130.00},
			{"product": {"id": 1}, "qty": 1, "amount": 80000.00}
		]
	}

	@NotBlank(message = "{name.required}")
	private String name;

	@Min(value=10, message="Price ${validatedValue} should be more than {value}")
	private double price;
	
	
	@Min(value = 0, message="Quantity ${validatedValue} should be more than {value}")
	@Column(name="qty")
	private int quantity;

=======================

RestController and Swagger API for RESTful documentation

AOP ==> Aspect Oriented Programming
Why?
	to handle concerns which leads to code tangling and scattering

 void transfer(Account fromAcc, Account toAcc, int amount, User user,
    Logger logger, Database database) throws Exception {
  logger.info("Transferring money...");
  
  if (!isUserAuthorised(user, fromAcc)) {
    logger.info("User has no permission.");
    throw new UnauthorisedUserException();
  }
  
  if (fromAcc.getBalance() < amount) {
    logger.info("Insufficient funds.");
    throw new InsufficientFundsException();
  }

  fromAcc.withdraw(amount);
  logger.info("wihdraw done")
  toAcc.deposit(amount);
  logger.info("deposit done")

  database.commitChanges();  // Atomic operation.

  logger.info("Transaction successful.");
}

AOP:
	Aspect ==> A concern which can be used along with main logic, but its not main logic
				==> Logging; Security; Profile; Transaction [ @Transactional ]

	JoinPoint ==> Where an aspect can be weaved [ method or exceptions]

	PointCut ==> selected joinpoint

	Advice ==> Before; After; Around; AfterReturning; AfterThrowing

	public @ResponseBody String updateProduct(@PathVariable("id") int id, @RequestBody Product p) {
		service.updateProduct(id, p.getQuantity());
		// After
		return "product updated";
	}

	ByteCode Instrumentaiton libraries [asm] ==> CGLIB or JavaAssist generates Proxies 
=========================================================


	public void doTransaction(Class<?> clazz) {

	}

	@Around
	public void doTransaction(Method<?> m) {
		if(m.getAnnoation(Transaction.class) != null) {
			try {
				Transaction tx = ...
				actual code
				tx.commit();
			} catch(Throwable ex) {
				tx.rollback();
			}
		}
	}
=========================

for UnitTesting:

Commit all RestTemplate code
===============================

Unit Testing ==> Testing Controller classes
============================================

Controller ==> Service ==> DAO ==> Database Connection

to Test Controller we need to mock Service
+++++++++++++++++++++++++++++++++++++++++++++

Mockito; EasyMock; JMock ==> Mocking Framework
===========================================
 
Start Docker Desktop

docker run -d --name=prometheus -p 9090:9090 -v C:\prometheus\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml


docker run redis --name=myredis 
=========================================

Future and CompletableFuture

Future's get(); blocking

CompletableFuture callbacks

cf.thenAsync(task).thenAsyc(task2).thenSyn(task3)
========

Thread pool by Jetty / Tomcat ==> 200 threads ==> to handle Request

Another set of thread pool of size 3 [ Executor/ ExcectorService]

	Service code ==> to make HTTP calls to another REST endpoint using these 3 threads form pool
		employeeName; 
		employeePhone; 
		employeeAddress;

	Controller which needs these 3 data:
		CompletableFuture

	Controller is still Synchnrouns
===================
	
	BlockingQueue 
1 client ==> 1 thread form tomcat

Trip Aggregator MMT; HolidayIQ
	Thread ==> Indigo; SpiceJET; Go;
===========================================================

Reactive Programming
Cache Redis
ProtoBuf [ Micro Service]
Security
==================================================

Day 3
======

PersonDTO or Person VO [ carriers of data in object way but it's not an enitity]

com.adbobe.prj.dto;

class ReportDTO {
	String email;
	Date orderDate;
	double total;	
}


@Query("select new com.adbobe.prj.dto.ReportDTO(c.email,o.orderDate,o.total) from Order o inner join Customer c")
List<ReportDTO> getReport();

	instead of
@Query("select  c.email,o.orderDate,o.total  from Order o inner join Customer c")
List<Object[]> getReport();
======================================================================

Async Service ==> we dedicate pool of threads for execution 

	Aggregator application ==> MakeMyTrip, HoildayIQ

	A request from client uses 1 Thread of Tomcat/jetty or any web container ;Aprox 200 threads ]
	Now over service will internally schedlue 3 threads to make request [ Indigo, Spice, Jet]
	from another set of thread pool [ Executor Framework]

	Without above apporach:
		1 ==> get quote from Indigo http://indigo.com
		then
		2 ==> get quote from Spice 
		then
		3 ==> get quote Jet

	we collect responses from these 3 and send it to client ==> CompletableFuture / Future
===================================================

Reactive Programming using Spring WebFLux instead of Spring MVC
Netty instead of Tomcat
======================================================
 BJ: ==> 9972058787

 1) using docker-compose.yaml
 $ docker-compose up

 2) another terminal
 	
 	docker exec -it mongo bash
 	
 	> mongo admin --username admin --password password

 	> db.createCollection("itemCapped", { capped : true, size : 5242880, max : 50 } )



http://localhost:8090/stream ==> @Tailable
http://localhost:8090/items
========================================================================

 @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> {
            Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
            configurationMap.put("cache1", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(20)));  
            configurationMap.put("cache2", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)));     
            builder.withInitialCacheConfigurations(configurationMap);
        };
    }
==================================================================

spring-data-redis RedisCacheManager
===================================

npm i redis-commander

$ redis-commander
connect:
http://locahost:8081

redis> keys *
==================

ProtoBuf

Account ==> entity package ==> JPA @Entity

	string --->

	FileOutputStream f

	FileWriter out
====================================================

Spring Security
===============

	Security ==> Filters provided to be exectued in servlet Web Container
				 Filters which run within Spring Container

	==============================
	login logout ==> user and generated password
	application.properties ==> username and password

	InMemory @EnableWebSecurity
	JDBCAuthentication ==> users and authorities
======================================================
	Method Level Security
====================

RESTful Web Services ==> Stateless

Mobile; MicroService <==> MicroService

JWT
=============================
	
	PRODUCTS API INVENTORY
	products
		All the products

	cart
		products added into your cart
		checkout 







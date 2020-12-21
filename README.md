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
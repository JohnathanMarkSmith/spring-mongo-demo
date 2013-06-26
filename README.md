###  Using Spring Data Mongo with Spring's Java Configuration (JavaConfig) style with Maven, JUnit, Log4J


In this example I am going to show you how to post data to a MongoDB database using Spring Data Mongo
in Java using Spring, Spring Java Configuration, Maven and Log4J.


### Spring Java Configuration

Let's take a quick look at the Spring Java Configuration file that the project is going to be using

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {MongoDBApp.class})
    @PropertySource("classpath:application.properties")
    public class MongoConfiguration extends AbstractMongoConfiguration
    {


        @Override
        protected String getDatabaseName() {
            return "demo";
        }

        @Override
        public Mongo mongo() throws Exception {
            return new Mongo();
        }

        @Override
        protected String getMappingBasePackage() {
            return "com.johnathanmarksmith.mongodb.example.domain";
        }

    }

 As you can see from the above code the trick to all this is to use @EnableMongoRepositories and to
 extend your class from AbstractMongoConfiguration.

### The Person Object

Now we are going to take a look at the object that we are going to be inserting into the database.

        @Document
        public class Person {

        @Id
        private String personId;

        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(final String personId) {
            this.personId = personId;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(final int age) {
            this.age = age;
        }
        @Override
        public String toString() {
            return "Person [id=" + personId + ", name=" + name
                    + ", age=" + age +  "]";
        }

    }


You see we use the @Document and the @Id.

### The Repository

Now its time to see the source code in the repository

    @Repository
    public class PersonRepository {

        static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

        @Autowired
        MongoTemplate mongoTemplate;

        public void countUnderAge() {
            List<Person> results = null;

            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria = criteria.and("age").lte(21);

            query.addCriteria(criteria);
            results = mongoTemplate.find(query, Person.class);

            logger.info("Total number of under age in database: {}", results.size());
        }

        /**
         * This will count how many Person Objects I have
         */
        public void countAllPersons() {
            List<Person> results = mongoTemplate.findAll(Person.class);
            logger.info("Total number in database: {}", results.size());
        }

        /**
         * This will install a new Person object with my
         * name and random age
         */
        public void insertPersonWithNameJohnathanAndRandomAge() {

            double age = Math.ceil(Math.random() * 100);
            Person p = new Person("Johnathan", (int) age);

            mongoTemplate.insert(p);
        }

        /**
         * this will create a {@link Person} collection if the collection does not already exists
         */
        public void createPersonCollection() {
            if (!mongoTemplate.collectionExists(Person.class)) {
                mongoTemplate.createCollection(Person.class);
            }
        }

        /**
         * this will drop the {@link Person} collection if the collection does already exists
         */
        public void dropPersonCollection() {
            if (mongoTemplate.collectionExists(Person.class)) {
                mongoTemplate.dropCollection(Person.class);
            }
        }
    }


## The Main Class

Time for the main class now.. are you ready for all this code??

        public static void main(String[] args) {
            logger.info("MongoDemo application");

            ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);

            PersonRepository personRepository = context.getBean(PersonRepository.class);

            // cleanup person collection before insertion
            personRepository.dropPersonCollection();

            //create person collection
            personRepository.createPersonCollection();

            for (int i = 0; i < 10000; i++) {
                personRepository.insertPersonWithNameJohnathanAndRandomAge();
            }

            personRepository.countAllPersons();


            /***
             *
             * Added Under Age Test For someone to see
             *
             */
            personRepository.countUnderAge();


            logger.info("MongoDemo application");
        }
    }



You can see from the above code how easy it is to use Spring Data Mongo to post data to a MongoDB.


### Download and Run

You can checkout the project from github.

    git clone git@github.com:JohnathanMarkSmith/spring-mongo-demo.git
    cd spring-mongo-demo
    mvn clean package
    mvn exec:java

Please keep in mind to be able to run this demo application start your MongoDB instance, Yes you need MongoDB.

If you have any questions please email me at john@johnathanmarksmith.com

For a MongoDB guide see http://www.mongodb.org/display/DOCS/Quickstart

Thanks, Johnathan Mark Smith

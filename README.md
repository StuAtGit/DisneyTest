## Dog Breed API*
A PoC service for my Disney interview.


## Dependencies

This project uses Maven for builds.
You need Java 8 installed.

## Building

```
$ mvn package
```

## Running

```
$ java -jar target/dog-breed-api-0.0.1-SNAPSHOT.jar
```

## Resources:

```
GET /api/ -> list all dogs (TODO: eliminate trailing slash!)
GET /api/dogs/[breed] -> list all dogs with the given breed
GET /api/dogs -> list all dogs grouped by breed
GET /api/dog/[dogId] -> get dog with [id]
POST /api/upvote/[dogId]/user/[userId] -> cast one upvote for dog [dogId] using [userId]
POST /api/upvote/[dogId]/user/[userId] -> cast one downvote for dog [dogId] using [userId]
```

##Assumptions:
 - userIds can be authenticated and authorized via some other service/library.
 - I do build out some dummy test users ids ( 1,2,3,4,etc )

##Known issues:
- the voting mechanism is logically correct (a given userId can only
  vote for a particular dog once => true). 
  However, it's not actually correct:
  a given userId can only vote once!!. 
  IOW, this statement is false: A given userId can vote for every
  dog at least once. Not true.
  I do realize these two constraints where what was meant, and
  I meant to add the ability to 
  track which dog(s) a userId voted for, but ran out of time.
  
- not enough unit tests! (ran out of time). 

### Notes on libraries, etc.

#### Spring Boot

Yes, this is (pretty much) my first time with spring boot.
(Not my first time with Spring, though) 
Why? Because the motivations that resulted in the creation 
of spring boot, motivated me off of spring for 
everything except DI before Spring Boot came out, and I haven't taken a look at it.
Examples of why I migrated off of using Spring, and, also, reasons given for the 
creation of spring boot:

- Code over Configuration
    - note that I even take this to preferring
    java language facilities over annotations, when 
    possible, of course, without too much trouble.
- Embedded Servers for easy local testing & debug
    - I typically prefer wrappers around Embedded Jetty, 
    though, since I feel like, in the *embedded*
    server space, Jetty has seen more use in prod,
    whereas *embedded* Tomcat isn't necessarily as widely used
    (although I've found standalone Tomcat w/ WARs is very common, obviously). But
    if I work at a place comfortable with Embedded Tomcat - works for me!

What's my typical stack?

- Spring for your basic wiring up of dependencies. Note,
however, that outside of application startup, I generally
prefer DI via explicit constructor arguments. There are plenty 
of good views on each side, and this is my preference.

- Something like SparkJava:
http://sparkjava.com/
Although, I can imagine someone being a little hesistant about it 
due to the paucity of maintainers & corporate backing. But it has been 
great for me! Even in prod, under fairly high load. It's really just a nice
wrapper around Embedded Jetty that makes things like wiring up your resources
to methods, TLS, thread pool configuration, etc, nice and easy.

- JDBI  
A.K.A What's the ORM you're using???  
http://jdbi.org/  
OK, so I realize I may be liberally interpreting the rules here, namely,
one one side:
    - The coding assignment will be a Java, JPA, Hibernate and Spring/Spring Boot experience
    
    On the other:
    - Need to use Hibernate or any ORM,
    
So, obviously, I went with the "any ORM" part :) 
 
Why? JDBI is just really nice, in my opinion. Note that I'm very
much on the "Keep Your ORM Thin", camp. But, on the other hand,
no, I don't like scattering SQL throughout my code. On the third 
side of things, no, I don't like debugging through a dense, complicated, 
opaque translation when things go wrong or performance goes haywire
with your SQL. There's been so much passion about ORMs in the past,
no need for me to re-hash here.   
Basically, yes, I like ORMs, but I like thin (and transparently thread-safe!) ones.

- DropWizard Metrics
Easy JMX integration, modular enough that you can usually hook it into
whatever X monitoring framework you happen to use. Method annotations, if you 
really want them (usually I end up with something a little more granular, though
,and I want better named metrics, more control, etc). 'Nuf Said.

*I wrote this in an IDE, on a kinda small window without much in the way of 
advanced spelling or grammar checking. Apologies for any spelling & grammar issues.


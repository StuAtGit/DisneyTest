## Dog Breed API*
A PoC service for my Disney interview.

### Notes on libraries, etc.

#### Spring Boot

Yes, this is (pretty much) my first time with spring boot.
(Not my first time with Spring, though) 
Why? Because the motivations that resulted in the creation 
of spring boot, motivated me off of spring for 
everything except DI before Spring Boot came out, and then
they fixed the issues with spring boot. Examples of things that 
spring finally came around too:

- Code over Configuration
    - note that I even take this to preferring
    java language facilities over annotations, when 
    possible, of course, without too much trouble.
- Embedded Servers for easy local testing & debug
    - I typically prefer wrappers around Embedded Jetty, 
    though, since I feel like, in the *embedded*
    server space, Jetty has seen more use in prod,
    whereas *embedded* Tomcat isn't necessarily as widely used
    (although I've found standalone Tomcat w/ WARs is very common, obviously)

What's my typical stack?

- Spring for your basic wiring up of dependencies. Note,
howver, that outside of application startup, I generally
prefer DI via explicit constructor arguments. There are plenty 
of good views on each side, and this is my preference.

- Something like SparkJava:
http://sparkjava.com/
Although, I can imagine someone being a little hesistant about it 
due to the paucity of maintainers & corporate backing. Buts it's been 
great for me!

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
no, I don't like scattering SQL throughout my code. On the other 
side of things, no, I don't like debugging through a dense, complicated, 
opaque translation when things go wrong or performance goes haywire
with your SQL. There's been so much passion about ORMs in the past,
no need for me to re-hash here.   
Basically, yes, I like ORMs, but I like thin (and transparently thread-safe!) ones.

*I wrote this in an IDE, on a kinda small window without much in the way of 
advanced spelling or grammar checking. Apologies for any spelling & grammar issues.


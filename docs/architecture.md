# Morty Architecture

## Components

Here is a summary of the most important components that Morty is comprised of.

- Spring Boot v3
- Vaadin 24
- Spring Data JPA
- PostgreSQL


## Design

Morty as a service is monolithic in nature and will always be so, however to offset the common and historical disadvantages 
of monolithic services there are the following design decisions:


### Onion Layered Architecture

The service is split into three layers which all take to do with a core responsibility:

- Application layer: is focussed on presentation and interfacing with the users
- Domain layer: is focussed on business logic
- Infra layer: concentrates on persistence of data

Each layer cannot use entities or classes of another layer, entities must be mapped to the corresponding entity in each layer.
All communication across layers is defined as contracts (Use Cases), of which each layer has to implement.

This roughly follows the guidelines here - https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)

### Testing

Morty will use Unit and Integration testing where suitable to minimise regression.

### Domain driven design (DDD)

Morty has a clear list of defined entities and how they are represented and mutated. Entities should be immutable at certain stages to prevent 
side affects and force clear calls for operations such as Create, Read, Update and Delete.

## Where stuff lives

**Infra**
- [Database Entities](..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fkapango%2Finfra%2Fentity)
- [Repos and Queries](..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fkapango%2Finfra%2Frepository)
- [Internal Configs](..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fkapango%2Finfra%2Fconfig)

**Config / Misc**
- [Config files](..%2Fsrc%2Fmain%2Fresources%2Fconfig)

**Application**
- [REST controllers](..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fkapango%2Fapplication%2Fcontroller)
- [Vaadin views](..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fkapango%2Fapplication%2Fview)
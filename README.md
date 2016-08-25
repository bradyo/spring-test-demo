# Testing Demo

This code demonstrates some different ways of black box functional and mock interaction style testing.

Black box testing focuses on testing behaviors for the public interfaces of an object by verifying 
only observable input and output behaviors.

Mock testing focuses on testing interactions between an object and it's direct collaborators by verifying
method calls directly.


## Link Repository Examples

This example shows both fully mocked unit tests and black box integration tests. 

See:
- `SimpleLinkRepositoryMockTest` (mock)
- `CachedLinkRepositoryMockTest` (mock)
- `SimpleLinkRepositoryIntegrationTest` (black box)
- `CachedLinkRepositoryIntegrationTest` (black box)

Mock tests assert on calls made in the code by dependencies. For example, in `SimpleLinkRepositoryMockTest`
you see that calls to the underlying `entityManager` are verified. Similarly in `SimpleLinkRepositoryMockTest`,
all calls made to the underlying `entityManager` and `cache` are verified. The mock tests verify specific
lines of code exist and are called with the right parameters. Because they know and depend on implementation 
details to pass, they are not considered black-box tests. 

Black-box tests only know about the public interface of the system under test and do not verify specific 
lines of code have been called. For example `SimpleLinkRepositoryIntegrationTest` and 
`CachedLinkRepositoryIntegrationTest` test that a method behaves as expected using only the
input/output of the public interface. Testing that a call is cache-able using only the input and output
of an interface is more difficult, since you cannot assert on weather or not a value is pulled from the 
cache directly. 

Instead, you need to ask the question: "What is the behavior of the get() method am I verifying?". In this case
of a cache-able method, what we are actually testing is that subsequent gets must perform better than first get.

## External Service Error Examples

- mock external service calls to simulate errors (http errors, timeout, remote server errors)
- create stub implementations with different behaviors to allow black-box testing
- can record input and output of remote server for tests


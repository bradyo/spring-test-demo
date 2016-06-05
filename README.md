# Black Box Testing Demo

This code demonstrates some differences between black box testing and mock interaction testing.

Mock testing focuses on testing interactions between an object and it's direct collaborators by verifying
method calls directly.

Black box testing focuses on testing behaviors for the public interfaces of an object by verifying inputs
and outputs only.

In both cases, collaborators are exposed to the consuming code via the constructor. This allows 
all the dependencies to be injected by the consumer of the class. 


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

// todo

- mock external service calls to simulate errors
- create stub implementations with different behaviors to allow black-box testing


## Side Note on Encapsulating Dependencies

Because black-box testing tests behaviors of the public interface of a class, arguments injected
through the public constructor are part of that interface. 

Having an object construct dependencies itself will eliminate it from the public interface of the
class, thereby encapsulating the implementation details within the class. 

For more information see the following:

- Encapsulation
- Information Hiding
- Dependency Elimination Principal

Such encapsulation of dependencies makes mock based interaction testing very difficult since
encapsulated dependencies can no longer be mocked and injected. However, it makes very little difference
for black-box testing since black-box tests are affected only by the public interface and not the 
implementation details of a class.

Combining dependency encapsulation and black box testing can create code and tests that
are more easily refactored. A class can be refactored significantly without changing the public
interface and tests will not need to be changed.


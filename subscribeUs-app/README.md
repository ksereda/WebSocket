### Sunscribe-Us app

This is Reactive application (Spring WebFlux in functional style) + WebSocket

___

In this example we save user email to MongoDB and then return this email to UI.

1) First you need to start app

2) Then you need open html file in browser

___

#### Flow

`WebSockets` are handled by implementing `WebSocketHandler`. 

The `handler` is provided with a `WebSocketSession` every time a connection is established. 

A `WebSocketSession` represents the connection made by a single browser. 

It has 2 Flux streams associated with it, the `receive()` stream for incoming messages and the `send()` stream outgoing messages.


___

#### Configure WebFlux to Handle WebSockets

To enable `WebSocket` connections through `WebFlux`, a `SimpleUrlHandlerMapping` can be used. 

It maps a `WebSocket URL` to an implementation of a `WebSocketHandler`. 

One thing to note is the explicit order of the `HandlerMapping`, omitting the order causes the mapping to clash with the `RouterFunction` configuration that deals with HTTP requests.

Peergreen iPOJO Performance Handler
-------------------------------------

Output, in a CSV formatted file, startup metrics gathered from iPOJO components' startup.
The handler monitors constructor and lifecycle methods (`@Validate`)

Sample:

    #factory-bundle-id;factory-name;instance-name;class-name;method-name;method-type;begin;end;elapsed
    31;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentFactoryCompleter;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentFactoryCompleter-0;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentFactoryCompleter;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentFactoryCompleter(...);constructor;1378193702563;1378193702563;0
    31;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentInstanceCompleter;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentInstanceCompleter-0;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentInstanceCompleter;org.ow2.shelbie.commands.ipojo.internal.completer.ComponentInstanceCompleter(...);constructor;1378193702582;1378193702582;0
    33;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter-0;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter(...);constructor;1378193702767;1378193702768;1
    33;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter-1;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter(...);constructor;1378193702778;1378193702778;0
    33;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter-2;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter;org.ow2.shelbie.commands.config.internal.completer.ConfigurationCompleter(...);constructor;1378193702788;1378193702788;0

Setup
--------

Add the following bundles in the kernel pom.xml (could be added in `deploy/`).

* `mvn:org.apache.felix:org.apache.felix.eventadmin:1.3.2`
* `mvn:com.peergreen.ipojo:performance-handler:1.0.0-SNAPSHOT`

We will use the auto-attached primitive handler feature to ask iPOJO to add a new required handler to all created factories.
Simply add the following system property to activate that feature:

    -Dorg.apache.felix.ipojo.handler.auto.primitive=com.peergreen.performance:tracker

Next ?
---------

* Introduce a @PerformanceMonitor annotation to activate the handler
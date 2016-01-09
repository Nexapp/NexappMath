# Nexapp Math
Nexapp's math library.

## Provided by the library
### Value Objects
**Angle**: degrees or radians, plus some useful methods <br />
**Duration**: from nanoseconds to centuries, allows conversion <br />
**Percentage**: handle percentage from its two forms: 83% (ratio) or 0.65 (fraction) <br />
**Point**: cartesian or polar, plus some useful methods

### Functions
**Line**:  in a 2D plane (mx + b), with many useful methods

### Shapes
**Rectangle**: easy instantiation. Manages: corners, center point, dimensions and rectangle intersections.

## Roadmap
###Value Objects
Volume (litres, millilitres, pint, gallons, tablespoon, teaspoon, etc.) <br />
Unit of length (km, meters, miles, etc.) <br />
Mass (grams, kg, pounds, ounces, tonnes, etc.)

### Shapes
Triangle <br />
Circle <br />
Ellipse

### Functions
Segment <br />
Quadratic function (2nd degree) <br />
Cubic function (3rd degree) <br />
Quartic function (4th degree)

### Bezier curves
Quadratic bezier curve (2nd degree) <br />
Cubic bezier curve (3rd degree) <br />
Spline

### Intersections
Between shapes <br />
Between functions

## How to use it
At the moment, this project is hosted on GitHub and not on Maven Central.

In your `pom.xml`, you must add the GitHub repository as follows:
```
<repositories>
	<repository>
		<id>nexapp-math-mvn-repo</id>
		<url>https://raw.github.com/nexapp/nexappmath/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>
```

Then, you simply add the dependency as follows:
```
<dependency>
	<groupId>ca.nexapp</groupId>
	<artifactId>math</artifactId>
	<version>0.0.1</version>
</dependency>
```
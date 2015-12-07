# Nexapp Math
Nexapp's math library.

## Provided by the library
### Value Objects
**Angle**: degrees or radians, plus some useful methods <br />
**Duration**: from nanoseconds to centuries, allows conversion <br />
**Percentage**: handle percentage from its two forms: 83% (ratio) or 0.65 (fraction) <br />
**Point**: cartesian or polar, plus some useful methods <br />

## Roadmap
### Shapes
Rectangle <br />
Triangle <br />
Circle <br />
Ellipse

### Equations
Line <br />
Segment <br />
Quadratic function (2nd degree) <br />
Cubic function (3rd degree) <br />
Quartic function (4th degree)

### Bezier curves
Quadratic bezier curve (2nd degree) <br />
Cubic bezier curve (3rd degree) <br />
Spline

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

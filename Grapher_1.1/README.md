Currently in BETA.
-
When completed, this grapher should be able graph both functions and relations.
This version utilizes of derivatives and Euler's Method to generate the curve.

A few Current Issues:
-
-A complete graph can only be generated if both the curve and its derivative are continuous over the over the given domain and range. Various glitches are seen around points for which the derivative is unbounded or the function does not exist.

-It is sometimes very expensive to look for a initial point to execute Euler's Method along the curve.

-Euler's Method can only generate parts of a curve that is not continuous. For instance, the hyperbola x^2 -y^2 =1 is itself "two separate continuous curves" x = sqrt(1+y^2) and x= -sqrt(1+y^2). Finding more points to which to generate the second curve can be fairly expensive as previously mentioned.

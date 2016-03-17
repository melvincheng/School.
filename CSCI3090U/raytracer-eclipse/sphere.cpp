//
//  Framework for a raytracer
//  File: sphere.cpp
//
//  Created for the Computer Science course "Introduction Computer Graphics"
//  taught at the University of Groningen by Tobias Isenberg.
//
//  Authors:
//    Maarten Everts
//    Jasper van de Gronde
//
//  This framework is inspired by and uses code of the raytracer framework of 
//  Bert Freudenberg that can be found at
//  http://isgwww.cs.uni-magdeburg.de/graphik/lehre/cg2/projekt/rtprojekt.html 
//

#include "sphere.h"
#include <iostream>
#include <math.h>

/************************** Sphere **********************************/

Hit Sphere::intersect(const Ray &ray)
{
    double t = 0.0;
    double A = ray.D.dot(ray.D);
    double B = 2.0 * ray.D.dot(ray.O - position);
    double C = (ray.O - position).dot(ray.O - position) - (r * r);

    double discriminant = B * B - 4.0 * A * C;
    if(discriminant < 0.0){
        return Hit::NO_HIT();
    }

    double plusRoot = (-1.0 * B + sqrt(discriminant))/(2.0 * A);
    double minusRoot = (-1.0 * B - sqrt(discriminant))/(2.0 * A);

    if((plusRoot < minusRoot) && (plusRoot >= 0.0)){
        t = plusRoot;
    }else if((minusRoot < plusRoot) && (minusRoot >= 0.0)){
        t = minusRoot;
    }else{
        return Hit::NO_HIT();
    }

    Vector N = ray.at(t) - position;
    N.normalize();
    return Hit(t,N);
}

//
//  Framework for a raytracer
//  File: scene.cpp
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

#include "scene.h"
#include "material.h"
#include <iostream>

Color Scene::trace(const Ray &ray, int count, double etaIn)
{
    bool shadowTrace = true;
    bool reflectTrace = true;
    bool refractTrace = true;
    bool diffuseTrace = true;
    bool specularTrace = true;
    bool ambientTrace = true;
    // Find hit object and distance
    Hit min_hit(std::numeric_limits<double>::infinity(),Vector());
    Object *obj = NULL;
    for (unsigned int i = 0; i < objects.size(); ++i) {
        Hit hit(objects[i]->intersect(ray));
        if (hit.t<min_hit.t) {
            min_hit = hit;
            obj = objects[i];
        }
    }


    // No hit? Return background color.
    if (!obj) return Color(0.0, 0.0, 0.0);

    Material *material = obj->material;            //the hit objects material
    Point hit = ray.at(min_hit.t);                 //the hit point
    Vector N = min_hit.N;                          //the normal at hit point
    Vector V = -ray.D;                             //the view vector

    Point hit_jiggle;
    Color color, ambient, diffuse, specular, totalDiffuse, totalSpecular, reflectPortion, refractPortion;
    Triple R;
    Vector L;
    Vector tempN = N;
    if(count!=10 && refractTrace ){
        if(material->refract > 0.0){
            if(ray.D.dot(N) > 0.0){
                tempN = -N;
            }

            double etaOut = material->eta;
            if(etaIn == etaOut){
                etaOut = 1.0;
            }

            double check = 1 - ((pow(etaIn,2)*(1 - (pow(ray.D.dot(tempN),2))))/pow(etaOut,2));
            if(check >= 0.0){
                Vector t = ((etaIn * (ray.D - tempN * (ray.D.dot(tempN))))/etaOut) - (tempN * sqrt(check));
                t.normalize();
                Ray refractRay(hit, t);
                hit_jiggle = refractRay.at(pow(2,-32));
                refractRay = Ray(hit_jiggle,t);
                refractPortion = trace(refractRay, ++count, etaOut) * material->refract;
            }
        }
    }

    if(count!=10 && reflectTrace){
        Ray reflectRay(hit, (ray.D - 2 * (ray.D.dot(N)) * N).normalized());
        hit_jiggle = reflectRay.at(pow(2,-32));
        reflectRay = Ray(hit_jiggle, (ray.D - 2 * (ray.D.dot(N)) * N).normalized());

        reflectPortion = trace(reflectRay, count++) * material->reflect;
    }

    // Diffusion and specular calculations
    for(int i = 0;i<lights.size();i++){

        if(diffuseTrace){
            L = lights[i]->position - hit;
            L.normalize();
            diffuse = material->kd * material->color * lights[i]->color * max(0.0,L.dot(N));
        }
        if(specularTrace){
            R = -lights[i]->position + 2 * (lights[i]->position.dot(N)) * N;
            R.normalize();
            specular = material->ks * lights[i]->color * pow(max(0.0,R.dot(V)),material->n);
        }
        // Find if in shadow
        if(shadowTrace){
            Ray shadowRay(hit, L);
            hit_jiggle = shadowRay.at(pow(2,-32));
            shadowRay = Ray(hit_jiggle,L);
            for (unsigned int i = 0; i < objects.size(); ++i) {
                Hit shadowHit(objects[i]->intersect(shadowRay));
                if(shadowHit.t>0.0){
                    diffuse = Color(0.0,0.0,0.0);
                    specular = Color(0.0,0.0,0.0);
                    break;
                }
            }
        }
        totalDiffuse += diffuse;
        totalSpecular += specular;
    }
    if(ambientTrace){
        ambient = material->ka * material->color;
    }else{
        ambient = material->color;
    }
    color = ambient + totalDiffuse + totalSpecular + reflectPortion + refractPortion;

    return color;
}

void Scene::render(Image &img)
{
    int w = img.width();
    int h = img.height();
    for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
            Point pixel(x, h-1-y, 0);
            Ray ray(eye, (pixel-eye).normalized());
            Color col = trace(ray);
            col.clamp();
            img(x,y) = col;
        }
    }
}

void Scene::addObject(Object *o)
{
    objects.push_back(o);
}

void Scene::addLight(Light *l)
{
    lights.push_back(l);
}

void Scene::setEye(Triple e)
{
    eye = e;
}

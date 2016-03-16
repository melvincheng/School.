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

Color Scene::trace(const Ray &ray)
{
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

    Triple R;
    Vector L;
    Hit shadowMinHit(std::numeric_limits<double>::infinity(),Vector());
    
    Color color, ambient, diffuse, specular, totalDiffuse, totalSpecular;
    
    ambient = material->ka * material->color;



    // Diffusion and specular calculations
    for(int i = 0;i<lights.size();i++){
        L = lights[i]->position - N;
        L.normalize();
        R = -lights[i]->position + 2 * (lights[i]->position.dot(N)) * N;
        R.normalize();
        diffuse = material->kd * material->color * lights[i]->color * max(0.0,L.dot(N));
        specular = material->ks * lights[i]->color * pow(max(0.0,R.dot(V)),material->n);

        // Find if in shadow
        Ray shadowRay(hit, (lights[i]->position - hit).normalized());
        for (unsigned int i = 0; i < objects.size(); ++i) {
            Hit shadowHit(objects[i]->intersect(shadowRay));
            if(shadowHit.t<min_hit.t && obj != objects[i]){
                diffuse = Color(0.0,0.0,0.0);
                specular = Color(0.0,0.0,0.0);
            }
        }
        totalDiffuse += diffuse;
        totalSpecular += specular;
        // Reflection calculations
        Color reflectColor, reflectTotalDiffuse, reflectTotalSpecular;
        Object *reflectObj = NULL;
        Hit reflectMinHit(std::numeric_limits<double>::infinity(),Vector());
        Ray reflectRay(hit, ray.D - 2 * (ray.D.dot(N)) * N);
        for (unsigned int i = 0; i < objects.size(); ++i) {
            Hit reflectHit(objects[i]->intersect(reflectRay));
            if(reflectHit.t < reflectMinHit.t && reflectHit.t > 0.0){
                reflectObj = objects[i];
                reflectMinHit.t = reflectHit.t;
            }
        }
        if(reflectObj){
            Material *reflectMaterial = reflectObj->material;
                for(int i = 0;i<lights.size();i++){
                    L = lights[i]->position - reflectMinHit.N;
                    L.normalize();
                    R = -lights[i]->position + 2 * (lights[i]->position.dot(reflectMinHit.N)) * reflectMinHit.N;
                    R.normalize();
                    diffuse = reflectMaterial->kd * reflectMaterial->color * lights[i]->color * max(0.0,L.dot(reflectMinHit.N));
                    specular = reflectMaterial->ks * lights[i]->color * pow(max(0.0,R.dot(-reflectRay.D)),reflectMaterial->n);
                    reflectTotalDiffuse += diffuse;
                    reflectTotalSpecular += specular;
                }
                reflectColor = (ambient + reflectTotalDiffuse + reflectTotalSpecular);
                totalSpecular += reflectColor * material->reflect;
        }
    }

    


    color = ambient + totalDiffuse + totalSpecular;

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

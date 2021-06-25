/*
 * Original shader from: https://www.shadertoy.com/view/Ns23z1
 */

#ifdef GL_ES
precision mediump float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //
//Box signed distance
float box( vec3 p, vec3 b )
{
  vec3 q = abs(p) - b;
  return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}

//Distance mapping
float map(vec3 p){
    float d = 1000.;
    vec3 q = p;

    //Here we compute the cell coordinates + index per cell
    float size = 2.;
    vec3 iq = floor((p+size/2.)/size);
    q = mod(q+size/2.,size)-size/2.;

    //Some noisy pattern
    float n=0.;
    n+=0.5*sin(0.8*iq.x+1.1*iTime)*sin(0.71*iq.y+0.2*iTime+0.4)*sin(0.85*iq.z+0.3*iTime+1.4);
    n+=0.5*sin(0.5*iq.x+1.1*iTime+1.5)*sin(0.3*iq.y+1.3*iTime+1.7)*sin(0.1*iq.z+0.3*iTime+2.4);
    n = 1.-smoothstep(0.,0.2,n+0.1);


    //Distance to box in a cell + space clamp (tweak box distance to raymarch slow)
    d = 0.25*box(q,0.9*vec3(n));
    d = max(d,box(p,vec3(9.)));
    return d;
}

vec3 normal(vec3 p){
    vec2 e = 0.001*vec2(1.,0.);float d = map(p);
    return normalize(vec3(map(p+e.xyy)-d,map(p+e.yxy)-d,map(p+e.yyx)-d));
}

//Raymarch (and shadow march) routines
const float FAR = 10000.;
float raytrace(vec3 ro,vec3 rd){
    float t = 0.;
    for(int i=0;i<200;i++){
        float d = map(ro+rd*t);
        if(abs(d)<0.001){
            return t;
        }
        t+=d;
    }

    return FAR;
}

float shadow(vec3 ro,vec3 rd){
    float t = 0.;
    for(int i=0;i<200;i++){
        float d = map(ro+rd*t);
        if(abs(d)<0.001){
            return 0.;
        }
        t+=d;
    }

    return 1.;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = -1. + 2. * fragCoord/iResolution.xy;;
    uv.x*=iResolution.x/iResolution.y;

    vec3 col = vec3(0.);


    float f =0.5*sin(0.5*iTime)-0.5;

    //Ortho camera
    vec3 ro= 5.*vec3(3.*sin(f),2.,3.*cos(f));
    vec3 rd = -normalize(ro);
    vec3 up = vec3(0.,1.,0.);vec3 fw =rd;vec3 ri = cross(fw,up); up = cross(ri,fw);
    ro=ro+20.*(uv.x*ri+uv.y*up);

    float t = raytrace(ro,rd);

    //Background
    col = vec3(1.,0.9,0.8)*(0.4-0.3*uv.y);
    vec3 lightdir = normalize(vec3(1.,2.,1.));

    if(t<FAR){

        //Simple lighting (dot, shadow and height based)
        vec3 pos = ro+rd*t;
        vec3 n = normal(pos);
        float sha = shadow(pos+0.01*n,lightdir);

        vec3 lin = max(dot(n,lightdir),0.)*vec3(1.,0.95,0.85)*(0.+1.*sha);
        lin += 1.1*vec3(0.1,0.05,0.0)*(1.5+0.1*pos.y);


        col = lin;

    }

    fragColor = vec4(col,1.0);
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}
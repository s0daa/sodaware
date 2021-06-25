/*
 * Original shader from: https://www.shadertoy.com/view/fssXzj
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
#define MDIST 120.0
#define STEPS 164.0
#define rot(a) mat2(cos(a),sin(a),-sin(a),cos(a))
#define sat(a) clamp(a,0.0,1.0)
vec3 col3 (vec3 col,float index){
    if(index==1.0)col.rg=1.0-col.rg;
    if(index==2.0)col.gb=1.0-col.gb;
    return col;
}
float SU( float d1, float d2, float k ) {
    float h = sat( 0.5 + 0.5*(d2-d1)/k);
    return mix( d2, d1, h ) - k*h*(1.0-h);
}
float box( vec3 p, vec3 b ){
    vec3 q = abs(p) - b;
    return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0) -0.3;
}
vec3 glw = vec3(0);
float map2(vec3 p){
    float a = length(p-vec3(0,sin(iTime)*4.0,0))-1.5;
    glw +=(0.01/(0.01+a*a))*vec3(0.867,0.000,0.259);
    float b = box(p,vec3(0.6,20.5,0.5));
    a = min(a,b);
    return a;
}
float map(vec3 p){
    vec3 np = p;
    float t = iTime;
    float anim = smoothstep(-.1,.1,cos(t*0.5+0.25+1.6));

    np.xy*=rot(t*anim*0.25);
    float modd = 55.0+anim*400.0;
    //vec2 id = abs(floor((p.xz+modd/2.0)/modd));
    np.xz = mod(np.xz+0.5*modd,modd)-0.5*modd;
    np = abs(np)-vec3(0,0,anim*30.0);
    //np.x = abs(np.x)-(20.0*anim);
    np*=1.0-anim*0.3;
    t*=1.0+anim*3.0;
    t = floor(t)+pow(fract(t),2.0+anim*2.0);
    for(int i = 0; i< 5; i++){
        np = abs(np)-vec3(1.5+4.5*anim);
        np.xy*=rot(0.3+t*0.7*(1.1-anim));
        np.zy*=rot(0.5+t*0.7*(1.1-anim));
    }

    float a = map2(np);
    float b = p.y+3.0;
    a = SU(a,b,1.0);

    b = length(p-vec3(0,-4.5+anim*10.0,0))-9.0;
    glw +=0.01/(0.01+b*b)*vec3(0.035,0.690,0.000);

    a = SU(a,b,1.0);
    float cir = 35.0;
    t=0.5*iTime;

    vec3 ro = vec3(cir*sin(t),4.0+sin(t),cir*cos(t));
    float camHole = length(p-ro)-3.5;
    a = max(-camHole,a);

    return a;
}

vec3 norm(vec3 p) {
    vec2 off=vec2(0.01,0);
    return normalize(map(p)-vec3(map(p-off.xyy),map(p-off.yxy),map(p-off.yyx)));
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = (fragCoord-0.5*iResolution.xy)/iResolution.y;
    vec3 col = vec3(0);
    float t = iTime*0.5;
    float cir = 35.0;
    vec3 ro = vec3(cir*sin(t),4.0+sin(t),cir*cos(t));
    vec3 ro2 = ro;
    vec3 look = vec3(0,0,0); float z = 1.0;
    vec3 f = normalize(look-ro);
    vec3 r = normalize(cross(vec3(0,1,0),f));
    vec3 rd = f*z+uv.x*r+uv.y*cross(f,r);

    float dO = 0.0;
    float shad = 1.0;
    float bnc = 0.0;
    float dist = 0.0;
    for(float i = 0.0; i<STEPS; i++){

        vec3 p = ro + rd * dO;

        float d = map(p)*0.9;

        dO += d;

        if(dO>MDIST || d < 0.01) {
            shad = float(i)/(STEPS);
            if(bnc == 0.0)dist=dO;
            if(bnc == 1.0)break;

            ro += rd*dO;
            vec3 sn = norm(ro);

            rd = reflect(rd,sn);
            ro +=  sn*0.1;
            dO = 0.0;
            //i=0.0;
            bnc++;

        }
    }

    col = vec3(shad)*mix(vec3(0.082,0.941,0.902),vec3(0.557,0.067,1.000),sin(dO*0.01))*5.0;
    col = mix(col,vec3(0.153,0.082,1.000),sat(dist/MDIST));
    col+=glw*0.08;
    col=pow(col,vec3(0.85));
    float index = floor(mod(t*0.7,3.0));
    //col = col3(col,index); //Uncomment for color swaps
    //col.rb*=rot(sin(t)*0.5+0.4); //actually this one is better :)
    fragColor = vec4(col,1.0);
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}
/*
 * Original shader from: https://www.shadertoy.com/view/tttfR2
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
#define PI 3.141592
#define ORBS 20.

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
  float orbs = 20.0+sin(time);
  vec2 uv = (2. * fragCoord - iResolution.xy) / iResolution.y;
  uv *= 279.27;
  for (float i = 0.; i < ORBS;i++) {
    uv.y -= i / 1000. * (uv.x);
    uv.x += i / 0.05 * sin(uv.x / 9.32 + iTime) * 0.21 * cos(uv.y / 16.92 + iTime / 3.) * 0.21;
    float t = 5.1 * i * PI / float(orbs) * (2. + 1.) + iTime / 10.;
    float x = -1. * tan(t);
    float y = sin(t / 3.5795);
    vec2 p = (115. * vec2(x, y)) / sin(PI * sin(uv.x / 14.28 + iTime / 10.));
    vec3 col = cos(vec3(0, 1, -1) * PI * 2. / 3. + PI * (5. + i / 5.)) * 0.5 + 0.5;
    fragColor += vec4(i / 40. * 55.94 / length(uv - p * 0.9) * col, 3.57);
  }
  fragColor.xyz = pow(fragColor.xyz, vec3(3.57));
  fragColor.w = 1.0;
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}